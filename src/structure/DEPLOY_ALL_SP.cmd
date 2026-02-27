@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION
CHCP 1252 >NUL

:: ================================================================================
:: ATHENEO - Script maitre de deploiement des procedures stockees Outlook Add-in
:: Version  : 002
:: Auteur   : VABE
:: Date     : 26/02/2026
::
:: Description :
::   Deploie en sequence toutes les procedures stockees SP_ATHENEO_*
::   sur la base SQL Server de production ATHENEO ERP.
::   Le script est idempotent : chaque procedure est droppee puis recree.
::   Un fichier de log horodate est genere a chaque execution.
::
:: Usage :
::   DEPLOY_ALL_SP.cmd                    -> connexion avec les parametres par defaut
::   DEPLOY_ALL_SP.cmd SERVEUR BASE       -> surcharge serveur et base
::   DEPLOY_ALL_SP.cmd SERVEUR BASE U MDP -> surcharge + auth SQL explicite
::
:: Parametres par defaut (pre-configures pour ATHENEO ERP V17) :
::   Serveur : NA-ATHERP\SQL2022
::   Base    : ATH_ERP_V17_1
::   User    : atheneo_sql
::   Mdp     : M@ster2016
:: ================================================================================

:: ---------------------------------------------------------------
:: PARAMETRES PAR DEFAUT (production ATHENEO ERP)
:: ---------------------------------------------------------------
SET "_SRV_DEFAULT=NA-ATHERP\SQL2022"
SET "_BDD_DEFAULT=ATH_ERP_V17_1"
SET "_USR_DEFAULT=atheneo_sql"
SET "_MDP_DEFAULT=M@ster2016"

:: ---------------------------------------------------------------
:: SURCHARGE PAR ARGUMENTS (optionnelle)
:: ---------------------------------------------------------------
IF NOT "%~1"=="" (SET "SQL_SERVER=%~1") ELSE (SET "SQL_SERVER=%_SRV_DEFAULT%")
IF NOT "%~2"=="" (SET "SQL_BASE=%~2")   ELSE (SET "SQL_BASE=%_BDD_DEFAULT%")
IF NOT "%~3"=="" (SET "SQL_USER=%~3")   ELSE (SET "SQL_USER=%_USR_DEFAULT%")
IF NOT "%~4"=="" (SET "SQL_PASS=%~4")   ELSE (SET "SQL_PASS=%_MDP_DEFAULT%")

:: ---------------------------------------------------------------
:: CHEMINS ET FICHIER DE LOG
:: ---------------------------------------------------------------
SET "SCRIPT_DIR=%~dp0"
SET "_D=%DATE:~6,4%%DATE:~3,2%%DATE:~0,2%"
SET "_T=%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%"
SET "_T=%_T: =0%"
SET "LOGFILE=%SCRIPT_DIR%logs\DEPLOY_%_D%_%_T%.log"

IF NOT EXIST "%SCRIPT_DIR%logs\" MKDIR "%SCRIPT_DIR%logs\"

:: ---------------------------------------------------------------
:: COMPTEURS
:: ---------------------------------------------------------------
SET /A CNT_OK=0
SET /A CNT_KO=0
SET /A CNT_TOTAL=0

:: ---------------------------------------------------------------
:: TIMER DE DEBUT
:: ---------------------------------------------------------------
SET "TIME_START=%TIME%"

:: ================================================================
:: BANNIERE
:: ================================================================
CALL :PRINT_SEP "="
ECHO.
ECHO    ATHENEO ERP - Deploiement Procedures Stockees Outlook Add-in
ECHO.
CALL :PRINT_SEP "="
ECHO.
ECHO    Serveur  : %SQL_SERVER%
ECHO    Base     : %SQL_BASE%
ECHO    User     : %SQL_USER%
ECHO    Date     : %DATE%  %TIME%
ECHO    Log      : %LOGFILE%
ECHO.
CALL :PRINT_SEP "-"

:: ================================================================
:: EN-TETE DU LOG
:: ================================================================
CALL :LOG "================================================================"
CALL :LOG " ATHENEO - Deploiement Procedures Stockees Outlook Add-in"
CALL :LOG " Serveur  : %SQL_SERVER%"
CALL :LOG " Base     : %SQL_BASE%"
CALL :LOG " User     : %SQL_USER%"
CALL :LOG " Debut    : %DATE% %TIME%"
CALL :LOG "================================================================"

:: ================================================================
:: VERIFICATION : sqlcmd disponible
:: ================================================================
WHERE sqlcmd >NUL 2>&1
IF ERRORLEVEL 1 (
    CALL :PRINT_KO "sqlcmd introuvable - installez SQL Server Client Tools"
    CALL :LOG "[FATAL] sqlcmd introuvable"
    GOTO :ERREUR_FATALE
)

:: ================================================================
:: VERIFICATION : connexion a la base
:: ================================================================
ECHO    Test de connexion...
sqlcmd -S "%SQL_SERVER%" -U "%SQL_USER%" -P "%SQL_PASS%" -d "%SQL_BASE%" ^
       -Q "SET NOCOUNT ON; SELECT 'OK' AS connexion" -b -h-1 >NUL 2>&1
IF ERRORLEVEL 1 (
    CALL :PRINT_KO "Connexion impossible a %SQL_SERVER% / %SQL_BASE%"
    CALL :LOG "[FATAL] Connexion impossible a %SQL_SERVER% / %SQL_BASE%"
    GOTO :ERREUR_FATALE
)
CALL :PRINT_OK "Connexion etablie sur %SQL_SERVER% / %SQL_BASE%"
ECHO.

:: ================================================================
:: DEPLOIEMENT DES PROCEDURES STOCKEES
:: (ordre : independantes d'abord, puis avec dependances)
:: ================================================================
CALL :PRINT_SEP "-"
ECHO    Deploiement en cours...
CALL :PRINT_SEP "-"

CALL :DEPLOY "SP_ATHENEO_STATS.sql"                     "Stats globales (health check)"
CALL :DEPLOY "SP_ATHENEO_RECHERCHER_INTERLOCUTEUR.sql"  "Recherche interlocuteur par email"
CALL :DEPLOY "SP_ATHENEO_CREER_INTERLOCUTEUR.sql"       "Creation interlocuteur (idempotent)"
CALL :DEPLOY "SP_ATHENEO_ENREGISTRER_MAIL.sql"          "Enregistrement email Outlook"
CALL :DEPLOY "SP_ATHENEO_LISTER_EMAILS.sql"             "Listage emails Outlook"
CALL :DEPLOY "SP_ATHENEO_CREER_DEMANDE.sql"             "Creation demande (ref DEM-YYYY-XXXXX)"
CALL :DEPLOY "SP_ATHENEO_LISTER_DEMANDES.sql"           "Listage demandes Outlook"
CALL :DEPLOY "SP_ATHENEO_CREER_ACTION.sql"              "Creation action suivi (ref ACT-YYYY-XXXXX)"
CALL :DEPLOY "SP_ATHENEO_LISTER_ACTIONS.sql"            "Listage actions Outlook"
CALL :DEPLOY "SP_ATHENEO_ENREGISTRER_PIECE_JOINTE.sql"  "Enregistrement piece jointe"

:: ================================================================
:: RESUME FINAL
:: ================================================================
SET "TIME_END=%TIME%"
ECHO.
CALL :PRINT_SEP "="
ECHO.
ECHO    RESUME DU DEPLOIEMENT
ECHO.
ECHO    Total     : %CNT_TOTAL% procedures
ECHO    Succes    : %CNT_OK%
ECHO    Erreurs   : %CNT_KO%
ECHO    Debut     : %TIME_START%
ECHO    Fin       : %TIME_END%
ECHO.

CALL :LOG "================================================================"
CALL :LOG " RESUME : %CNT_OK% OK / %CNT_KO% ERREUR / %CNT_TOTAL% TOTAL"
CALL :LOG " Debut : %TIME_START%  |  Fin : %TIME_END%"
CALL :LOG "================================================================"

IF %CNT_KO% GTR 0 (
    CALL :PRINT_KO "%CNT_KO% procedure(s) en erreur - consultez : %LOGFILE%"
    ECHO.
    CALL :PRINT_SEP "="
    ECHO.
    PAUSE
    EXIT /B 1
)

CALL :PRINT_OK "Toutes les procedures ont ete deployees avec succes."
ECHO.
CALL :PRINT_SEP "="
ECHO.
ECHO    Appuyez sur une touche pour fermer...
PAUSE >NUL
EXIT /B 0

:: ================================================================
:: :DEPLOY - deploie une procedure stockee
::   %1 = nom du fichier .sql
::   %2 = libelle affiche dans la console
:: ================================================================
:DEPLOY
SET /A CNT_TOTAL+=1
SET "_FILE=%~1"
SET "_DESC=%~2"
SET "_PATH=%SCRIPT_DIR%%_FILE%"

:: Padding du libelle pour aligner les statuts
SET "_LABEL=  [%CNT_TOTAL%] %-50s%_DESC%"

IF NOT EXIST "%_PATH%" (
    SET "_LABEL=[%CNT_TOTAL%] INTROUVABLE : %_FILE%"
    ECHO    !_LABEL!  [SKIP]
    CALL :LOG "[SKIP] %_FILE% - fichier introuvable"
    SET /A CNT_KO+=1
    GOTO :EOF
)

<NUL SET /P "=    [%CNT_TOTAL%] %-52s%_DESC% ... "

sqlcmd -S "%SQL_SERVER%" -U "%SQL_USER%" -P "%SQL_PASS%" -d "%SQL_BASE%" ^
       -i "%_PATH%" -b -r1 >> "%LOGFILE%" 2>&1

IF ERRORLEVEL 1 (
    ECHO [ ERREUR ]
    CALL :LOG "[ERREUR] %_FILE%"
    SET /A CNT_KO+=1
) ELSE (
    ECHO [  OK    ]
    CALL :LOG "[OK]     %_FILE%"
    SET /A CNT_OK+=1
)
GOTO :EOF

:: ================================================================
:: :PRINT_SEP - separateur console
::   %1 = caractere (= ou -)
:: ================================================================
:PRINT_SEP
ECHO    ---------------------------------------------------------------
GOTO :EOF

:: ================================================================
:: :PRINT_OK - message succes
:: ================================================================
:PRINT_OK
ECHO    [  OK  ] %~1
GOTO :EOF

:: ================================================================
:: :PRINT_KO - message erreur
:: ================================================================
:PRINT_KO
ECHO    [ERREUR] %~1
GOTO :EOF

:: ================================================================
:: :LOG - ecriture dans le fichier log
:: ================================================================
:LOG
ECHO %~1 >> "%LOGFILE%"
GOTO :EOF

:: ================================================================
:: :ERREUR_FATALE
:: ================================================================
:ERREUR_FATALE
ECHO.
CALL :PRINT_SEP "="
ECHO    Deploiement interrompu. Appuyez sur une touche...
PAUSE >NUL
EXIT /B 1
