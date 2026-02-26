IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_CREER_ACTION]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_CREER_ACTION]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_CREER_ACTION

No Version : 001

Description :
Cree une nouvelle action de suivi dans la table ACTION de la base ATHENEO.
Genere une reference unique au format ACT-YYYY-XXXXX.
Mapping des champs metier vers ACTION :
  COMPTE_ACT  = Titre de l'action
  MEMO        = Description / corps
  CD_T_AC_PA  = Type (code type action, 10 car max)
  COD_PRIORITE= Priorite (code 10 car max)
  CO_T_CO_PA  = Statut / conclusion (code 10 car max)
  DAT_ACT_PR  = Date prevue (date creation)
  NO_INTERLO  = Lien vers l'interlocuteur
  C1          = Email contact
  C2          = Nom contact
  C3          = Source (outlook_addin)
  C4          = Reference (ACT-YYYY-XXXXX)
Retourne l'ID et la reference de l'action creee.

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

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
