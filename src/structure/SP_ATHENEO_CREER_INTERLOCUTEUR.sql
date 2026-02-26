IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_CREER_INTERLOCUTEUR]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_CREER_INTERLOCUTEUR]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_CREER_INTERLOCUTEUR

No Version : 001

Description :
Cree automatiquement un nouvel interlocuteur dans la table INTERLOC
a partir des informations d'un expediteur email Outlook.
Si un interlocuteur avec le meme email existe deja, retourne son ID
sans creer de doublon (comportement idempotent).

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

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
