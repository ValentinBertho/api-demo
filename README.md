# ATHENEO Demo API

API REST qui alimente l'**Add-in Outlook ATHENEO**.
Elle fait le lien entre la boîte mail Outlook d'un conseiller et l'ERP ATHENEO :
enregistrement d'e-mails, création de demandes (incidents), recherche de contacts,
dépôt de pièces jointes — le tout rattaché automatiquement au contexte de travail
courant de l'utilisateur.

---

## Table des matières

- [Architecture](#architecture)
- [Prérequis](#prérequis)
- [Démarrage rapide (Docker)](#démarrage-rapide-docker)
- [Démarrage sans Docker](#démarrage-sans-docker)
- [Configuration](#configuration)
- [Endpoints](#endpoints)
- [Documentation Swagger](#documentation-swagger)
- [Collection Postman](#collection-postman)
- [Structure des procédures stockées SQL](#structure-des-procédures-stockées-sql)
- [Structure du projet](#structure-du-projet)

---

## Architecture

```
┌─────────────────────────────────────┐
│          Outlook Add-in             │  ← taskpane.html (Office.js)
└──────────────────┬──────────────────┘
                   │ REST / multipart
┌──────────────────▼──────────────────┐
│         ATHENEO Demo API            │  ← Spring Boot 3.2 / Java 21
│                                     │
│  ┌──────────────┐  ┌─────────────┐  │
│  │  Controllers │  │  Services   │  │
│  └──────┬───────┘  └──────┬──────┘  │
│         │                 │         │
│  ┌──────▼─────────────────▼──────┐  │
│  │  SQL Server (via JDBC + SP)   │  │
│  └───────────────────────────────┘  │
│  ┌────────────────────────────────┐ │
│  │  WSDocument (SOAP interne)     │ │  ← téléversement fichiers
│  └────────────────────────────────┘ │
└─────────────────────────────────────┘
```

**Stack :**
| Couche | Technologie |
|---|---|
| Framework | Spring Boot 3.2.0 |
| Langage | Java 21 |
| ORM | Spring Data JPA / Hibernate |
| Base de données | Microsoft SQL Server 2022 |
| SOAP | JAX-WS / jaxws-rt 4.0 |
| Build | Maven |
| Documentation | SpringDoc OpenAPI 2 (Swagger UI) |

---

## Prérequis

| Outil | Version minimale |
|---|---|
| Java JDK | 21 |
| Maven | 3.9+ |
| Docker & Docker Compose | 24+ (optionnel) |
| Microsoft SQL Server | 2019+ |

> **Base de données :** L'API utilise des **procédures stockées** déployées dans votre instance SQL Server ATHENEO.
> Voir la section [Structure des procédures stockées SQL](#structure-des-procédures-stockées-sql).

---

## Démarrage rapide (Docker)

```bash
# 1. Cloner le dépôt
git clone <url-du-repo>
cd api-demo

# 2. Lancer avec Docker Compose
docker compose up --build
```

L'API est disponible sur **http://localhost:8080/atheneo/api**
Swagger UI : **http://localhost:8080/atheneo/swagger-ui.html**

> Voir le fichier [`docker-compose.yml`](./docker-compose.yml) pour les variables d'environnement disponibles.

---

## Démarrage sans Docker

```bash
# Compiler
./mvnw clean package -DskipTests

# Lancer avec configuration externe
java -jar target/atheneo-demo-api-1.0.0.jar \
  --spring.datasource.url="jdbc:sqlserver://MON-SERVEUR\SQL2022;databaseName=ATH_ERP_V17_1;encrypt=false" \
  --spring.datasource.username="atheneo_sql" \
  --spring.datasource.password="monMotDePasse"
```

Ou via le Maven wrapper en développement :

```bash
./mvnw spring-boot:run
```

---

## Configuration

Toute la configuration se trouve dans `src/main/resources/application.yml`.
En production, surcharger via des **variables d'environnement** ou un fichier externe.

### Variables d'environnement clés

| Variable | Description | Défaut |
|---|---|---|
| `PORT` | Port d'écoute du serveur | `8080` |
| `DB_URL` | JDBC URL SQL Server | *(voir application.yml)* |
| `DB_USERNAME` | Utilisateur SQL Server | `atheneo_sql` |
| `DB_PASSWORD` | Mot de passe SQL Server | *(à définir)* |
| `WS_DOCUMENT_URI` | URL du service SOAP WSDocument | *(voir application.yml)* |
| `WS_DOCUMENT_LOGIN` | Login WSDocument | *(voir application.yml)* |
| `WS_DOCUMENT_PASSWORD` | Mot de passe WSDocument | *(voir application.yml)* |

---

## Endpoints

**Base URL :** `/atheneo/api`

### Health

| Méthode | Chemin | Description |
|---|---|---|
| `GET` | `/health` | Vérification de l'état du service |

### Mails

| Méthode | Chemin | Description |
|---|---|---|
| `POST` | `/mails` | Enregistrer un e-mail (.eml) dans ATHENEO |

**Paramètres multipart :**

| Paramètre | Type | Requis | Description |
|---|---|---|---|
| `file` | fichier (.eml) | ✅ | Fichier e-mail à archiver |
| `to` | string | ✅ | E-mail du destinataire (conseiller ATHENEO) |
| `from` | string | ✅ | E-mail de l'expéditeur |
| `fromName` | string | ✅ | Nom de l'expéditeur |
| `subject` | string | ✅ | Objet de l'e-mail |
| `body` | string | ✅ | Corps HTML de l'e-mail |
| `date` | string | ✅ | Date ISO-8601 de réception |

### Interlocuteurs

| Méthode | Chemin | Description |
|---|---|---|
| `GET` | `/interlocuteurs?email={email}` | Rechercher un contact par e-mail |

### Demandes

| Méthode | Chemin | Description |
|---|---|---|
| `POST` | `/demandes` | Créer une demande (incident) depuis un e-mail |

**Corps JSON :**

```json
{
  "email": "client@example.com",
  "contactName": "Jean Dupont",
  "subject": "Panne sur le module de facturation",
  "description": "Depuis ce matin, impossible d'accéder à la liste des factures.",
  "source": "EMAIL",
  "priority": "HAUTE",
  "type": "INCIDENT"
}
```

### Pièces jointes

| Méthode | Chemin | Description |
|---|---|---|
| `POST` | `/pieces-jointes` | Enregistrer des pièces jointes dans ATHENEO |

**Paramètres multipart :**

| Paramètre | Type | Requis | Description |
|---|---|---|---|
| `files` | fichiers | ✅ | Un ou plusieurs fichiers |
| `to` | string | ✅ | E-mail du destinataire (conseiller ATHENEO) |
| `from` | string | ✅ | E-mail de l'expéditeur |
| `subject` | string | ✅ | Objet de l'e-mail d'origine |

### Format de réponse standard

Tous les endpoints retournent une enveloppe `ApiResponse<T>` :

```json
{
  "success": true,
  "message": "Opération effectuée",
  "data": { ... }
}
```

En cas d'erreur :

```json
{
  "success": false,
  "message": "Description de l'erreur",
  "data": null
}
```

---

## Documentation Swagger

Une fois l'API démarrée, la documentation interactive est accessible à :

- **Swagger UI :** `http://localhost:8080/atheneo/swagger-ui.html`
- **OpenAPI JSON :** `http://localhost:8080/atheneo/api-docs`

---

## Collection Postman

Un fichier de collection Postman est disponible à la racine du projet :

```
atheneo-api.postman_collection.json
```

**Import :**
1. Ouvrir Postman
2. `File` → `Import`
3. Sélectionner `atheneo-api.postman_collection.json`
4. Configurer la variable `baseUrl` si nécessaire (défaut : `http://localhost:8080/atheneo/api`)

---

## Structure des procédures stockées SQL

Les procédures stockées doivent être déployées dans la base ATHENEO **avant** de démarrer l'API.
Les scripts SQL se trouvent dans `src/structure/`.

```
src/structure/
├── MASTER_DEPLOY_SP.sql                    ← script maître (tout déployer)
├── SP_ATHENEO_CREER_DEMANDE.sql            ← création demande / incident
├── SP_ATHENEO_ENREGISTRER_MAIL.sql         ← archivage e-mail
├── SP_ATHENEO_ENREGISTRER_PIECE_JOINTE.sql ← archivage pièce jointe
├── SP_ATHENEO_RECHERCHER_INTERLOCUTEUR.sql ← recherche contact
├── SP_ATHENEO_RECHERCHER_CONTEXTE.sql      ← contexte de session
└── SP_ATHENEO_STATS.sql                    ← statistiques
```

**Déploiement :**

```sql
-- Exécuter dans SSMS sur la base ATH_ERP_V17_1 :
:r src\structure\MASTER_DEPLOY_SP.sql
```

Ou via PowerShell :

```powershell
.\src\structure\install_procedures.ps1 -Server "NA-ATHERP\SQL2022" -Database "ATH_ERP_V17_1"
```

---

## Structure du projet

```
api-demo/
├── src/
│   ├── main/
│   │   ├── java/fr/mismo/demo_web_addin/
│   │   │   ├── config/           # CORS, OpenAPI
│   │   │   ├── controller/       # Endpoints REST
│   │   │   ├── dto/              # Objets de transfert
│   │   │   ├── model/            # Entités JPA
│   │   │   ├── projection/       # Projections Spring Data
│   │   │   ├── properties/       # Configuration SOAP
│   │   │   ├── repository/       # Accès base de données
│   │   │   ├── services/         # Logique métier
│   │   │   └── util/             # Utilitaires
│   │   └── resources/
│   │       ├── application.yml   # Configuration principale
│   │       ├── wsdocument/       # WSDL / XSD du service WSDocument
│   │       └── static/           # Frontend Add-in Outlook
│   └── structure/                # Scripts SQL (procédures stockées)
├── installer/                    # Scripts d'installation Windows
├── docker-compose.yml            # Démarrage Docker
├── atheneo-api.postman_collection.json  # Collection Postman
├── pom.xml
└── README.md
```
