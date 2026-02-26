@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

REM ========================================================================
REM  ATHENEO Outlook Add-in - Script de deploiement des procedures stockees
REM  Version : 001
REM  Auteur  : VABE - 26/02/2026
REM
REM  Usage :
REM    DEPLOY_ALL_SP.cmd [SERVEUR] [BASE] [UTILISATEUR] [MOT_DE_PASSE]
REM
REM  Exemples :
REM    DEPLOY_ALL_SP.cmd                          -> Authentification Windows, serveur local
REM    DEPLOY_ALL_SP.cmd MONSRV ATHENEO           -> Auth Windows sur serveur MONSRV, base ATHENEO
REM    DEPLOY_ALL_SP.cmd MONSRV ATHENEO sa MonMdp -> Auth SQL sur MONSRV
REM
REM  Ce script est idempotent : les procedures existantes sont
REM  supprimees puis recrees (DROP / CREATE normalise ATHENEO).
REM ========================================================================

REM --- Parametres de connexion (arguments optionnels) ---
SET "SQL_SERVER=%~1"
SET "SQL_BASE=%~2"
SET "SQL_USER=%~3"
SET "SQL_PASS=%~4"

IF "%SQL_SERVER%"=="" SET "SQL_SERVER=(local)"
IF "%SQL_BASE%"==""   SET "SQL_BASE=ATHENEO"

REM --- Dossier des scripts SQL (meme dossier que ce .cmd) ---
SET "SCRIPT_DIR=%~dp0"

REM --- Fichier de log horodate ---
SET "LOGFILE=%SCRIPT_DIR%deploy_%DATE:~6,4%%DATE:~3,2%%DATE:~0,2%_%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%.log"
SET "LOGFILE=%LOGFILE: =0%"

REM --- Couleurs console (via ANSI si disponible) ---
SET "OK=[OK]"
SET "KO=[ERREUR]"

ECHO ========================================================================  >> "%LOGFILE%"
ECHO  ATHENEO Outlook Add-in - Deploiement procedures stockees                 >> "%LOGFILE%"
ECHO  Serveur  : %SQL_SERVER%                                                  >> "%LOGFILE%"
ECHO  Base     : %SQL_BASE%                                                    >> "%LOGFILE%"
ECHO  Date     : %DATE% %TIME%                                                 >> "%LOGFILE%"
ECHO ========================================================================  >> "%LOGFILE%"

ECHO.
ECHO  ========================================================
ECHO   ATHENEO Outlook Add-in - Deploiement procedures stockees
ECHO   Serveur : %SQL_SERVER%   Base : %SQL_BASE%
ECHO  ========================================================
ECHO.

REM --- Construction de la chaine de connexion SQLCMD ---
IF "%SQL_USER%"=="" (
    SET "SQLCMD_AUTH=-E"
    ECHO  Mode authentification : Windows (Trusted)
) ELSE (
    SET "SQLCMD_AUTH=-U %SQL_USER% -P %SQL_PASS%"
    ECHO  Mode authentification : SQL Server (utilisateur %SQL_USER%)
)
ECHO.

REM --- Verification de la presence de sqlcmd ---
WHERE sqlcmd >nul 2>&1
IF ERRORLEVEL 1 (
    ECHO %KO% sqlcmd introuvable. Verifiez l'installation de SQL Server Client Tools.
    ECHO %KO% sqlcmd introuvable >> "%LOGFILE%"
    PAUSE
    EXIT /B 1
)

REM --- Verification de la connexion a la base ---
ECHO  Test de connexion a %SQL_SERVER%\%SQL_BASE%...
sqlcmd -S %SQL_SERVER% %SQLCMD_AUTH% -d %SQL_BASE% -Q "SELECT 1" -b >nul 2>&1
IF ERRORLEVEL 1 (
    ECHO %KO% Connexion impossible a %SQL_SERVER%\%SQL_BASE%
    ECHO %KO% Connexion impossible >> "%LOGFILE%"
    PAUSE
    EXIT /B 1
)
ECHO  %OK% Connexion etablie.
ECHO.

REM ========================================================================
REM  Ordre de deploiement des procedures stockees
REM  (les procedures independantes en premier, puis celles avec dependances)
REM ========================================================================

SET COUNT_OK=0
SET COUNT_KO=0

CALL :DEPLOY_SP "SP_ATHENEO_STATS.sql"                       "Stats globales Outlook Add-in"
CALL :DEPLOY_SP "SP_ATHENEO_RECHERCHER_INTERLOCUTEUR.sql"    "Recherche interlocuteur par email"
CALL :DEPLOY_SP "SP_ATHENEO_CREER_INTERLOCUTEUR.sql"         "Creation interlocuteur depuis email"
CALL :DEPLOY_SP "SP_ATHENEO_ENREGISTRER_MAIL.sql"            "Enregistrement email Outlook"
CALL :DEPLOY_SP "SP_ATHENEO_LISTER_EMAILS.sql"               "Liste des emails Outlook"
CALL :DEPLOY_SP "SP_ATHENEO_CREER_DEMANDE.sql"               "Creation demande depuis Outlook"
CALL :DEPLOY_SP "SP_ATHENEO_LISTER_DEMANDES.sql"             "Liste des demandes Outlook"
CALL :DEPLOY_SP "SP_ATHENEO_CREER_ACTION.sql"                "Creation action de suivi Outlook"
CALL :DEPLOY_SP "SP_ATHENEO_LISTER_ACTIONS.sql"              "Liste des actions Outlook"
CALL :DEPLOY_SP "SP_ATHENEO_ENREGISTRER_PIECE_JOINTE.sql"    "Enregistrement piece jointe Outlook"

REM --- Resume ---
ECHO.
ECHO  ========================================================
ECHO   RESUME DU DEPLOIEMENT
ECHO   %OK% Procedures deployees avec succes : %COUNT_OK%
IF %COUNT_KO% GTR 0 (
    ECHO   %KO% Procedures en erreur           : %COUNT_KO%
    ECHO.
    ECHO   Consultez le fichier de log :
    ECHO   %LOGFILE%
) ELSE (
    ECHO   Toutes les procedures ont ete deployees avec succes.
)
ECHO  ========================================================
ECHO.

ECHO RESUME : %COUNT_OK% OK / %COUNT_KO% ERREUR >> "%LOGFILE%"

IF %COUNT_KO% GTR 0 (
    PAUSE
    EXIT /B 1
)

ECHO  Deploiement termine. Appuyez sur une touche pour quitter.
PAUSE >nul
EXIT /B 0

REM ========================================================================
REM  Sous-routine de deploiement d'une procedure stockee
REM  Param 1 : nom du fichier SQL
REM  Param 2 : libelle descriptif
REM ========================================================================
:DEPLOY_SP
SET "SP_FILE=%~1"
SET "SP_DESC=%~2"
SET "SP_PATH=%SCRIPT_DIR%%SP_FILE%"

IF NOT EXIST "%SP_PATH%" (
    ECHO  %KO% Fichier introuvable : %SP_FILE%
    ECHO %KO% Fichier introuvable : %SP_FILE% >> "%LOGFILE%"
    SET /A COUNT_KO+=1
    GOTO :EOF
)

ECHO  Deploiement : %-55s%%SP_DESC%
sqlcmd -S %SQL_SERVER% %SQLCMD_AUTH% -d %SQL_BASE% -i "%SP_PATH%" -b >> "%LOGFILE%" 2>&1

IF ERRORLEVEL 1 (
    ECHO   %KO%  %SP_FILE%
    ECHO %KO% %SP_FILE% >> "%LOGFILE%"
    SET /A COUNT_KO+=1
) ELSE (
    ECHO   %OK%  %SP_FILE%
    ECHO %OK% %SP_FILE% >> "%LOGFILE%"
    SET /A COUNT_OK+=1
)
GOTO :EOF
