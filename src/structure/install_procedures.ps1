<#
.SYNOPSIS
    Installation des procedures stockees ADDIN OUTLOOK
.DESCRIPTION
    Script lanceur pour MASTER_DEPLOY.sql.
    Utilise sqlcmd.exe pour l'execution native des scripts SQL (gestion GO).
    Idempotent : DROP IF EXISTS + CREATE PROCEDURE.
    Auteur : VABE - MISMO - 26/02/2026
.PARAMETER Server
    Serveur SQL Server (defaut : NA-ATHERP\SQL2022)
.PARAMETER Database
    Base de donnees (defaut : ATH_ERP_V17_1)
.PARAMETER Username
    Utilisateur SQL (defaut : atheneo_sql)
.PARAMETER Password
    Mot de passe SQL
.PARAMETER WindowsAuth
    Authentification Windows (Trusted Connection)
.EXAMPLE
    .\install_procedures.ps1
.EXAMPLE
    .\install_procedures.ps1 -Server "MON_SRV\SQL2022" -Database "MA_BASE"
.EXAMPLE
    .\install_procedures.ps1 -WindowsAuth
#>

param(
    [string]$Server      = "MISMOI2824P\SQL2022",
    [string]$Database    = "ATH_ERP_V17_1",
    [string]$Username    = "sa",
    [string]$Password    = "M@ster2022",
    [switch]$WindowsAuth
)

# ─── Configuration ─────────────────────────────────────────────────────────────
$ScriptDir = $PSScriptRoot
$SqlMaster = Join-Path $ScriptDir "MASTER_DEPLOY_SP.sql"
$Timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$LogFile   = Join-Path $ScriptDir "install_$Timestamp.log"
$RunDate   = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
$AuthLabel = if ($WindowsAuth) { "Windows (Trusted)" } else { "SQL - $Username" }

# ─── Helpers ───────────────────────────────────────────────────────────────────
function Write-Banner ([string]$Text) {
    Write-Host $Text -ForegroundColor Cyan
}

# ─── En-tete ───────────────────────────────────────────────────────────────────
Write-Host ""
Write-Banner "  ============================================================================"
Write-Banner "   ADDIN - Installation des procedures stockees"
Write-Banner "  ============================================================================"
Write-Host "   Date    : $RunDate"
Write-Host "   Serveur : $Server"
Write-Host "   Base    : $Database"
Write-Host "   Auth    : $AuthLabel"
Write-Host "   Log     : $LogFile"
Write-Banner "  ============================================================================"
Write-Host ""

@"
============================================================================
 ADDIN - Installation des procedures stockees
 Version : 1.0.0  |  VABE - MISMO  |  26/02/2026
 Date    : $RunDate
 Serveur : $Server
 Base    : $Database
 Auth    : $AuthLabel
 Log     : $LogFile
============================================================================

"@ | Out-File $LogFile -Encoding UTF8

# ─── Verification de sqlcmd ────────────────────────────────────────────────────
if (-not (Get-Command sqlcmd -ErrorAction SilentlyContinue)) {
    Write-Host "  [ERREUR] sqlcmd.exe introuvable dans le PATH." -ForegroundColor Red
    Write-Host "           https://learn.microsoft.com/sql/tools/sqlcmd/sqlcmd-utility" -ForegroundColor Yellow
    exit 1
}

# ─── Verification du script SQL maitre ────────────────────────────────────────
if (-not (Test-Path $SqlMaster)) {
    Write-Host "  [ERREUR] Script introuvable : $SqlMaster" -ForegroundColor Red
    exit 1
}

# ─── Arguments de connexion sqlcmd ─────────────────────────────────────────────
$ConnArgs = @("-S", $Server, "-d", $Database, "-b", "-f", "65001")
if ($WindowsAuth) {
    $ConnArgs += "-E"
} else {
    $ConnArgs += @("-U", $Username, "-P", $Password)
}

# ─── Execution via sqlcmd (sortie console + log simultanes) ────────────────────
Push-Location $ScriptDir
try {
    & sqlcmd @ConnArgs -i "MASTER_DEPLOY_SP.sql" 2>&1 | Tee-Object -FilePath $LogFile -Append
    $ExitCode = $LASTEXITCODE
} finally {
    Pop-Location
}

# ─── Resultat ──────────────────────────────────────────────────────────────────
Write-Host ""
if ($ExitCode -ne 0) {
    Write-Host "  [ERREUR] Echec de l'installation (code $ExitCode)." -ForegroundColor Red
    Write-Host "           Consultez le log : $LogFile" -ForegroundColor Yellow
    exit $ExitCode
}

Write-Host "  [OK] Installation terminee avec succes!" -ForegroundColor Green
Write-Host "  Log  : $LogFile"
Write-Host ""
exit 0
