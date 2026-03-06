; ========================================================================
;  ATHENEO Outlook Add-in - Script d'installation Inno Setup 6.x
;  Version      : 1.0.0
;  Auteur       : VABE - 26/02/2026
;
;  Comportement idempotent :
;    - Si une installation precedente existe (detectee via le registre),
;      les valeurs de configuration sont lues et pre-remplies dans le wizard.
;    - Le YAML n'est ecrase que si l'utilisateur coche "Reinitialiser".
;
;  Pre-requis :
;    - Java 21+ installe sur le serveur cible (JRE ou JDK)
;    - Le JAR atheneo-demo-api-1.0.0.jar compile par Maven (target/)
;    - Inno Setup 6.x (https://jrsoftware.org/isinfo.php)
;
;  Pour compiler :
;    iscc atheneo_outlook_addin_setup.iss
;    => output\ATHENEO_OutlookAddinAPI_Setup_1.0.0.exe
; ========================================================================

#define AppName      "ATHENEO Outlook Add-in API"
#define AppVersion   "1.0.0"
#define AppPublisher "MISMO / ATHENEO"
#define AppID        "D4F5E6A7-8B9C-0D1E-2F3A-4B5C6D7E8F9A"
#define AppExeName   "atheneo-demo-api-1.0.0.jar"
#define ServiceName  "AtheneoOutlookAPI"

; ── SECTION SETUP ────────────────────────────────────────────

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
; Pour ajouter une icone personnalisee : placez atheneo.ico ici et decommenter :
; SetupIconFile=atheneo.ico
Compression=lzma2/ultra64
SolidCompression=yes
WizardStyle=modern
PrivilegesRequired=admin
DisableProgramGroupPage=no
CloseApplications=yes
RestartApplications=no
ArchitecturesInstallIn64BitMode=x64
MinVersion=10.0

; ── LANGUES ──────────────────────────────────────────────────

[Languages]
Name: "french"; MessagesFile: "compiler:Languages\French.isl"

[CustomMessages]
french.LabelPort=Port d'ecoute de l'API (1024-65535) :
french.LabelDBServer=Serveur SQL Server (ex: MONSЕРВEUR\SQL2022) :
french.LabelDBName=Nom de la base de donnees :
french.LabelDBUser=Utilisateur SQL (laisser vide = auth Windows) :
french.LabelDBPass=Mot de passe SQL :
french.LabelCors=Origines CORS autorisees (* = tout, ou https://outlook.office.com) :
french.JavaNotFound=Java 21 ou superieur est requis mais introuvable.%nVeuillez installer Java 21+ avant de continuer.%n%nContinuer quand meme (non recommande) ?

; ── TYPES ET COMPOSANTS ───────────────────────────────────────

[Types]
Name: "full";   Description: "Installation complete (API + Service Windows + Scripts SQL)"
Name: "update"; Description: "Mise a jour uniquement (JAR + scripts, conservation config)"
Name: "custom"; Description: "Installation personnalisee"; Flags: iscustom

[Components]
Name: "api";        Description: "API Spring Boot (JAR + script de demarrage)"; Types: full update custom; Flags: fixed
Name: "service";    Description: "Demarrage automatique au boot (Planificateur Windows)";  Types: full custom
Name: "sqlscripts"; Description: "Scripts SQL (procedures stockees ATHENEO)";              Types: full custom

; ── TACHES ───────────────────────────────────────────────────

[Tasks]
Name: "startservice"; Description: "Demarrer l'API immediatement apres l'installation"; GroupDescription: "Demarrage :"; Components: service
Name: "resetconfig";  Description: "Reinitialiser la configuration (ecrasera application.yml existant)"; GroupDescription: "Configuration :"; Flags: unchecked

; ── DOSSIERS ─────────────────────────────────────────────────

[Dirs]
Name: "{app}"
Name: "{app}\config"
Name: "{app}\logs"
Name: "{app}\sql"

; ── FICHIERS ─────────────────────────────────────────────────

[Files]
; JAR principal
Source: "..\target\atheneo-demo-api-1.0.0.jar"; DestDir: "{app}"; Flags: ignoreversion; Components: api
; Script de demarrage manuel
Source: "start_api.bat";                         DestDir: "{app}"; Flags: ignoreversion; Components: api
; Scripts SQL (deploiement procedures stockees)
Source: "..\src\structure\*.sql";                DestDir: "{app}\sql"; Flags: ignoreversion; Components: sqlscripts
Source: "..\src\structure\DEPLOY_ALL_SP.cmd";    DestDir: "{app}\sql"; Flags: ignoreversion; Components: sqlscripts

; ── RACCOURCIS ───────────────────────────────────────────────

[Icons]
Name: "{group}\Demarrer l'API ATHENEO";    Filename: "{app}\start_api.bat"; WorkingDir: "{app}"
Name: "{group}\Health Check";              Filename: "http://localhost:{code:GetPortValue}/atheneo/api/health"
Name: "{group}\Console H2 (demo)";        Filename: "http://localhost:{code:GetPortValue}/atheneo/h2-console"
Name: "{group}\Desinstaller {#AppName}";  Filename: "{uninstallexe}"

; ── EXECUTION POST-INSTALLATION ──────────────────────────────

[Run]
; Supprime l'ancienne tache planifiee si elle existe (mise a jour idempotente)
Filename: "{sys}\schtasks.exe"; Parameters: "/delete /tn ""{#ServiceName}"" /f"; Flags: runhidden waituntilterminated; Check: ServiceExists; StatusMsg: "Suppression tache existante..."
; Cree la tache planifiee au demarrage systeme (aucun outil tiers requis)
Filename: "{sys}\schtasks.exe"; Parameters: "/create /tn ""{#ServiceName}"" /tr ""cmd /c start """" /b \""{app}\start_api.bat\"""" /sc onstart /ru SYSTEM /rl HIGHEST /f"; Flags: runhidden waituntilterminated; Components: service; StatusMsg: "Creation de la tache planifiee..."
; Demarrage immediat si l'utilisateur a coche la case
Filename: "{sys}\schtasks.exe"; Parameters: "/run /tn ""{#ServiceName}"""; Flags: runhidden waituntilterminated; Components: service; Tasks: startservice; StatusMsg: "Demarrage de l'API..."

; ── DESINSTALLATION ──────────────────────────────────────────

[UninstallRun]
Filename: "{sys}\schtasks.exe"; Parameters: "/end /tn ""{#ServiceName}""";    Flags: runhidden waituntilterminated
Filename: "{sys}\schtasks.exe"; Parameters: "/delete /tn ""{#ServiceName}"" /f"; Flags: runhidden waituntilterminated; Check: ServiceExists

; ════════════════════════════════════════════════════════════
; CODE PASCAL
; ════════════════════════════════════════════════════════════

[Code]

{ ── Variables globales ─────────────────────────────────────── }
var
  PageConfig:   TWizardPage;
  EdtPort:      TNewEdit;
  EdtDBServer:  TNewEdit;
  EdtDBName:    TNewEdit;
  EdtDBUser:    TNewEdit;
  EdtDBPass:    TNewEdit;
  EdtCors:      TNewEdit;

  { Valeurs lues depuis le YAML existant (ou defauts) }
  DefaultPort:     String;
  DefaultDBServer: String;
  DefaultDBName:   String;
  DefaultDBUser:   String;
  DefaultDBPass:   String;
  DefaultCors:     String;
  PrevConfigFound: Boolean;  { True si une installation precedente a ete detectee }

{ ── ReadYamlValue ───────────────────────────────────────────
  Lit une valeur depuis un fichier YAML simple.
  Cherche la premiere ligne qui commence par "cle:" (insensible
  a l'indentation apres Trim) et retourne la valeur associee.
  ─────────────────────────────────────────────────────────── }
function ReadYamlValue(const FilePath, Key: String): String;
var
  Lines:  TStringList;
  I:      Integer;
  Line:   String;
  Prefix: String;
  Val:    String;
  ComPos: Integer;
begin
  Result := '';
  if not FileExists(FilePath) then Exit;

  Prefix := Key + ':';
  Lines  := TStringList.Create;
  try
    Lines.LoadFromFile(FilePath);
    for I := 0 to Lines.Count - 1 do
    begin
      Line := Trim(Lines[I]);
      if Pos(Prefix, Line) = 1 then
      begin
        Val := Trim(Copy(Line, Length(Prefix) + 1, MaxInt));
        { Retire les guillemets simples ou doubles }
        if (Length(Val) >= 2) and
           ((Val[1] = '"') or (Val[1] = '''')) and
           (Val[Length(Val)] = Val[1]) then
          Val := Copy(Val, 2, Length(Val) - 2);
        { Retire les commentaires en fin de ligne }
        ComPos := Pos(' #', Val);
        if ComPos > 0 then Val := Trim(Copy(Val, 1, ComPos - 1));
        Result := Val;
        Break;
      end;
    end;
  finally
    Lines.Free;
  end;
end;

{ ── WriteApplicationYml ─────────────────────────────────────
  Genere le fichier application.yml dans app config a partir
  des champs saisis dans la page de configuration du wizard.
  Appele uniquement depuis CurStepChanged(ssPostInstall), moment
  ou la constante app est garantie d'etre resolue.
  ─────────────────────────────────────────────────────────── }
procedure WriteApplicationYml(const DestPath: String);
var
  Lines:       TStringList;
  Port:        String;
  DBUser:      String;
  DBPass:      String;
  DBUrl:       String;
  CorsOrigins: String;
  AppDir:      String;
  IsSqlServer: Boolean;
begin
  Port        := Trim(EdtPort.Text);
  DBUser      := Trim(EdtDBUser.Text);
  DBPass      := Trim(EdtDBPass.Text);
  CorsOrigins := Trim(EdtCors.Text);
  AppDir      := ExpandConstant('{app}');

  if Port = '' then Port := '8080';
  if CorsOrigins = '' then CorsOrigins := '*';

  IsSqlServer := (Trim(EdtDBServer.Text) <> '') and (Trim(EdtDBName.Text) <> '');

  { Construction de l'URL JDBC }
  if IsSqlServer then
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
    DBUrl := 'jdbc:h2:mem:atheneo_demo';

  Lines := TStringList.Create;
  try
    Lines.Add('# ============================================================');
    Lines.Add('# ATHENEO Outlook Add-in API - Configuration');
    Lines.Add('# Genere par l''installeur le ' + GetDateTimeString('dd/mm/yyyy hh:nn:ss', '/', ':'));
    Lines.Add('# ============================================================');
    Lines.Add('');

    { Serveur }
    Lines.Add('server:');
    Lines.Add('  port: ' + Port);
    Lines.Add('  servlet:');
    Lines.Add('    context-path: /atheneo');
    Lines.Add('');

    { DataSource }
    Lines.Add('spring:');
    Lines.Add('  datasource:');
    Lines.Add('    url: ' + DBUrl);

    if IsSqlServer then
    begin
      Lines.Add('    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver');
      Lines.Add('    username: ' + DBUser);
      Lines.Add('    password: ' + DBPass);
    end
    else
    begin
      Lines.Add('    driver-class-name: org.h2.Driver');
      Lines.Add('    username: sa');
      Lines.Add('    password:');
    end;

    { JPA }
    Lines.Add('  jpa:');
    if IsSqlServer then
    begin
      Lines.Add('    database-platform: org.hibernate.dialect.SQLServerDialect');
      Lines.Add('    hibernate:');
      Lines.Add('      ddl-auto: none');
    end
    else
    begin
      Lines.Add('    database-platform: org.hibernate.dialect.H2Dialect');
      Lines.Add('    hibernate:');
      Lines.Add('      ddl-auto: create-drop');
    end;
    Lines.Add('    show-sql: false');
    Lines.Add('');

    { Console H2 (mode demo uniquement) }
    if not IsSqlServer then
    begin
      Lines.Add('  h2:');
      Lines.Add('    console:');
      Lines.Add('      enabled: true');
      Lines.Add('      path: /h2-console');
      Lines.Add('');
    end;

    { CORS }
    Lines.Add('cors:');
    Lines.Add('  allowed:');
    Lines.Add('    origins: "' + CorsOrigins + '"');
    Lines.Add('');

    { Logging }
    Lines.Add('logging:');
    Lines.Add('  level:');
    Lines.Add('    fr:');
    Lines.Add('      mismo: INFO');
    Lines.Add('    org:');
    Lines.Add('      hibernate:');
    Lines.Add('        SQL: WARN');
    Lines.Add('  pattern:');
    Lines.Add('    console: "%d{dd/MM HH:mm:ss} [%-5level] %logger{20} - %msg%n"');
    Lines.Add('  file:');
    Lines.Add('    name: ' + AppDir + '\logs\atheneo-api.log');
    Lines.Add('    max-size: 10MB');
    Lines.Add('    max-history: 30');

    ForceDirectories(ExtractFilePath(DestPath));
    Lines.SaveToFile(DestPath);

    Log('application.yml ecrit : ' + DestPath);
  finally
    Lines.Free;
  end;
end;

{ ── ServiceExists ───────────────────────────────────────────
  Retourne True si la tache planifiee AtheneoOutlookAPI existe.
  ─────────────────────────────────────────────────────────── }
function ServiceExists: Boolean;
var
  ResultCode: Integer;
begin
  Result := Exec(ExpandConstant('{sys}\schtasks.exe'),
                 '/query /tn "{#ServiceName}"',
                 '', SW_HIDE, ewWaitUntilTerminated, ResultCode)
            and (ResultCode = 0);
end;

{ ── GetPortValue ─────────────────────────────────────────────
  Fonction de rappel pour les constantes code: dans [Icons].
  ─────────────────────────────────────────────────────────── }
function GetPortValue(Param: String): String;
begin
  if Assigned(EdtPort) and (Trim(EdtPort.Text) <> '') then
    Result := Trim(EdtPort.Text)
  else
    Result := '8080';
end;

{ ── InitializeSetup ─────────────────────────────────────────
  Verifie la presence de Java 21+ avant d'ouvrir le wizard.
  ─────────────────────────────────────────────────────────── }
function InitializeSetup: Boolean;
var
  JavaVer: String;
begin
  Result := True;

  if not RegQueryStringValue(HKLM, 'SOFTWARE\JavaSoft\JDK', 'CurrentVersion', JavaVer) then
    if not RegQueryStringValue(HKLM64, 'SOFTWARE\JavaSoft\JDK', 'CurrentVersion', JavaVer) then
      JavaVer := '';

  if JavaVer = '' then
  begin
    if MsgBox(CustomMessage('JavaNotFound'), mbConfirmation, MB_YESNO) = IDNO then
      Result := False;
  end;
end;

{ ── InitializeWizard ────────────────────────────────────────
  Cree la page de configuration et pre-remplit les champs si
  une installation precedente est detectee via le registre.

  IMPORTANT : appn'est PAS encore disponible ici car le
  wizard n'a pas demande le repertoire d'installation.
  On utilise le registre pour trouver le chemin precedent.
  ─────────────────────────────────────────────────────────── }
procedure InitializeWizard;
var
  PrevInstDir: String;
  ExistingYml: String;
  LblInfo:     TNewStaticText;
  LblPort:     TNewStaticText;
  LblDBServer: TNewStaticText;
  LblDBName:   TNewStaticText;
  LblDBUser:   TNewStaticText;
  LblDBPass:   TNewStaticText;
  LblCors:     TNewStaticText;
  Y:           Integer;
  RegKey:      String;
begin
  { Valeurs par defaut }
  DefaultPort     := '8080';
  DefaultDBServer := '';
  DefaultDBName   := 'ATH_ERP_V17_1';
  DefaultDBUser   := '';
  DefaultDBPass   := '';
  DefaultCors     := '*';
  PrevConfigFound := False;

  { Recherche du repertoire d'installation precedent dans le registre.
    Inno Setup stocke InstallLocation sous la cle Uninstall avec le GUID. }
  RegKey := 'SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\{#AppID}_is1';
  PrevInstDir := '';

  if not RegQueryStringValue(HKLM, RegKey, 'InstallLocation', PrevInstDir) then
    RegQueryStringValue(HKLM64, RegKey, 'InstallLocation', PrevInstDir);

  { Si une installation precedente est trouvee, lire le YAML existant }
  if PrevInstDir <> '' then
  begin
    { Inno Setup ajoute un \ final a InstallLocation }
    if Copy(PrevInstDir, Length(PrevInstDir), 1) <> '\' then
      PrevInstDir := PrevInstDir + '\';
    ExistingYml := PrevInstDir + 'config\application.yml';

    if FileExists(ExistingYml) then
    begin
      PrevConfigFound := True;
      Log('Configuration existante trouvee : ' + ExistingYml);

      { Lecture du port }
      DefaultPort := ReadYamlValue(ExistingYml, 'port');
      if DefaultPort = '' then DefaultPort := '8080';

      { Lecture de l'URL JDBC et extraction du nom de serveur }
      DefaultDBServer := ReadYamlValue(ExistingYml, 'url');
      if Pos('sqlserver://', DefaultDBServer) > 0 then
      begin
        DefaultDBServer := Copy(DefaultDBServer,
          Pos('sqlserver://', DefaultDBServer) + Length('sqlserver://'), MaxInt);
        if Pos(';', DefaultDBServer) > 0 then
          DefaultDBServer := Copy(DefaultDBServer, 1, Pos(';', DefaultDBServer) - 1);
      end
      else
        DefaultDBServer := '';

      { Lecture du nom de base }
      DefaultDBName := ReadYamlValue(ExistingYml, 'databaseName');
      if DefaultDBName = '' then DefaultDBName := 'ATH_ERP_V17_1';

      { Lecture identifiants SQL }
      DefaultDBUser := ReadYamlValue(ExistingYml, 'username');
      DefaultDBPass := ReadYamlValue(ExistingYml, 'password');

      { Lecture CORS }
      DefaultCors := ReadYamlValue(ExistingYml, 'origins');
      if DefaultCors = '' then DefaultCors := '*';
    end;
  end;

  { ── Creation de la page de configuration ── }
  PageConfig := CreateCustomPage(
    wpSelectComponents,
    'Configuration de l''API ATHENEO',
    'Parametres de connexion et d''ecoute');

  Y := 8;

  { Bandeau info si reinstallation }
  if PrevConfigFound then
  begin
    LblInfo            := TNewStaticText.Create(PageConfig);
    LblInfo.Parent     := PageConfig.Surface;
    LblInfo.Caption    := 'Installation precedente detectee - valeurs pre-remplies depuis la configuration existante.';
    LblInfo.Font.Color := clNavy;
    LblInfo.Font.Style := [fsBold];
    LblInfo.WordWrap   := True;
    LblInfo.SetBounds(0, Y, PageConfig.SurfaceWidth, 26);
    Y := Y + 30;
  end;

  { Port }
  LblPort        := TNewStaticText.Create(PageConfig);
  LblPort.Parent := PageConfig.Surface;
  LblPort.Caption := CustomMessage('LabelPort');
  LblPort.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;

  EdtPort        := TNewEdit.Create(PageConfig);
  EdtPort.Parent := PageConfig.Surface;
  EdtPort.Text   := DefaultPort;
  EdtPort.SetBounds(0, Y, 90, 21);
  Y := Y + 28;

  { Serveur SQL }
  LblDBServer        := TNewStaticText.Create(PageConfig);
  LblDBServer.Parent := PageConfig.Surface;
  LblDBServer.Caption := CustomMessage('LabelDBServer');
  LblDBServer.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;

  EdtDBServer        := TNewEdit.Create(PageConfig);
  EdtDBServer.Parent := PageConfig.Surface;
  EdtDBServer.Text   := DefaultDBServer;
  EdtDBServer.SetBounds(0, Y, PageConfig.SurfaceWidth * 2 div 3, 21);
  Y := Y + 28;

  { Base de donnees }
  LblDBName        := TNewStaticText.Create(PageConfig);
  LblDBName.Parent := PageConfig.Surface;
  LblDBName.Caption := CustomMessage('LabelDBName');
  LblDBName.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;

  EdtDBName        := TNewEdit.Create(PageConfig);
  EdtDBName.Parent := PageConfig.Surface;
  EdtDBName.Text   := DefaultDBName;
  EdtDBName.SetBounds(0, Y, PageConfig.SurfaceWidth * 2 div 3, 21);
  Y := Y + 28;

  { Utilisateur SQL }
  LblDBUser        := TNewStaticText.Create(PageConfig);
  LblDBUser.Parent := PageConfig.Surface;
  LblDBUser.Caption := CustomMessage('LabelDBUser');
  LblDBUser.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;

  EdtDBUser        := TNewEdit.Create(PageConfig);
  EdtDBUser.Parent := PageConfig.Surface;
  EdtDBUser.Text   := DefaultDBUser;
  EdtDBUser.SetBounds(0, Y, PageConfig.SurfaceWidth * 2 div 3, 21);
  Y := Y + 28;

  { Mot de passe SQL }
  LblDBPass        := TNewStaticText.Create(PageConfig);
  LblDBPass.Parent := PageConfig.Surface;
  LblDBPass.Caption := CustomMessage('LabelDBPass');
  LblDBPass.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;

  EdtDBPass              := TNewEdit.Create(PageConfig);
  EdtDBPass.Parent       := PageConfig.Surface;
  EdtDBPass.Text         := DefaultDBPass;
  EdtDBPass.PasswordChar := '*';
  EdtDBPass.SetBounds(0, Y, PageConfig.SurfaceWidth * 2 div 3, 21);
  Y := Y + 28;

  { Origines CORS }
  LblCors        := TNewStaticText.Create(PageConfig);
  LblCors.Parent := PageConfig.Surface;
  LblCors.Caption := CustomMessage('LabelCors');
  LblCors.SetBounds(0, Y, PageConfig.SurfaceWidth, 13);
  Y := Y + 16;

  EdtCors        := TNewEdit.Create(PageConfig);
  EdtCors.Parent := PageConfig.Surface;
  EdtCors.Text   := DefaultCors;
  EdtCors.SetBounds(0, Y, PageConfig.SurfaceWidth, 21);
end;

{ ── NextButtonClick ─────────────────────────────────────────
  Validation de la page de configuration :
  - Le port doit etre un entier entre 1024 et 65535.
  ─────────────────────────────────────────────────────────── }
function NextButtonClick(CurPageID: Integer): Boolean;
var
  Port: Integer;
begin
  Result := True;

  if CurPageID = PageConfig.ID then
  begin
    if Trim(EdtPort.Text) <> '' then
    begin
      Port := StrToIntDef(Trim(EdtPort.Text), 0);
      if (Port < 1024) or (Port > 65535) then
      begin
        MsgBox('Le port doit etre un nombre entre 1024 et 65535.', mbError, MB_OK);
        Result := False;
        Exit;
      end;
    end;
  end;
end;

{ ── CurStepChanged ──────────────────────────────────────────
  Ecriture du fichier application.yml apres copie des fichiers.
  A ce stade, app est resolu et le dossier de destination existe.
  ─────────────────────────────────────────────────────────── }
procedure CurStepChanged(CurStep: TSetupStep);
var
  YmlPath: String;
begin
  if CurStep = ssPostInstall then
  begin
    YmlPath := ExpandConstant('{app}\config\application.yml');

    if (not PrevConfigFound) or IsTaskSelected('resetconfig') then
      WriteApplicationYml(YmlPath)
    else
      Log('Configuration precedente conservee : ' + YmlPath);
  end;
end;
