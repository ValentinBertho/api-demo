# Documentation Technico-Fonctionnelle — Add-in Outlook ATHENEO

> **Version :** 1.0.0
> **Date :** Mars 2026
> **Projet :** ATHENEO Outlook Add-in
> **Auteur :** MISMO

---

## Table des matières

1. [Description générale](#1-description-générale)
2. [Fonctionnalités principales](#2-fonctionnalités-principales)
3. [Architecture technique](#3-architecture-technique)
4. [Prérequis et dépendances](#4-prérequis-et-dépendances)
5. [Installation et déploiement](#5-installation-et-déploiement)
6. [Référence des endpoints API](#6-référence-des-endpoints-api)
7. [Base de données et procédures stockées](#7-base-de-données-et-procédures-stockées)
8. [Configuration](#8-configuration)
9. [Sécurité](#9-sécurité)

---

## 1. Description générale

### 1.1 Contexte et objectifs

L'**Add-in Outlook ATHENEO** est une extension intégrée directement dans le client de messagerie Microsoft Outlook (desktop et web). Elle permet aux utilisateurs d'interagir avec l'ERP **ATHENEO** sans quitter leur environnement de travail habituel.

L'objectif principal est de **réduire les frictions liées à la saisie manuelle** en permettant d'archiver des e-mails, de créer des demandes de support et de gérer des pièces jointes directement depuis Outlook, en les associant automatiquement aux objets métier correspondants dans ATHENEO (devis, interventions, etc.).

### 1.2 Périmètre fonctionnel

| Domaine | Description |
|---|---|
| Archivage e-mail | Enregistrement d'un e-mail dans l'ERP ATHENEO |
| Gestion des demandes | Création de tickets/incidents depuis un e-mail |
| Gestion des contacts | Recherche d'un interlocuteur ATHENEO par adresse e-mail |
| Pièces jointes | Dépôt des pièces jointes d'un e-mail dans ATHENEO |

### 1.3 Composants du projet

Le projet est composé de deux parties distinctes :

- **Backend REST API** : application Spring Boot 3 (Java 21) agissant comme couche intermédiaire entre le client Outlook et l'ERP ATHENEO.
- **Frontend Add-in** : interface HTML/JS chargée dans un panneau latéral Outlook (taskpane), pilotée par l'API Office.js de Microsoft.

---

## 2. Fonctionnalités principales

### 2.1 Archivage d'un e-mail

L'utilisateur peut archiver l'e-mail sélectionné dans Outlook directement dans ATHENEO. Le système :

1. Récupère les métadonnées de l'e-mail (expéditeur, destinataire, sujet, date, corps).
2. Détermine automatiquement le **contexte ATHENEO actif** de l'utilisateur (ex. : fiche Devis n°69392).
3. Transmet le fichier `.eml` au service documentaire ATHENEO via SOAP.
4. Enregistre les métadonnées en base de données.

### 2.2 Création d'une demande (ticket/incident)

Depuis un e-mail reçu, l'utilisateur peut créer une demande de support dans ATHENEO :

- Pré-remplissage automatique des champs (contact, sujet, description).
- Choix de la priorité, du type et de la source de la demande.
- Le ticket est créé dans ATHENEO et un identifiant de référence est retourné à l'utilisateur.

### 2.3 Recherche d'un interlocuteur

Le système recherche automatiquement dans ATHENEO un interlocuteur correspondant à l'adresse e-mail de l'expéditeur. Si un contact est trouvé, ses informations sont affichées dans le panneau (nom, société, téléphone, statut).

### 2.4 Gestion des pièces jointes

L'utilisateur peut enregistrer les pièces jointes d'un e-mail dans ATHENEO :

- Chaque fichier est transmis au service documentaire ATHENEO via SOAP (par blocs de 512 Ko).
- Les métadonnées de chaque pièce jointe sont enregistrées en base de données.
- Un récapitulatif du nombre de pièces jointes enregistrées est retourné.

---

## 3. Architecture technique

### 3.1 Vue d'ensemble

```
┌─────────────────────────────────────────────────┐
│                 Client Outlook                  │
│          (Desktop / Web / Mobile)               │
│                                                 │
│  ┌───────────────────────────────────────────┐  │
│  │         Panneau latéral (Taskpane)        │  │
│  │   taskpane.html + Office.js (API Microsoft│  │
│  └────────────────────┬──────────────────────┘  │
└───────────────────────┼─────────────────────────┘
                        │ HTTPS (REST/JSON)
                        ▼
┌───────────────────────────────────────────────────┐
│              Backend — Spring Boot 3              │
│                                                   │
│  ┌──────────────┐  ┌───────────────────────────┐  │
│  │  Controllers │  │        Services           │  │
│  │  (REST API)  │→ │ Mail / Interlocuteur /    │  │
│  └──────────────┘  │ Demande / PieceJointe /   │  │
│                    │ WsDocument                │  │
│                    └──────┬────────────────────┘  │
│                           │                       │
│              ┌────────────┴────────────┐          │
│              ▼                         ▼          │
│  ┌───────────────────┐   ┌─────────────────────┐ │
│  │ SQL Server (JDBC) │   │  WSDocument (SOAP)  │ │
│  │  via Repositories │   │  Service ATHENEO    │ │
│  │  + Procédures     │   └─────────────────────┘ │
│  │  stockées         │                           │
│  └───────────────────┘                           │
└───────────────────────────────────────────────────┘
```

### 3.2 Structure du projet

```
api-demo/
├── src/
│   ├── main/
│   │   ├── java/fr/mismo/demo_web_addin/
│   │   │   ├── config/              # Configuration Spring (CORS, OpenAPI, SOAP)
│   │   │   ├── controller/          # Contrôleurs REST (endpoints API)
│   │   │   ├── dto/                 # Objets de transfert de données (requêtes/réponses)
│   │   │   ├── entity/              # Entités JPA (mapping base de données)
│   │   │   ├── model/               # Modèles métier
│   │   │   ├── projection/          # Projections Spring Data
│   │   │   ├── repository/          # Couche d'accès aux données (JPA)
│   │   │   ├── services/            # Logique métier
│   │   │   ├── properties/          # Propriétés de configuration typées
│   │   │   └── util/                # Utilitaires (parsing, fichiers)
│   │   └── resources/
│   │       ├── application.yml      # Configuration principale
│   │       ├── wsdocument/          # WSDL + XSD du service SOAP
│   │       └── static/              # Frontend de l'add-in (HTML, JS, icônes)
│   └── structure/                   # Scripts SQL (procédures stockées)
├── installer/                       # Scripts d'installation Windows
├── Dockerfile                       # Image Docker multi-stage
├── docker-compose.yml               # Orchestration Docker
└── pom.xml                          # Dépendances Maven
```

### 3.3 Couches applicatives

| Couche | Classes | Rôle |
|---|---|---|
| **Contrôleur** | `AtheneoController`, `RootController` | Exposition des endpoints REST, validation des entrées |
| **Service** | `MailService`, `InterlocuteurService`, `DemandeService`, `PieceJointeService`, `WsDocumentService` | Logique métier, orchestration des appels |
| **Repository** | `InterlocuteurRepository`, `EmailRepository`, `PieceJointeRepository` | Accès base de données via Spring Data JPA |
| **SOAP Client** | `WsDocumentService` / `IWsDocumentService` | Communication avec le service documentaire ATHENEO |
| **Utilitaires** | `ContexteParser`, `FilesUtil` | Parsing du contexte utilisateur, manipulation de fichiers |

### 3.4 Flux de traitement — Archivage d'un e-mail

```
Outlook (taskpane)
      │
      │  POST /atheneo/api/mails  (multipart/form-data)
      │  Champs : file(.eml), to, from, fromName, subject, body, date
      ▼
AtheneoController.enregistrerMail()
      │
      ├─► InterlocuteurService.rechercherContexte(to)
      │       └─► SP_ATHENEO_RECHERCHER_CONTEXTE  →  contexte actif (ex: "Devis N°69392")
      │
      ├─► ContexteParser.parse(contexte)
      │       └─►  {"NO_DEVIS": "69392"}
      │
      ├─► WsDocumentService.creerDocument(file, contextParams)
      │       ├─► creerDocInfo()   (SOAP)
      │       ├─► creerDocVersion() (SOAP)
      │       └─► televerser()     (SOAP, blocs 512 Ko)
      │
      └─► MailService.enregistrerMail(metadata)
              └─► SP_ATHENEO_ENREGISTRER_MAIL  →  ID e-mail enregistré
```

### 3.5 APIs et intégrations externes

| Intégration | Protocole | Description |
|---|---|---|
| **Microsoft Office.js** | JS (navigateur) | API Outlook pour lire les données de l'e-mail sélectionné |
| **WSDocument ATHENEO** | SOAP/HTTP | Service de gestion documentaire ATHENEO (upload de fichiers) |
| **SQL Server ATHENEO** | JDBC | Base de données ERP ATHENEO (`ATH_ERP_V17_1`) |

### 3.6 Manifeste Outlook (`manifest.xml`)

Le manifeste définit les métadonnées de l'add-in pour Microsoft 365 :

| Propriété | Valeur |
|---|---|
| Identifiant | `d4f5e6a7-8b9c-0d1e-2f3a-4b5c6d7e8f9a` |
| Nom affiché | ATHENEO |
| Version | 1.0.0.0 |
| Mode d'activation | Lecture d'un message (`MessageRead`) |
| Permissions | `ReadWriteMailbox` |
| URL du panneau | `https://<host>/atheneo/taskpane.html` |

---

## 4. Prérequis et dépendances

### 4.1 Environnement serveur

| Composant | Version minimale | Remarques |
|---|---|---|
| **Java (JDK)** | 21 | OpenJDK ou Eclipse Temurin recommandés |
| **Maven** | 3.9+ | Ou utiliser le wrapper `./mvnw` inclus |
| **Docker** | 24+ | Optionnel, pour déploiement conteneurisé |
| **Docker Compose** | 2.x | Optionnel |

### 4.2 Infrastructure ATHENEO

| Composant | Description |
|---|---|
| **SQL Server** | Instance SQL Server hébergeant la base `ATH_ERP_V17_1` |
| **Service WSDocument** | Service SOAP de gestion documentaire ATHENEO accessible en HTTP |
| **Procédures stockées** | Procédures ATHENEO déployées dans la base SQL Server |

### 4.3 Dépendances applicatives (Maven)

| Dépendance | Version | Rôle |
|---|---|---|
| `spring-boot-starter-web` | 3.2.0 | Framework REST |
| `spring-boot-starter-data-jpa` | 3.2.0 | ORM et accès base de données |
| `mssql-jdbc` | Latest | Driver JDBC Microsoft SQL Server |
| `spring-boot-starter-web-services` | 3.2.0 | Client SOAP |
| `spring-ws-core` | Latest | `WebServiceTemplate` pour SOAP |
| `jaxws-rt` | 4.0.2 | Implémentation JAX-WS |
| `jakarta.xml.bind-api` | Latest | Sérialisation XML (JAXB) |
| `httpclient` (Apache) | 4.5.14 | Transport HTTP pour SOAP |
| `springdoc-openapi-starter-webmvc-ui` | 2.3.0 | Swagger UI / OpenAPI 3 |
| `lombok` | Latest | Réduction du code boilerplate |
| `h2` | Latest | Base en mémoire (profil dev uniquement) |

### 4.4 Prérequis côté client Outlook

- Microsoft Outlook **2016 ou supérieur** (desktop) ou **Outlook sur le Web** (OWA)
- Connectivité HTTPS vers le serveur hébergeant l'API
- Compte Microsoft 365 avec droits d'installation d'add-ins

---

## 5. Installation et déploiement

### 5.1 Déploiement Docker (recommandé)

#### Étape 1 — Cloner le dépôt et configurer

```bash
git clone <url-du-dépôt>
cd api-demo
```

Éditer les variables d'environnement dans `docker-compose.yml` :

```yaml
environment:
  SPRING_DATASOURCE_URL: jdbc:sqlserver://<SERVEUR>\<INSTANCE>;databaseName=ATH_ERP_V17_1
  SPRING_DATASOURCE_USERNAME: <utilisateur_sql>
  SPRING_DATASOURCE_PASSWORD: <mot_de_passe_sql>
  WSDOCUMENT_DEFAULTURI: http://<serveur_soap>:8082/WSDocumentAth/WSDocumentAth.svc
  WSDOCUMENT_LOGIN: <login_soap>
  WSDOCUMENT_PASSWORD: <mot_de_passe_soap>
```

#### Étape 2 — Construire et démarrer

```bash
docker compose up --build -d
```

#### Étape 3 — Vérifier le démarrage

```bash
curl http://localhost:8080/atheneo/api/health
```

Réponse attendue :
```json
{
  "status": "UP",
  "service": "ATHENEO Demo API",
  "timestamp": "2026-03-17T10:00:00"
}
```

---

### 5.2 Déploiement JAR standalone (Windows/Linux)

#### Étape 1 — Compiler le projet

```bash
./mvnw clean package -DskipTests
```

Le JAR est généré dans `target/atheneo-demo-api-1.0.0.jar`.

#### Étape 2 — Créer le fichier de configuration

Créer un fichier `application.yml` à côté du JAR (ou utiliser l'exemple dans `installer/application-example.yml`) :

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://<SERVEUR>\<INSTANCE>;databaseName=ATH_ERP_V17_1
    username: <utilisateur_sql>
    password: <mot_de_passe_sql>

wsdocument:
  defaultUri: http://<serveur_soap>:8082/WSDocumentAth/WSDocumentAth.svc
  login: <login_soap>
  password: <mot_de_passe_soap>
```

#### Étape 3 — Démarrer l'API

```bash
java -jar atheneo-demo-api-1.0.0.jar
```

Ou via les scripts Windows fournis dans `installer/` :

```cmd
installer\start_api.bat
```

---

### 5.3 Déploiement via installeur Windows

Un installeur Windows est disponible, généré par **Inno Setup** à partir du script `installer/atheneo_outlook_addin_setup.iss`.

Il automatise :
- Le déploiement du JAR et de la configuration
- L'enregistrement du service Windows (démarrage automatique)
- La configuration initiale

---

### 5.4 Déploiement des procédures stockées SQL

Les procédures stockées doivent être déployées **une seule fois** sur la base de données ATHENEO.

#### Option 1 — Script PowerShell (recommandé)

```powershell
cd src/structure
.\install_procedures.ps1 -Server "NA-ATHERP\SQL2022" -Database "ATH_ERP_V17_1"
```

#### Option 2 — Script SQL maître

Exécuter `MASTER_DEPLOY_SP.sql` dans SQL Server Management Studio (SSMS) sur la base `ATH_ERP_V17_1`.

#### Option 3 — Ligne de commande (sqlcmd)

```cmd
src\structure\DEPLOY_ALL_SP.cmd
```

---

### 5.5 Installation de l'add-in dans Outlook

#### Via sideloading (développement / test)

1. Dans Outlook, aller dans **Fichier > Gérer les compléments** (ou **Obtenir des compléments**).
2. Choisir **Mes compléments > Ajouter un complément personnalisé > À partir d'un fichier**.
3. Sélectionner le fichier `src/main/resources/static/manifest.xml`.

#### Via déploiement centralisé Microsoft 365 (production)

1. Se connecter au **Centre d'administration Microsoft 365**.
2. Aller dans **Paramètres > Applications intégrées > Télécharger des applications personnalisées**.
3. Charger le fichier `manifest.xml` et l'affecter aux utilisateurs ou groupes cibles.

> **Important :** L'URL définie dans le manifeste (`taskpane.html`) doit être accessible en HTTPS depuis les postes clients.

---

### 5.6 Accès à la documentation API

Une fois l'API démarrée, la documentation interactive Swagger est disponible à :

```
http://localhost:8080/atheneo/swagger-ui.html
```

La définition OpenAPI (JSON) est disponible à :

```
http://localhost:8080/atheneo/api-docs
```

---

## 6. Référence des endpoints API

**URL de base :** `http://<host>:8080/atheneo/api`

### 6.1 Health

| Méthode | Endpoint | Description |
|---|---|---|
| `GET` | `/health` | Vérifie que le service est opérationnel |

**Réponse :**
```json
{
  "status": "UP",
  "service": "ATHENEO Demo API",
  "timestamp": "2026-03-17T10:00:00"
}
```

---

### 6.2 Mails

| Méthode | Endpoint | Content-Type | Description |
|---|---|---|---|
| `POST` | `/mails` | `multipart/form-data` | Archive un e-mail dans ATHENEO |

**Paramètres (form-data) :**

| Paramètre | Type | Requis | Description |
|---|---|---|---|
| `file` | Fichier `.eml` | Oui | Fichier brut de l'e-mail |
| `to` | String | Oui | Adresse e-mail du destinataire |
| `from` | String | Oui | Adresse e-mail de l'expéditeur |
| `fromName` | String | Non | Nom de l'expéditeur |
| `subject` | String | Oui | Sujet de l'e-mail |
| `body` | String | Non | Corps de l'e-mail |
| `date` | String | Non | Date d'envoi (ISO 8601) |

**Réponse (200 OK) :**
```json
{
  "success": true,
  "message": "E-mail enregistré avec succès",
  "data": {
    "id": 42,
    "expediteur": "contact@client.fr",
    "sujet": "Demande de devis"
  }
}
```

---

### 6.3 Interlocuteurs

| Méthode | Endpoint | Description |
|---|---|---|
| `GET` | `/interlocuteurs?email={email}` | Recherche un contact par adresse e-mail |

**Paramètres (query string) :**

| Paramètre | Type | Requis | Description |
|---|---|---|---|
| `email` | String | Oui | Adresse e-mail à rechercher |

**Réponse (200 OK) :**
```json
{
  "success": true,
  "message": "Interlocuteur trouvé",
  "data": {
    "id": 123,
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean.dupont@client.fr",
    "telephone": "01 23 45 67 89",
    "telephonePortable": "06 12 34 56 78",
    "societe": "Client SAS",
    "noSociete": 456,
    "actif": true,
    "source": "CRM"
  }
}
```

---

### 6.4 Demandes

| Méthode | Endpoint | Content-Type | Description |
|---|---|---|---|
| `POST` | `/demandes` | `application/json` | Crée un ticket/incident dans ATHENEO |

**Corps de la requête :**
```json
{
  "email": "jean.dupont@client.fr",
  "contactName": "Jean Dupont",
  "subject": "Problème de connexion",
  "description": "Impossible de se connecter depuis ce matin.",
  "source": "EMAIL",
  "priority": "HAUTE",
  "type": "INCIDENT"
}
```

**Réponse (200 OK) :**
```json
{
  "success": true,
  "message": "Demande créée avec succès",
  "data": {
    "id": 789,
    "reference": "INC-2026-0789"
  }
}
```

---

### 6.5 Pièces jointes

| Méthode | Endpoint | Content-Type | Description |
|---|---|---|---|
| `POST` | `/pieces-jointes` | `multipart/form-data` | Enregistre les pièces jointes d'un e-mail |

**Paramètres (form-data) :**

| Paramètre | Type | Requis | Description |
|---|---|---|---|
| `files` | Fichiers (liste) | Oui | Liste des fichiers joints |
| `to` | String | Oui | Adresse e-mail du destinataire |
| `from` | String | Oui | Adresse e-mail de l'expéditeur |
| `subject` | String | Oui | Sujet de l'e-mail |

**Réponse (200 OK) :**
```json
{
  "success": true,
  "message": "3 pièce(s) jointe(s) enregistrée(s)",
  "data": {
    "count": 3
  }
}
```

---

### 6.6 Structure générique des réponses

Tous les endpoints retournent un objet `ApiResponse<T>` :

```json
{
  "success": true | false,
  "message": "Description du résultat",
  "data": { ... }
}
```

---

## 7. Base de données et procédures stockées

### 7.1 Procédures stockées

| Procédure | Rôle |
|---|---|
| `SP_ATHENEO_ENREGISTRER_MAIL` | Archive les métadonnées d'un e-mail |
| `SP_ATHENEO_ENREGISTRER_PIECE_JOINTE` | Enregistre les métadonnées d'une pièce jointe |
| `SP_ATHENEO_CREER_DEMANDE` | Crée un ticket/incident dans la table `INCIDENT` |
| `SP_ATHENEO_RECHERCHER_INTERLOCUTEUR` | Recherche un contact dans la table `INTERLOC` |
| `SP_ATHENEO_RECHERCHER_CONTEXTE` | Retourne le contexte ATHENEO actif de l'utilisateur |
| `SP_ATHENEO_STATS` | Procédure de statistiques et reporting |

### 7.2 Tables concernées

| Table | Description |
|---|---|
| `INTERLOC` | Contacts/interlocuteurs de l'ERP |
| `INCIDENT` | Tickets et demandes de support |
| Archives e-mail | Table d'archivage des e-mails entrants |

### 7.3 Parsing du contexte utilisateur

Le `ContexteParser` transforme une chaîne de contexte ATHENEO en paramètres exploitables :

| Contexte (chaîne) | Clé générée |
|---|---|
| `Fiche Devis` | `NO_DEVIS` |
| `Fiche Intervention` | `NO_INTERVENTION` |
| *(autres modules)* | *(selon mapping défini)* |

Exemple : `"Fiche Devis - N°69392"` → `{"NO_DEVIS": "69392"}`

---

## 8. Configuration

### 8.1 Fichier `application.yml`

```yaml
server:
  port: 8080
  servlet:
    context-path: /atheneo

spring:
  datasource:
    url: jdbc:sqlserver://<SERVEUR>\<INSTANCE>;databaseName=ATH_ERP_V17_1
    username: <utilisateur>
    password: <mot_de_passe>
  jpa:
    database-platform: org.hibernate.dialect.SQLServerDialect
    hibernate:
      ddl-auto: none

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html

wsdocument:
  defaultUri: http://<serveur_soap>:8082/WSDocumentAth/WSDocumentAth.svc
  login: <login_soap>
  password: <mot_de_passe_soap>
  proprietesMail:
    typeDocument: MAIL
    auteurDocument: ADDIN
  proprietesPieceJointe:
    typeDocument: PJ
    auteurDocument: ADDIN

cors:
  allowed:
    origins: "*"

logging:
  level:
    fr.mismo: INFO
```

### 8.2 Profils Spring

| Profil | Comportement |
|---|---|
| *(par défaut)* | Connexion SQL Server réelle + service SOAP activé |
| `dev` | Service SOAP désactivé (`@Profile("!dev")`) — utile pour les tests sans infrastructure SOAP |

Pour activer le profil dev :
```bash
java -jar atheneo-demo-api-1.0.0.jar --spring.profiles.active=dev
```

### 8.3 Variables d'environnement (Docker)

| Variable | Description |
|---|---|
| `SPRING_DATASOURCE_URL` | URL JDBC SQL Server |
| `SPRING_DATASOURCE_USERNAME` | Utilisateur SQL |
| `SPRING_DATASOURCE_PASSWORD` | Mot de passe SQL |
| `WSDOCUMENT_DEFAULTURI` | URL du service SOAP WSDocument |
| `WSDOCUMENT_LOGIN` | Login SOAP |
| `WSDOCUMENT_PASSWORD` | Mot de passe SOAP |

---

## 9. Sécurité

### 9.1 Recommandations de production

> **Attention :** Le fichier `application.yml` fourni dans le dépôt contient des identifiants en clair à titre d'exemple. Ces valeurs **ne doivent pas** être utilisées en production telles quelles.

| Point de vigilance | Recommandation |
|---|---|
| Credentials base de données | Externaliser via variables d'environnement ou vault |
| Credentials SOAP | Externaliser via variables d'environnement |
| CORS (`allowed.origins: "*"`) | Restreindre aux domaines Outlook autorisés en production |
| Transport | Exposer l'API exclusivement en **HTTPS** (requis par Microsoft 365) |
| Permissions add-in | `ReadWriteMailbox` — vérifier que le niveau de permission est nécessaire et justifié |

### 9.2 Exigences Microsoft 365

Microsoft impose que toute URL référencée dans un manifeste d'add-in Office soit accessible via **HTTPS** avec un certificat valide. En environnement de développement local, un proxy HTTPS (ex. : `ngrok`, `localtunnel`) ou un certificat auto-signé avec configuration de confiance est nécessaire.

---

*Documentation générée à partir du code source du projet `api-demo` — MISMO.*
