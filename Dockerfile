# ──────────────────────────────────────────────────
#  Étape 1 : Build Maven
# ──────────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /build

# Copier les fichiers de configuration Maven en premier
# (meilleur usage du cache Docker)
COPY pom.xml .
COPY mvnw .
COPY .mvn/ .mvn/

# Télécharger les dépendances (couche cachée séparément)
RUN ./mvnw dependency:go-offline -q

# Copier les sources et compiler
COPY src/ src/
RUN ./mvnw clean package -DskipTests -q

# ──────────────────────────────────────────────────
#  Étape 2 : Image de production
# ──────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Créer un utilisateur non-root
RUN addgroup -S atheneo && adduser -S atheneo -G atheneo

# Copier le JAR depuis l'étape de build
COPY --from=build /build/target/atheneo-demo-api-*.jar app.jar

# Répertoire d'archivage des fichiers .eml
RUN mkdir -p /app/archives && chown -R atheneo:atheneo /app

USER atheneo

EXPOSE 8080

ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]
