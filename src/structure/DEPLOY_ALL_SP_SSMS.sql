/* ============================================================
   ATHENEO ERP - Script de deploiement SSMS (tout-en-un)
   Procedures stockees Outlook Add-in

   Version     : 001
   Auteur      : VABE
   Date        : 26/02/2026

   UTILISATION SSMS :
     1. Ouvrez ce fichier dans SQL Server Management Studio
     2. Selectionnez la base cible dans la liste deroulante
        (ou ajoutez USE [ATH_ERP_V17_1]; GO en debut de script)
     3. Executez (F5)

   Ce script est equivalent a MASTER_DEPLOY_SP.sql mais sans
   les directives :r de SQLCMD, afin de fonctionner directement
   dans SSMS sans configuration supplementaire.

   Ordre de deploiement (dependances respectees) :
     1.  SP_ATHENEO_STATS
     2.  SP_ATHENEO_RECHERCHER_INTERLOCUTEUR
     3.  SP_ATHENEO_CREER_INTERLOCUTEUR
     4.  SP_ATHENEO_ENREGISTRER_MAIL
     5.  SP_ATHENEO_LISTER_EMAILS
     6.  SP_ATHENEO_CREER_DEMANDE
     7.  SP_ATHENEO_LISTER_DEMANDES
     8.  SP_ATHENEO_CREER_ACTION
     9.  SP_ATHENEO_LISTER_ACTIONS
     10. SP_ATHENEO_ENREGISTRER_PIECE_JOINTE
============================================================ */

-- Decommenter la ligne ci-dessous si vous n'avez pas selectionne la base dans SSMS :
-- USE [ATH_ERP_V17_1];
-- GO

SET NOCOUNT ON;
GO

PRINT '============================================================';
PRINT ' ATHENEO - Deploiement Procedures Stockees Outlook Add-in';
PRINT ' Serveur  : ' + @@SERVERNAME;
PRINT ' Base     : ' + DB_NAME();
PRINT ' Debut    : ' + CONVERT(varchar(20), GETDATE(), 120);
PRINT '============================================================';
PRINT '';
GO


/* ============================================================
   1/10  SP_ATHENEO_STATS
         Stats globales pour le endpoint /api/health
   ============================================================ */

PRINT '[1/10] SP_ATHENEO_STATS ...';
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_STATS]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_STATS]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE [SP_ATHENEO_STATS]
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_STATS]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_STATS
END
ELSE
BEGIN
    SET NOCOUNT ON;

    SELECT
        (SELECT COUNT(*) FROM MAIL        WHERE SOURCE = 'OUTLOOK_ADDIN')   AS totalEmails,
        (SELECT COUNT(*) FROM DEMANDE_P   WHERE C5     = 'outlook_addin')   AS totalDemandes,
        (SELECT COUNT(*) FROM INTERLOC    WHERE SOURCE = 'OUTLOOK_ADDIN')   AS totalInterlocuteurs,
        (SELECT COUNT(*) FROM ACTION      WHERE SOURCE = 'OUTLOOK_ADDIN')   AS totalActions,
        (SELECT COUNT(*) FROM PIECE
            WHERE CREER_PAR IN (
                SELECT DISTINCT LEFT(CREER_PAR, 10) FROM MAIL
                WHERE SOURCE = 'OUTLOOK_ADDIN'
            )
        )                                                                    AS totalPiecesJointes,
        GETDATE()                                                            AS dateConsultation

END
GO

PRINT '       -> OK';
PRINT '';
GO


/* ============================================================
   2/10  SP_ATHENEO_RECHERCHER_INTERLOCUTEUR
         Recherche un interlocuteur par email (SELECT INTERLOC)
   ============================================================ */

PRINT '[2/10] SP_ATHENEO_RECHERCHER_INTERLOCUTEUR ...';
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_RECHERCHER_INTERLOCUTEUR]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_RECHERCHER_INTERLOCUTEUR]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE [SP_ATHENEO_RECHERCHER_INTERLOCUTEUR]
    @EMAIL varchar(100)
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_RECHERCHER_INTERLOCUTEUR]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_RECHERCHER_INTERLOCUTEUR @EMAIL
END
ELSE
BEGIN
    SET NOCOUNT ON;

    SELECT
        I.NO_INTERLO            AS id,
        I.NOM                   AS nom,
        I.PRENOM                AS prenom,
        I.E_MAIL                AS email,
        I.TELEPHONE             AS telephone,
        I.TELPORTAB             AS telephonePortable,
        S.NOM                   AS societe,
        I.NO_SOCIETE            AS noSociete,
        I.CREER_LE              AS dateCreation,
        I.MODIF_LE              AS dateModification,
        I.SOURCE                AS source,
        I.NOM_SOCIETE_OUTLOOK   AS nomSocieteOutlook,
        I.ACTIF                 AS actif
    FROM INTERLOC I
    LEFT JOIN SOCIETE S ON S.NO_SOCIETE = I.NO_SOCIETE
    WHERE UPPER(LTRIM(RTRIM(I.E_MAIL))) = UPPER(LTRIM(RTRIM(@EMAIL)))
    AND ISNULL(I.ACTIF, 1) = 1

END
GO

PRINT '       -> OK';
PRINT '';
GO


/* ============================================================
   3/10  SP_ATHENEO_CREER_INTERLOCUTEUR
         Insert dans INTERLOC, idempotent sur E_MAIL
         Depend de : INTERLOC, SOCIETE
   ============================================================ */

PRINT '[3/10] SP_ATHENEO_CREER_INTERLOCUTEUR ...';
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_CREER_INTERLOCUTEUR]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_CREER_INTERLOCUTEUR]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE [SP_ATHENEO_CREER_INTERLOCUTEUR]
    @EMAIL          varchar(100),
    @NOM_COMPLET    varchar(255)    = NULL,
    @PRENOM         varchar(50)     = NULL,
    @NOM            varchar(150)    = NULL,
    @SOCIETE        varchar(100)    = NULL,
    @UTILISATEUR    varchar(10)     = NULL,
    @INTERLOCUTEUR_ID int           OUTPUT
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_CREER_INTERLOCUTEUR]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_CREER_INTERLOCUTEUR
        @EMAIL,
        @NOM_COMPLET,
        @PRENOM,
        @NOM,
        @SOCIETE,
        @UTILISATEUR,
        @INTERLOCUTEUR_ID OUTPUT
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_NO_INTERLO   int
    DECLARE @V_NOM          varchar(150)
    DECLARE @V_PRENOM       varchar(50)
    DECLARE @V_UTILISATEUR  char(10)

    SET @V_UTILISATEUR = ISNULL(LEFT(@UTILISATEUR, 10), 'OUTLOOK')

    -- Verification : interlocuteur deja existant sur cet email
    SELECT @V_NO_INTERLO = NO_INTERLO
    FROM INTERLOC
    WHERE UPPER(LTRIM(RTRIM(E_MAIL))) = UPPER(LTRIM(RTRIM(@EMAIL)))
    AND ISNULL(ACTIF, 1) = 1

    IF @V_NO_INTERLO IS NOT NULL
    BEGIN
        -- Interlocuteur existant : retourner son ID sans creation
        SET @INTERLOCUTEUR_ID = @V_NO_INTERLO
        RETURN
    END

    -- Decomposition NOM / PRENOM si NOM_COMPLET fourni sans NOM/PRENOM explicites
    IF @NOM IS NULL AND @PRENOM IS NULL AND @NOM_COMPLET IS NOT NULL
    BEGIN
        DECLARE @V_ESPACE int
        SET @V_ESPACE = CHARINDEX(' ', LTRIM(RTRIM(@NOM_COMPLET)))
        IF @V_ESPACE > 0
        BEGIN
            SET @V_PRENOM = LEFT(LTRIM(RTRIM(@NOM_COMPLET)), @V_ESPACE - 1)
            SET @V_NOM    = SUBSTRING(LTRIM(RTRIM(@NOM_COMPLET)), @V_ESPACE + 1, 150)
        END
        ELSE
        BEGIN
            SET @V_PRENOM = NULL
            SET @V_NOM    = LTRIM(RTRIM(@NOM_COMPLET))
        END
    END
    ELSE
    BEGIN
        SET @V_NOM    = @NOM
        SET @V_PRENOM = @PRENOM
    END

    -- Extraction de la societe a partir du domaine email si non fournie
    DECLARE @V_SOCIETE varchar(100)
    IF @SOCIETE IS NOT NULL
        SET @V_SOCIETE = @SOCIETE
    ELSE
    BEGIN
        DECLARE @V_AROBASE int
        SET @V_AROBASE = CHARINDEX('@', @EMAIL)
        IF @V_AROBASE > 0
            SET @V_SOCIETE = UPPER(LEFT(SUBSTRING(@EMAIL, @V_AROBASE + 1, 100),
                                   CHARINDEX('.', SUBSTRING(@EMAIL, @V_AROBASE + 1, 100)) - 1))
        ELSE
            SET @V_SOCIETE = NULL
    END

    -- Insertion du nouvel interlocuteur
    INSERT INTO INTERLOC (
        NOM,
        PRENOM,
        E_MAIL,
        DATECREAT,
        CREER_LE,
        CREER_PAR,
        MODIF_LE,
        MODIF_PAR,
        ACTIF,
        MODIFIABLE,
        SOURCE
    )
    VALUES (
        ISNULL(@V_NOM, ''),
        @V_PRENOM,
        LTRIM(RTRIM(@EMAIL)),
        GETDATE(),
        GETDATE(),
        @V_UTILISATEUR,
        GETDATE(),
        @V_UTILISATEUR,
        1,
        1,
        'OUTLOOK_ADDIN'
    )

    SET @INTERLOCUTEUR_ID = SCOPE_IDENTITY()

    -- Mise a jour NOM_SOCIETE_OUTLOOK si societe detectee
    IF @V_SOCIETE IS NOT NULL
    BEGIN
        UPDATE INTERLOC
        SET NOM_SOCIETE_OUTLOOK = @V_SOCIETE
        WHERE NO_INTERLO = @INTERLOCUTEUR_ID
    END

END
GO

PRINT '       -> OK';
PRINT '';
GO


/* ============================================================
   4/10  SP_ATHENEO_ENREGISTRER_MAIL
         Insert dans MAIL, lien automatique NO_INTERLO
         Depend de : MAIL, INTERLOC
   ============================================================ */

PRINT '[4/10] SP_ATHENEO_ENREGISTRER_MAIL ...';
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_ENREGISTRER_MAIL]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_ENREGISTRER_MAIL]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE [SP_ATHENEO_ENREGISTRER_MAIL]
    @EXPEDITEUR         varchar(200),
    @EXPEDITEUR_NOM     varchar(200)    = NULL,
    @SUJET              varchar(200)    = NULL,
    @CONTENU            text            = NULL,
    @DATE_RECEPTION     datetime        = NULL,
    @UTILISATEUR        varchar(10)     = NULL,
    @EMAIL_ID           int             OUTPUT
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_ENREGISTRER_MAIL]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_ENREGISTRER_MAIL
        @EXPEDITEUR,
        @EXPEDITEUR_NOM,
        @SUJET,
        @CONTENU,
        @DATE_RECEPTION,
        @UTILISATEUR,
        @EMAIL_ID OUTPUT
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_NO_INTERLO   int
    DECLARE @V_UTILISATEUR  char(10)
    DECLARE @V_DATE_ENV     datetime

    SET @V_UTILISATEUR = ISNULL(LEFT(@UTILISATEUR, 10), 'OUTLOOK')
    SET @V_DATE_ENV    = ISNULL(@DATE_RECEPTION, GETDATE())

    -- Recherche de l'interlocuteur correspondant a l'expediteur
    SELECT TOP 1 @V_NO_INTERLO = NO_INTERLO
    FROM INTERLOC
    WHERE UPPER(LTRIM(RTRIM(E_MAIL))) = UPPER(LTRIM(RTRIM(@EXPEDITEUR)))
    AND ISNULL(ACTIF, 1) = 1

    -- Insertion du mail dans la table MAIL
    INSERT INTO MAIL (
        NO_INTERLO,
        OBJET,
        CORPS,
        EXPEDITEUR,
        ENVOYER_LE,
        SORTANT,
        CREER_LE,
        CREER_PAR,
        MODIF_LE,
        MODIF_PAR,
        MODIFIABLE,
        C1,
        SOURCE
    )
    VALUES (
        @V_NO_INTERLO,
        LEFT(ISNULL(@SUJET, ''), 200),
        @CONTENU,
        LEFT(ISNULL(@EXPEDITEUR, ''), 200),
        @V_DATE_ENV,
        0,              -- 0 = mail recu (entrant)
        GETDATE(),
        @V_UTILISATEUR,
        GETDATE(),
        @V_UTILISATEUR,
        1,
        LEFT(ISNULL(@EXPEDITEUR_NOM, ''), 100),  -- C1 = nom expediteur
        'OUTLOOK_ADDIN'
    )

    SET @EMAIL_ID = SCOPE_IDENTITY()

END
GO

PRINT '       -> OK';
PRINT '';
GO


/* ============================================================
   5/10  SP_ATHENEO_LISTER_EMAILS
         Select MAIL filtre SOURCE = OUTLOOK_ADDIN
         Depend de : MAIL, INTERLOC
   ============================================================ */

PRINT '[5/10] SP_ATHENEO_LISTER_EMAILS ...';
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_LISTER_EMAILS]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_LISTER_EMAILS]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE [SP_ATHENEO_LISTER_EMAILS]
    @DATE_DEBUT     datetime    = NULL,
    @DATE_FIN       datetime    = NULL,
    @EXPEDITEUR     varchar(200) = NULL,
    @LIMITE         int         = 100
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_LISTER_EMAILS]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_LISTER_EMAILS
        @DATE_DEBUT,
        @DATE_FIN,
        @EXPEDITEUR,
        @LIMITE
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_LIMITE int
    SET @V_LIMITE = ISNULL(@LIMITE, 100)
    IF @V_LIMITE > 1000 SET @V_LIMITE = 1000

    SELECT TOP (@V_LIMITE)
        M.NO_MAIL           AS id,
        M.EXPEDITEUR        AS expediteur,
        M.C1                AS expediteurNom,
        M.OBJET             AS sujet,
        M.CORPS             AS contenu,
        M.ENVOYER_LE        AS dateReception,
        M.CREER_LE          AS dateCreation,
        M.NO_INTERLO        AS idInterlocuteur,
        I.NOM               AS interlocuteurNom,
        I.PRENOM            AS interlocuteurPrenom
    FROM MAIL M
    LEFT JOIN INTERLOC I ON I.NO_INTERLO = M.NO_INTERLO
    WHERE M.SOURCE = 'OUTLOOK_ADDIN'
    AND (@DATE_DEBUT IS NULL OR M.CREER_LE >= @DATE_DEBUT)
    AND (@DATE_FIN   IS NULL OR M.CREER_LE <= @DATE_FIN)
    AND (@EXPEDITEUR IS NULL OR UPPER(M.EXPEDITEUR) LIKE '%' + UPPER(@EXPEDITEUR) + '%')
    ORDER BY M.CREER_LE DESC

END
GO

PRINT '       -> OK';
PRINT '';
GO


/* ============================================================
   6/10  SP_ATHENEO_CREER_DEMANDE
         Insert dans DEMANDE_P, reference DEM-YYYY-XXXXX
         Depend de : DEMANDE_P, INTERLOC
   ============================================================ */

PRINT '[6/10] SP_ATHENEO_CREER_DEMANDE ...';
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_CREER_DEMANDE]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_CREER_DEMANDE]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE [SP_ATHENEO_CREER_DEMANDE]
    @ID_INTERLOCUTEUR   int,
    @EMAIL_CONTACT      varchar(200),
    @CONTACT_NOM        varchar(200)    = NULL,
    @TITRE              varchar(254)    = NULL,
    @DESCRIPTION        text            = NULL,
    @SOURCE             varchar(50)     = 'outlook_addin',
    @PRIORITE           varchar(20)     = 'normale',
    @TYPE               varchar(50)     = 'email',
    @UTILISATEUR        varchar(10)     = NULL,
    @DEMANDE_ID         int             OUTPUT,
    @REFERENCE          varchar(50)     OUTPUT
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_CREER_DEMANDE]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_CREER_DEMANDE
        @ID_INTERLOCUTEUR,
        @EMAIL_CONTACT,
        @CONTACT_NOM,
        @TITRE,
        @DESCRIPTION,
        @SOURCE,
        @PRIORITE,
        @TYPE,
        @UTILISATEUR,
        @DEMANDE_ID OUTPUT,
        @REFERENCE  OUTPUT
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_UTILISATEUR  char(10)
    DECLARE @V_ANNEE        char(4)
    DECLARE @V_SEQUENCE     int
    DECLARE @V_REFERENCE    varchar(50)
    DECLARE @V_NO_SOCIETE   int

    SET @V_UTILISATEUR = ISNULL(LEFT(@UTILISATEUR, 10), 'OUTLOOK')
    SET @V_ANNEE       = CAST(YEAR(GETDATE()) AS char(4))

    -- Recuperation du NO_SOCIETE de l'interlocuteur
    SELECT @V_NO_SOCIETE = NO_SOCIETE
    FROM INTERLOC
    WHERE NO_INTERLO = @ID_INTERLOCUTEUR

    -- Generation de la reference unique DEM-YYYY-XXXXX
    SELECT @V_SEQUENCE = ISNULL(MAX(CAST(RIGHT(C8, 5) AS int)), 0) + 1
    FROM DEMANDE_P
    WHERE C8 LIKE 'DEM-' + @V_ANNEE + '-%'
    AND ISNUMERIC(RIGHT(C8, 5)) = 1

    SET @V_REFERENCE = 'DEM-' + @V_ANNEE + '-' + RIGHT('00000' + CAST(@V_SEQUENCE AS varchar(5)), 5)

    -- Insertion de la demande
    INSERT INTO DEMANDE_P (
        NO_INTERLO,
        NO_SOCIETE,
        DATE_DEMANDE_P,
        DEMANDE_PAR,
        MEMO,
        CREER_LE,
        CREER_PAR,
        MODIF_LE,
        MODIF_PAR,
        MODIFIABLE,
        C1,
        C2,
        C3,
        C4,
        C5,
        C6,
        C7,
        C8
    )
    VALUES (
        @ID_INTERLOCUTEUR,
        @V_NO_SOCIETE,
        GETDATE(),
        @V_UTILISATEUR,
        @DESCRIPTION,
        GETDATE(),
        @V_UTILISATEUR,
        GETDATE(),
        @V_UTILISATEUR,
        1,
        LEFT(ISNULL(@TITRE, ''), 254),          -- C1 = Titre
        LEFT(ISNULL('nouvelle', ''), 254),       -- C2 = Statut initial
        LEFT(ISNULL(@PRIORITE, 'normale'), 254), -- C3 = Priorite
        LEFT(ISNULL(@TYPE, 'email'), 254),       -- C4 = Type
        LEFT(ISNULL(@SOURCE, ''), 254),          -- C5 = Source
        LEFT(ISNULL(@EMAIL_CONTACT, ''), 254),   -- C6 = Email contact
        LEFT(ISNULL(@CONTACT_NOM, ''), 254),     -- C7 = Nom contact
        @V_REFERENCE                             -- C8 = Reference
    )

    SET @DEMANDE_ID = SCOPE_IDENTITY()
    SET @REFERENCE  = @V_REFERENCE

END
GO

PRINT '       -> OK';
PRINT '';
GO


/* ============================================================
   7/10  SP_ATHENEO_LISTER_DEMANDES
         Select DEMANDE_P filtre C5 = outlook_addin
         Depend de : DEMANDE_P, INTERLOC
   ============================================================ */

PRINT '[7/10] SP_ATHENEO_LISTER_DEMANDES ...';
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_LISTER_DEMANDES]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_LISTER_DEMANDES]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE [SP_ATHENEO_LISTER_DEMANDES]
    @STATUT             varchar(20)     = NULL,
    @PRIORITE           varchar(20)     = NULL,
    @TYPE               varchar(50)     = NULL,
    @ID_INTERLOCUTEUR   int             = NULL,
    @LIMITE             int             = 100
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_LISTER_DEMANDES]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_LISTER_DEMANDES
        @STATUT,
        @PRIORITE,
        @TYPE,
        @ID_INTERLOCUTEUR,
        @LIMITE
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_LIMITE int
    SET @V_LIMITE = ISNULL(@LIMITE, 100)
    IF @V_LIMITE > 1000 SET @V_LIMITE = 1000

    SELECT TOP (@V_LIMITE)
        D.NO_DEMANDE_P      AS id,
        D.C8                AS reference,
        D.NO_INTERLO        AS idInterlocuteur,
        D.C6                AS emailContact,
        D.C7                AS contactNom,
        D.C1                AS titre,
        D.MEMO              AS description,
        D.C2                AS statut,
        D.C3                AS priorite,
        D.C4                AS type,
        D.C5                AS source,
        D.DATE_DEMANDE_P    AS dateCreation,
        I.NOM               AS interlocuteurNom,
        I.PRENOM            AS interlocuteurPrenom,
        I.E_MAIL            AS interlocuteurEmail
    FROM DEMANDE_P D
    LEFT JOIN INTERLOC I ON I.NO_INTERLO = D.NO_INTERLO
    WHERE D.C5 = 'outlook_addin'
    AND (@STATUT           IS NULL OR UPPER(D.C2) = UPPER(@STATUT))
    AND (@PRIORITE         IS NULL OR UPPER(D.C3) = UPPER(@PRIORITE))
    AND (@TYPE             IS NULL OR UPPER(D.C4) = UPPER(@TYPE))
    AND (@ID_INTERLOCUTEUR IS NULL OR D.NO_INTERLO = @ID_INTERLOCUTEUR)
    ORDER BY D.DATE_DEMANDE_P DESC

END
GO

PRINT '       -> OK';
PRINT '';
GO


/* ============================================================
   8/10  SP_ATHENEO_CREER_ACTION
         Insert dans ACTION, reference ACT-YYYY-XXXXX
         Depend de : ACTION, INTERLOC
   ============================================================ */

PRINT '[8/10] SP_ATHENEO_CREER_ACTION ...';
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_CREER_ACTION]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_CREER_ACTION]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE [SP_ATHENEO_CREER_ACTION]
    @ID_INTERLOCUTEUR   int,
    @EMAIL_CONTACT      varchar(200),
    @CONTACT_NOM        varchar(200)    = NULL,
    @TITRE              varchar(200)    = NULL,
    @DESCRIPTION        text            = NULL,
    @TYPE               varchar(10)     = 'OUTLOOK',
    @PRIORITE           varchar(10)     = 'NORM',
    @STATUT             varchar(10)     = 'AFAIRE',
    @SOURCE             varchar(50)     = 'outlook_addin',
    @UTILISATEUR        varchar(10)     = NULL,
    @ACTION_ID          int             OUTPUT,
    @REFERENCE          varchar(50)     OUTPUT
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_CREER_ACTION]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_CREER_ACTION
        @ID_INTERLOCUTEUR,
        @EMAIL_CONTACT,
        @CONTACT_NOM,
        @TITRE,
        @DESCRIPTION,
        @TYPE,
        @PRIORITE,
        @STATUT,
        @SOURCE,
        @UTILISATEUR,
        @ACTION_ID  OUTPUT,
        @REFERENCE  OUTPUT
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_UTILISATEUR  char(10)
    DECLARE @V_ANNEE        char(4)
    DECLARE @V_SEQUENCE     int
    DECLARE @V_REFERENCE    varchar(50)
    DECLARE @V_NO_SOCIETE   int

    SET @V_UTILISATEUR = ISNULL(LEFT(@UTILISATEUR, 10), 'OUTLOOK')
    SET @V_ANNEE       = CAST(YEAR(GETDATE()) AS char(4))

    -- Recuperation du NO_SOCIETE de l'interlocuteur
    SELECT @V_NO_SOCIETE = NO_SOCIETE
    FROM INTERLOC
    WHERE NO_INTERLO = @ID_INTERLOCUTEUR

    -- Generation de la reference unique ACT-YYYY-XXXXX
    SELECT @V_SEQUENCE = ISNULL(MAX(CAST(RIGHT(C4, 5) AS int)), 0) + 1
    FROM ACTION
    WHERE C4 LIKE 'ACT-' + @V_ANNEE + '-%'
    AND ISNUMERIC(RIGHT(C4, 5)) = 1

    SET @V_REFERENCE = 'ACT-' + @V_ANNEE + '-' + RIGHT('00000' + CAST(@V_SEQUENCE AS varchar(5)), 5)

    -- Insertion de l'action
    INSERT INTO ACTION (
        NO_INTERLO,
        NO_SOCIETE,
        COMPTE_ACT,
        MEMO,
        CD_T_AC_PA,
        COD_PRIORITE,
        CO_T_CO_PA,
        DAT_ACT_PR,
        COD_USER,
        CREER_LE,
        CREER_PAR,
        MODIF_LE,
        MODIF_PAR,
        MODIFIABLE,
        C1,
        C2,
        C3,
        C4,
        SOURCE
    )
    VALUES (
        @ID_INTERLOCUTEUR,
        @V_NO_SOCIETE,
        LEFT(ISNULL(@TITRE, ''), 200),          -- COMPTE_ACT = Titre
        @DESCRIPTION,                            -- MEMO = Description
        LEFT(ISNULL(@TYPE, 'OUTLOOK'), 10),      -- CD_T_AC_PA = Type
        LEFT(ISNULL(@PRIORITE, 'NORM'), 10),     -- COD_PRIORITE = Priorite
        LEFT(ISNULL(@STATUT, 'AFAIRE'), 10),     -- CO_T_CO_PA = Statut/conclusion
        GETDATE(),                               -- DAT_ACT_PR = Date prevue
        @V_UTILISATEUR,
        GETDATE(),
        @V_UTILISATEUR,
        GETDATE(),
        @V_UTILISATEUR,
        1,
        LEFT(ISNULL(@EMAIL_CONTACT, ''), 100),   -- C1 = Email contact
        LEFT(ISNULL(@CONTACT_NOM, ''), 100),     -- C2 = Nom contact
        LEFT(ISNULL(@SOURCE, ''), 100),          -- C3 = Source
        @V_REFERENCE,                            -- C4 = Reference
        'OUTLOOK_ADDIN'
    )

    SET @ACTION_ID = SCOPE_IDENTITY()
    SET @REFERENCE = @V_REFERENCE

END
GO

PRINT '       -> OK';
PRINT '';
GO


/* ============================================================
   9/10  SP_ATHENEO_LISTER_ACTIONS
         Select ACTION filtre SOURCE = OUTLOOK_ADDIN
         Depend de : ACTION, INTERLOC
   ============================================================ */

PRINT '[9/10] SP_ATHENEO_LISTER_ACTIONS ...';
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_LISTER_ACTIONS]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_LISTER_ACTIONS]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE [SP_ATHENEO_LISTER_ACTIONS]
    @STATUT             varchar(10)     = NULL,
    @PRIORITE           varchar(10)     = NULL,
    @TYPE               varchar(10)     = NULL,
    @ID_INTERLOCUTEUR   int             = NULL,
    @LIMITE             int             = 100
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_LISTER_ACTIONS]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_LISTER_ACTIONS
        @STATUT,
        @PRIORITE,
        @TYPE,
        @ID_INTERLOCUTEUR,
        @LIMITE
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_LIMITE int
    SET @V_LIMITE = ISNULL(@LIMITE, 100)
    IF @V_LIMITE > 1000 SET @V_LIMITE = 1000

    SELECT TOP (@V_LIMITE)
        A.NO_ACTION         AS id,
        A.C4                AS reference,
        A.NO_INTERLO        AS idInterlocuteur,
        A.C1                AS emailContact,
        A.C2                AS contactNom,
        A.COMPTE_ACT        AS titre,
        A.MEMO              AS description,
        A.CD_T_AC_PA        AS type,
        A.COD_PRIORITE      AS priorite,
        A.CO_T_CO_PA        AS statut,
        A.C3                AS source,
        A.DAT_ACT_PR        AS datePrevue,
        A.CREER_LE          AS dateCreation,
        I.NOM               AS interlocuteurNom,
        I.PRENOM            AS interlocuteurPrenom,
        I.E_MAIL            AS interlocuteurEmail
    FROM ACTION A
    LEFT JOIN INTERLOC I ON I.NO_INTERLO = A.NO_INTERLO
    WHERE A.SOURCE = 'OUTLOOK_ADDIN'
    AND (@STATUT           IS NULL OR UPPER(A.CO_T_CO_PA) = UPPER(@STATUT))
    AND (@PRIORITE         IS NULL OR UPPER(A.COD_PRIORITE) = UPPER(@PRIORITE))
    AND (@TYPE             IS NULL OR UPPER(A.CD_T_AC_PA) = UPPER(@TYPE))
    AND (@ID_INTERLOCUTEUR IS NULL OR A.NO_INTERLO = @ID_INTERLOCUTEUR)
    ORDER BY A.CREER_LE DESC

END
GO

PRINT '       -> OK';
PRINT '';
GO


/* ============================================================
   10/10 SP_ATHENEO_ENREGISTRER_PIECE_JOINTE
         Insert dans PIECE, lien interlocuteur via email
         Depend de : PIECE, INTERLOC
   ============================================================ */

PRINT '[10/10] SP_ATHENEO_ENREGISTRER_PIECE_JOINTE ...';
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_ENREGISTRER_PIECE_JOINTE]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_ENREGISTRER_PIECE_JOINTE]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE [SP_ATHENEO_ENREGISTRER_PIECE_JOINTE]
    @NOM_FICHIER        varchar(200),
    @TAILLE             bigint          = NULL,
    @TYPE_MIME          varchar(100)    = NULL,
    @ID_OUTLOOK         varchar(255)    = NULL,
    @EMAIL_SOURCE       varchar(200)    = NULL,
    @SUJET_MAIL         varchar(200)    = NULL,
    @UTILISATEUR        varchar(10)     = NULL,
    @PIECE_JOINTE_ID    int             OUTPUT
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_ENREGISTRER_PIECE_JOINTE]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_ENREGISTRER_PIECE_JOINTE
        @NOM_FICHIER,
        @TAILLE,
        @TYPE_MIME,
        @ID_OUTLOOK,
        @EMAIL_SOURCE,
        @SUJET_MAIL,
        @UTILISATEUR,
        @PIECE_JOINTE_ID OUTPUT
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_UTILISATEUR  char(10)
    DECLARE @V_REFERENCE    varchar(50)
    DECLARE @V_NO_INTERLO   int

    SET @V_UTILISATEUR = ISNULL(LEFT(@UTILISATEUR, 10), 'OUTLOOK')
    -- Reference = nom fichier tronque a 50 caracteres
    SET @V_REFERENCE   = LEFT(ISNULL(@NOM_FICHIER, ''), 50)

    -- Recherche de l'interlocuteur correspondant a l'email source
    IF @EMAIL_SOURCE IS NOT NULL
    BEGIN
        SELECT TOP 1 @V_NO_INTERLO = NO_INTERLO
        FROM INTERLOC
        WHERE UPPER(LTRIM(RTRIM(E_MAIL))) = UPPER(LTRIM(RTRIM(@EMAIL_SOURCE)))
        AND ISNULL(ACTIF, 1) = 1
    END

    -- Insertion de la piece jointe dans la table PIECE
    INSERT INTO PIECE (
        NO_INTERLO,
        REFERENCE,
        OBJET,
        CREER_LE,
        CREER_PAR,
        MODIF_LE,
        MODIF_PAR,
        MODIFIABLE,
        C1,
        C2,
        C3,
        C4,
        C5
    )
    VALUES (
        @V_NO_INTERLO,
        @V_REFERENCE,
        LEFT(ISNULL(@NOM_FICHIER, ''), 200),                 -- OBJET = nom complet fichier
        GETDATE(),
        @V_UTILISATEUR,
        GETDATE(),
        @V_UTILISATEUR,
        1,
        LEFT(ISNULL(@TYPE_MIME, ''), 100),                   -- C1 = Type MIME
        LEFT(ISNULL(CAST(@TAILLE AS varchar(20)), ''), 100), -- C2 = Taille en octets
        LEFT(ISNULL(@ID_OUTLOOK, ''), 100),                  -- C3 = ID Outlook
        LEFT(ISNULL(@EMAIL_SOURCE, ''), 100),                -- C4 = Email source
        LEFT(ISNULL(@SUJET_MAIL, ''), 100)                   -- C5 = Sujet du mail
    )

    SET @PIECE_JOINTE_ID = SCOPE_IDENTITY()

END
GO

PRINT '        -> OK';
PRINT '';
GO


/* ============================================================
   Verification finale : liste les procedures deployees
   ============================================================ */

PRINT '============================================================';
PRINT ' Verification - Procedures deployees :';
PRINT '============================================================';
GO

SELECT
    name                                    AS procedure_name,
    CONVERT(varchar(20), create_date, 120)  AS creee_le,
    CONVERT(varchar(20), modify_date, 120)  AS modifiee_le
FROM sys.objects
WHERE name LIKE 'SP_ATHENEO_%'
AND [type] = 'P'
AND is_ms_shipped = 0
ORDER BY name;
GO

PRINT '';
PRINT 'Deploiement termine le : ' + CONVERT(varchar(20), GETDATE(), 120);
PRINT '============================================================';
GO
