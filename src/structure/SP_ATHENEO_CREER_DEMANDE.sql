IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_CREER_DEMANDE]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_CREER_DEMANDE]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_CREER_DEMANDE

No Version : 001

Description :
Cree une nouvelle demande dans la table DEMANDE_P de la base ATHENEO.
Genere une reference unique au format DEM-YYYY-XXXXX.
Stocke les informations metier dans les champs libres (C1-C8) de DEMANDE_P :
  C1 = Titre
  C2 = Statut
  C3 = Priorite
  C4 = Type
  C5 = Source
  C6 = Email contact
  C7 = Nom contact
  C8 = Reference (DEM-YYYY-XXXXX)
Retourne l'ID et la reference de la demande creee.

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

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
