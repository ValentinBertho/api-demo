; ========================================================================
;  ATHENEO Outlook Add-in - Script d'installation Inno Setup
;  Version      : 1.0.0
;  Auteur       : VABE - 26/02/2026
;
;  Comportement idempotent :
;    - Si une installation existe deja (application.yml present),
;      les valeurs de configuration existantes sont CONSERVEES.
;    - Seul le JAR et les assets statiques sont mis a jour.
;    - La configuration YAML n'est ecrasee que si l'utilisateur
;      choisit explicitement de la reinitialiser.
;
;  Pre-requis :
;    - Java 21+ installe sur le serveur cible (JRE ou JDK)
;    - Le JAR atheneo-demo-api-1.0.0.jar compile par Maven
;    - Inno Setup 6.x (https://jrsoftware.org/isinfo.php)
;
;  Pour compiler ce script :
;    iscc atheneo_outlook_addin_setup.iss
; ========================================================================

#define AppName      "ATHENEO Outlook Add-in API"
#define AppVersion   "1.0.0"
#define AppPublisher "MISMO / ATHENEO"
#define AppID        "{D4F5E6A7-8B9C-0D1E-2F3A-4B5C6D7E8F9A}"
#define AppExeName   "atheneo-demo-api-1.0.0.jar"
#define ServiceName  "AtheneoOutlookAPI"
#define ServiceDesc  "ATHENEO Outlook Add-in Backend API"

[Setup]
AppId={#AppID}
AppName={#AppName}
AppVersion={#AppVersion}
AppVerName={#AppName} {#AppVersion}
AppPublisher={#AppPublisher}
DefaultDirName={autopf}\ATHENEO\OutlookAddinAPI
DefaultGroupName=ATHENEO\Outlook Add-in API
OutputDir=.\output
OutputBaseFilename=ATHENEO_OutlookAddinAPI_Setup_{#AppVersion}
; Note : pas de SetupIconFile - utilise l'icone Inno Setup par defaut
; Pour utiliser une icone personnalisee, placez un fichier .ico ici et ajoutez :
; SetupIconFile=atheneo.ico
Compression=lzma2/ultra64
SolidCompression=yes
WizardStyle=modern
PrivilegesRequired=admin
DisableProgramGroupPage=no
; Desactive la detection de reinstallation pour forcer le comportement idempotent
CloseApplications=yes
RestartApplications=no
ArchitecturesInstallIn64BitMode=x64
MinVersion=10.0

[Languages]
Name: "french"; MessagesFile: "compiler:Languages\French.isl"

[CustomMessages]
french.ConfigKeepExisting=Conserver la configuration existante (recommande pour une mise a jour)
french.ConfigResetDefault=Reinitialiser la configuration avec les valeurs par defaut
french.LabelPort=Port d'ecoute de l'API :
french.LabelDBServer=Serveur SQL Server :
french.LabelDBName=Nom de la base de donnees :
french.LabelDBUser=Utilisateur SQL (vide = auth Windows) :
french.LabelDBPass=Mot de passe SQL :
french.LabelCors=Origines CORS autorisees (ex: https://outlook.office.com) :
french.ServiceInstalled=Le service Windows AtheneoOutlookAPI sera installe et demarre automatiquement.
french.JavaNotFound=Java 21 ou superieur est requis mais introuvable sur ce systeme.%nVeuillez installer Java 21+ et relancer l'installation.
french.ExistingConfigFound=Une configuration existante a ete detectee et sera conservee.

[Types]
Name: "full";    Description: "Installation complete (API + Service Windows + Scripts SQL)"
Name: "update";  Description: "Mise a jour uniquement (JAR + assets, conservation config)"
Name: "custom";  Description: "Installation personnalisee"; Flags: iscustom

[Components]
Name: "api";       Description: "API Spring Boot (JAR + assets)";      Types: full update custom; Flags: fixed
Name: "service";   Description: "Service Windows (demarrage automatique)"; Types: full custom
Name: "sqlscripts"; Description: "Scripts SQL (procedures stockees)";   Types: full custom

[Tasks]
Name: "startservice";  Description: "Demarrer le service immediatement apres l'installation"; GroupDescription: "Options de service :"; Components: service
Name: "resetconfig";   Description: "Reinitialiser la configuration (ecrasera application.yml existant)"; GroupDescription: "Configuration :"; Flags: unchecked

[Dirs]
Name: "{app}"
Name: "{app}\config"
Name: "{app}\logs"
Name: "{app}\sql"

[Files]
; JAR principal
Source: "..\target\atheneo-demo-api-1.0.0.jar"; DestDir: "{app}"; Flags: ignoreversion; Components: api
; Script de demarrage
Source: "start_api.bat";                         DestDir: "{app}"; Flags: ignoreversion; Components: api
; Scripts SQL
Source: "..\src\structure\*.sql";                DestDir: "{app}\sql"; Flags: ignoreversion recursesubdirs; Components: sqlscripts
Source: "..\src\structure\DEPLOY_ALL_SP.cmd";    DestDir: "{app}\sql"; Flags: ignoreversion; Components: sqlscripts
; Assets statiques (servis par Spring Boot)
; Note : inclus dans le JAR, pas besoin de copie separee

; === NOTE SUR LE SERVICE WINDOWS ===
; Cette installation utilise le Planificateur de taches Windows (schtasks)
; pour demarrer l'API automatiquement au boot - aucun outil tiers requis.
; Prerequis : Java 21+ accessible dans le PATH du compte SYSTEM
; (ou modifier start_api.bat pour preciser le chemin Java complet).

[Icons]
Name: "{group}\Demarrer l'API ATHENEO";        Filename: "{app}\start_api.bat"; WorkingDir: "{app}"
Name: "{group}\Console H2 (dev)";              Filename: "http://localhost:{code:GetPortValue}/atheneo/h2-console"
Name: "{group}\Health Check API";              Filename: "http://localhost:{code:GetPortValue}/atheneo/api/health"
Name: "{group}\Desinstaller {#AppName}";       Filename: "{uninstallexe}"

[Run]
; Suppression de la tache planifiee existante avant mise a jour
Filename: "schtasks"; Parameters: "/delete /tn ""{#ServiceName}"" /f"; Flags: runhidden waituntilterminated; StatusMsg: "Suppression tache planifiee existante..."; Check: ServiceExists
; Creation de la tache planifiee (Planificateur Windows - aucun outil tiers requis)
; La tache se lance au demarrage systeme avec les droits SYSTEM
Filename: "schtasks"; Parameters: "/create /tn ""{#ServiceName}"" /tr ""cmd /c start """" /b \""{app}\start_api.bat\"""" /sc onstart /ru SYSTEM /rl HIGHEST /f"; Flags: runhidden waituntilterminated; StatusMsg: "Creation de la tache planifiee au demarrage..."; Components: service
; Demarrage immediat si demande
Filename: "schtasks"; Parameters: "/run /tn ""{#ServiceName}"""; Flags: runhidden waituntilterminated; StatusMsg: "Demarrage du service..."; Components: service; Tasks: startservice

[UninstallRun]
Filename: "schtasks"; Parameters: "/end /tn ""{#ServiceName}""";    Flags: runhidden waituntilterminated
Filename: "schtasks"; Parameters: "/delete /tn ""{#ServiceName}"" /f"; Flags: runhidden waituntilterminated; Check: ServiceExists

[Code]

{ ======================================================================
  Variables globales
  ====================================================================== }
var
  PageConfig: TWizardPage;

  { Champs du formulaire de configuration }
  EdtPort:     TNewEdit;
  EdtDBServer: TNewEdit;
  EdtDBName:   TNewEdit;
  EdtDBUser:   TNewEdit;
  EdtDBPass:   TNewEdit;
  EdtCors:     TNewEdit;

  { Valeurs lues depuis le YAML existant }
  ExistingPort:     String;
  ExistingDBServer: String;
  ExistingDBName:   String;
  ExistingDBUser:   String;
  ExistingDBPass:   String;
  ExistingCors:     String;
  ConfigFileExists: Boolean;

{ ======================================================================
  Lecture d'une valeur dans le fichier application.yml existant.
  Recherche la cle YAML au format "  cle: valeur" (indentation variable).
  ====================================================================== }
function ReadYamlValue(const FilePath, Key: String): String;
var
  Lines:   TStringList;
  I:       Integer;
  Line:    String;
  KeyPos:  Integer;
  ValStr:  String;
begin
  Result := '';
  if not FileExists(FilePath) then Exit;

  Lines := TStringList.Create;
  try
    Lines.LoadFromFile(FilePath);
    for I := 0 to Lines.Count - 1 do
    begin
      Line := Trim(Lines[I]);
      { Recherche "cle: " ou "cle:" }
      KeyPos := Pos(Key + ':', Line);
      if KeyPos = 1 then
      begin
        ValStr := Trim(Copy(Line, Length(Key) + 2, MaxInt));
        { Suppression des guillemets eventuels }
        if (Length(ValStr) >= 2) and
           ((ValStr[1] = '"') or (ValStr[1] = '''')) then
          ValStr := Copy(ValStr, 2, Length(ValStr) - 2);
        { Suppression du commentaire eventuel }
        KeyPos := Pos('#', ValStr);
        if KeyPos > 0 then
          ValStr := Trim(Copy(ValStr, 1, KeyPos - 1));
        Result := ValStr;
        Break;
      end;
    end;
  finally
    Lines.Free;
  end;
end;

{ ======================================================================
  Ecriture du fichier application.yml avec les valeurs saisies.
  ====================================================================== }
procedure WriteApplicationYml(const DestPath: String);
var
  Lines: TStringList;
  Port, DBUrl, DBUser, DBPass, CorsOrigins: String;
begin
  Port       := Trim(EdtPort.Text);
  DBUser     := Trim(EdtDBUser.Text);
  DBPass     := Trim(EdtDBPass.Text);
  CorsOrigins := Trim(EdtCors.Text);

  if Port = '' then Port := '8080';

  { Construction de l'URL JDBC SQL Server }
  if (Trim(EdtDBServer.Text) <> '') and (Trim(EdtDBName.Text) <> '') then
  begin
    if DBUser <> '' then
      DBUrl := 'jdbc:sqlserver://' + Trim(EdtDBServer.Text) +
               ';databaseName=' + Trim(EdtDBName.Text) +
               ';encrypt=true;trustServerCertificate=true'
    else
      DBUrl := 'jdbc:sqlserver://' + Trim(EdtDBServer.Text) +
               ';databaseName=' + Trim(EdtDBName.Text) +
               ';integratedSecurity=true;encrypt=true;trustServerCertificate=true';
  end
  else
    { Mode demo H2 si pas de serveur SQL configure }
    DBUrl := 'jdbc:h2:mem:atheneo_demo';

  Lines := TStringList.Create;
  try
    Lines.Add('# ========================================================');
    Lines.Add('# ATHENEO Outlook Add-in API - Configuration');
    Lines.Add('# Genere par l''installeur le ' + GetDateTimeString('dd/mm/yyyy hh:nn:ss', '-', ':'));
    Lines.Add('# ========================================================');
    Lines.Add('');
    Lines.Add('server:');
    Lines.Add('  port: ' + Port);
    Lines.Add('  servlet:');
    Lines.Add('    context-path: /atheneo');
    Lines.Add('');
    Lines.Add('spring:');
    Lines.Add('  datasource:');
    Lines.Add('    url: ' + DBUrl);

    if (Trim(EdtDBServer.Text) <> '') and (DBUser <> '') then
    begin
      Lines.Add('    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver');
      Lines.Add('    username: ' + DBUser);
      Lines.Add('    password: ' + DBPass);
    end
    else if (Trim(EdtDBServer.Text) <> '') then
    begin
      Lines.Add('    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver');
      Lines.Add('    username:');
      Lines.Add('    password:');
    end
    else
    begin
      Lines.Add('    driverClassName: org.h2.Driver');
      Lines.Add('    username: sa');
      Lines.Add('    password:');
    end;

    Lines.Add('  jpa:');
    if Trim(EdtDBServer.Text) <> '' then
      Lines.Add('    database-platform: org.hibernate.dialect.SQLServerDialect')
    else
      Lines.Add('    database-platform: org.hibernate.dialect.H2Dialect');
    Lines.Add('    hibernate:');
    if Trim(EdtDBServer.Text) <> '' then
      Lines.Add('      ddl-auto: validate')
    else
      Lines.Add('      ddl-auto: create-drop');
    Lines.Add('    show-sql: false');
    Lines.Add('');

    if Trim(EdtDBServer.Text) = '' then
    begin
      Lines.Add('  h2:');
      Lines.Add('    console:');
      Lines.Add('      enabled: true');
      Lines.Add('      path: /h2-console');
      Lines.Add('');
    end;

    Lines.Add('cors:');
    Lines.Add('  allowed:');
    if CorsOrigins = '' then
      Lines.Add('    origins: "*"')
    else
      Lines.Add('    origins: "' + CorsOrigins + '"');
    Lines.Add('');
    Lines.Add('logging:');
    Lines.Add('  level:');
    Lines.Add('    fr:');
    Lines.Add('      mismo: INFO');
    Lines.Add('  pattern:');
    Lines.Add('    console: "%d{dd/MM HH:mm:ss} [%thread] %-5level %logger{15} - %msg%n"');
    Lines.Add('  file:');
    Lines.Add('    name: ' + ExpandConstant('{app}') + '\logs\atheneo-api.log');
    Lines.Add('    max-size: 10MB');
    Lines.Add('    max-history: 30');

    ForceDirectories(ExtractFilePath(DestPath));
    Lines.SaveToFile(DestPath);
  finally
    Lines.Free;
  end;
end;

{ ======================================================================
  Verifie si le service Windows est deja installe
  ====================================================================== }
{ Verifie si la tache planifiee est deja enregistree }
function ServiceExists: Boolean;
var
  ResultCode: Integer;
begin
  Result := Exec('schtasks', '/query /tn "{#ServiceName}"', '', SW_HIDE, ewWaitUntilTerminated, ResultCode)
            and (ResultCode = 0);
end;

{ ======================================================================
  Retourne la valeur du port (pour les icones)
  ====================================================================== }
function GetPortValue(Param: String): String;
begin
  if Assigned(EdtPort) and (Trim(EdtPort.Text) <> '') then
    Result := Trim(EdtPort.Text)
  else
    Result := '8080';
end;

{ ======================================================================
  Initialisation : lecture config existante
  ====================================================================== }
procedure InitializeWizard;
var
  ExistingYml: String;
  LblPort, LblDBServer, LblDBName, LblDBUser, LblDBPass, LblCors: TNewStaticText;
  LblInfo: TNewStaticText;
  Y: Integer;
begin
  { Chemin du YAML existant }
  ExistingYml     := ExpandConstant('{app}\config\application.yml');
  ConfigFileExists := FileExists(ExistingYml);

  { Valeurs par defaut }
  ExistingPort     := '8080';
  ExistingDBServer := '';
  ExistingDBName   := 'ATHENEO';
  ExistingDBUser   := '';
  ExistingDBPass   := '';
  ExistingCors     := '*';

  { Lecture des valeurs existantes si le fichier YAML est present }
  if ConfigFileExists then
  begin
    ExistingPort     := ReadYamlValue(ExistingYml, 'port');
    ExistingDBServer := ReadYamlValue(ExistingYml, 'url');
    ExistingDBName   := ReadYamlValue(ExistingYml, 'databaseName');
    ExistingDBUser   := ReadYamlValue(ExistingYml, 'username');
    ExistingCors     := ReadYamlValue(ExistingYml, 'origins');
    { Nettoyage de l'URL JDBC pour extraire le serveur }
    if Pos('sqlserver://', ExistingDBServer) > 0 then
    begin
      ExistingDBServer := Copy(ExistingDBServer,
        Pos('sqlserver://', ExistingDBServer) + Length('sqlserver://'), MaxInt);
      if Pos(';', ExistingDBServer) > 0 then
        ExistingDBServer := Copy(ExistingDBServer, 1, Pos(';', ExistingDBServer) - 1);
    end else
      ExistingDBServer := '';
    if ExistingPort = '' then ExistingPort := '8080';
    if ExistingCors = '' then ExistingCors := '*';
  end;

  { Creation de la page de configuration personnalisee }
  PageConfig := CreateCustomPage(wpSelectComponents,
    'Configuration de l''API ATHENEO',
    'Parametres de connexion et d''ecoute de l''API Outlook Add-in');

  Y := 8;

  { Bandeau information si config existante detectee }
  if ConfigFileExists then
  begin
    LblInfo := TNewStaticText.Create(PageConfig);
    LblInfo.Parent  := PageConfig.Surface;
    LblInfo.Caption := 'Configuration existante detectee - les valeurs ci-dessous sont pre-remplies.';
    LblInfo.Font.Color := clBlue;
    LblInfo.Font.Style := [fsBold];
    LblInfo.SetBounds(0, Y, PageConfig.SurfaceWidth, 16);
    Y := Y + 22;
  end;

  { Port }
  LblPort         := TNewStaticText.Create(PageConfig);
  LblPort.Parent  := PageConfig.Surface;
  LblPort.Caption := CustomMessage('LabelPort');
  LblPort.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;
  EdtPort         := TNewEdit.Create(PageConfig);
  EdtPort.Parent  := PageConfig.Surface;
  EdtPort.Text    := ExistingPort;
  EdtPort.SetBounds(0, Y, 100, 21);
  Y := Y + 28;

  { Serveur SQL }
  LblDBServer         := TNewStaticText.Create(PageConfig);
  LblDBServer.Parent  := PageConfig.Surface;
  LblDBServer.Caption := CustomMessage('LabelDBServer');
  LblDBServer.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;
  EdtDBServer         := TNewEdit.Create(PageConfig);
  EdtDBServer.Parent  := PageConfig.Surface;
  EdtDBServer.Text    := ExistingDBServer;
  EdtDBServer.SetBounds(0, Y, PageConfig.SurfaceWidth div 2, 21);
  Y := Y + 28;

  { Base de donnees }
  LblDBName         := TNewStaticText.Create(PageConfig);
  LblDBName.Parent  := PageConfig.Surface;
  LblDBName.Caption := CustomMessage('LabelDBName');
  LblDBName.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;
  EdtDBName         := TNewEdit.Create(PageConfig);
  EdtDBName.Parent  := PageConfig.Surface;
  EdtDBName.Text    := ExistingDBName;
  EdtDBName.SetBounds(0, Y, PageConfig.SurfaceWidth div 2, 21);
  Y := Y + 28;

  { Utilisateur SQL }
  LblDBUser         := TNewStaticText.Create(PageConfig);
  LblDBUser.Parent  := PageConfig.Surface;
  LblDBUser.Caption := CustomMessage('LabelDBUser');
  LblDBUser.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;
  EdtDBUser         := TNewEdit.Create(PageConfig);
  EdtDBUser.Parent  := PageConfig.Surface;
  EdtDBUser.Text    := ExistingDBUser;
  EdtDBUser.SetBounds(0, Y, PageConfig.SurfaceWidth div 2, 21);
  Y := Y + 28;

  { Mot de passe SQL }
  LblDBPass         := TNewStaticText.Create(PageConfig);
  LblDBPass.Parent  := PageConfig.Surface;
  LblDBPass.Caption := CustomMessage('LabelDBPass');
  LblDBPass.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;
  EdtDBPass         := TNewEdit.Create(PageConfig);
  EdtDBPass.Parent  := PageConfig.Surface;
  EdtDBPass.Text    := ExistingDBPass;
  EdtDBPass.PasswordChar := '*';
  EdtDBPass.SetBounds(0, Y, PageConfig.SurfaceWidth div 2, 21);
  Y := Y + 28;

  { Origines CORS }
  LblCors         := TNewStaticText.Create(PageConfig);
  LblCors.Parent  := PageConfig.Surface;
  LblCors.Caption := CustomMessage('LabelCors');
  LblCors.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;
  EdtCors         := TNewEdit.Create(PageConfig);
  EdtCors.Parent  := PageConfig.Surface;
  EdtCors.Text    := ExistingCors;
  EdtCors.SetBounds(0, Y, PageConfig.SurfaceWidth, 21);
end;

{ ======================================================================
  Verification de Java 21+
  ====================================================================== }
function InitializeSetup: Boolean;
var
  JavaVer: String;
  ResultCode: Integer;
begin
  Result := True;
  { Verification de Java }
  if not RegQueryStringValue(HKLM, 'SOFTWARE\JavaSoft\JDK', 'CurrentVersion', JavaVer) then
    if not RegQueryStringValue(HKLM64, 'SOFTWARE\JavaSoft\JDK', 'CurrentVersion', JavaVer) then
      JavaVer := '';

  if JavaVer = '' then
  begin
    if MsgBox(CustomMessage('JavaNotFound') + #13#10 +
              'Continuer quand meme (non recommande) ?',
              mbConfirmation, MB_YESNO) = IDNO then
      Result := False;
  end;
end;

{ ======================================================================
  Ecriture du YAML lors de l'installation (apres copie des fichiers)
  ====================================================================== }
procedure CurStepChanged(CurStep: TSetupStep);
var
  YmlPath: String;
begin
  if CurStep = ssPostInstall then
  begin
    YmlPath := ExpandConstant('{app}\config\application.yml');

    { Ecriture du YAML uniquement si :
      - Pas de config existante, OU
      - La tache "resetconfig" est cochee }
    if (not ConfigFileExists) or IsTaskSelected('resetconfig') then
    begin
      WriteApplicationYml(YmlPath);
    end
    else
    begin
      { Config existante conservee - on affiche juste un message }
      Log('Configuration existante conservee : ' + YmlPath);
    end;
  end;
end;

{ ======================================================================
  Validation de la page de configuration
  ====================================================================== }
function NextButtonClick(CurPageID: Integer): Boolean;
var
  Port: Integer;
begin
  Result := True;
  if CurPageID = PageConfig.ID then
  begin
    { Validation du port }
    if Trim(EdtPort.Text) <> '' then
    begin
      Port := StrToIntDef(Trim(EdtPort.Text), 0);
      if (Port < 1024) or (Port > 65535) then
      begin
        MsgBox('Le port doit etre compris entre 1024 et 65535.', mbError, MB_OK);
        EdtPort.SetFocus;
        Result := False;
        Exit;
      end;
    end;
  end;
end;
