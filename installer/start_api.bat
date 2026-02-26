@ECHO OFF
SETLOCAL

REM ========================================================================
REM  ATHENEO Outlook Add-in API - Script de demarrage
REM  Auteur : VABE - 26/02/2026
REM ========================================================================

SET "APP_DIR=%~dp0"
SET "JAR_FILE=%APP_DIR%atheneo-demo-api-1.0.0.jar"
SET "CONFIG_FILE=%APP_DIR%config\application.yml"
SET "LOG_DIR=%APP_DIR%logs"

IF NOT EXIST "%LOG_DIR%" MKDIR "%LOG_DIR%"

ECHO  Demarrage de l'API ATHENEO Outlook Add-in...
ECHO  JAR    : %JAR_FILE%
ECHO  Config : %CONFIG_FILE%
ECHO.

IF NOT EXIST "%JAR_FILE%" (
    ECHO [ERREUR] Fichier JAR introuvable : %JAR_FILE%
    PAUSE
    EXIT /B 1
)

IF EXIST "%CONFIG_FILE%" (
    java -jar "%JAR_FILE%" --spring.config.location=file:"%CONFIG_FILE%"
) ELSE (
    ECHO  [INFO] Pas de configuration personnalisee, demarrage avec les valeurs par defaut...
    java -jar "%JAR_FILE%"
)
