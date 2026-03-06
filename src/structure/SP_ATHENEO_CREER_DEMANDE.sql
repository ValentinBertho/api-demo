IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_CREER_DEMANDE]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_CREER_DEMANDE]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_CREER_DEMANDE

No Version : 002

Description :
Cree un nouvel incident dans la table INCIDENT de la base ATHENEO.
Une "demande" dans le contexte Outlook Add-in correspond a un INCIDENT ATHENEO.

Generation des identifiants :
  - NO_INCIDENT    : via sp_COMPTEUR (compteur = 'NO_INCIDENT', pas = 1)
  - CHRONO_INCIDENT: via sp_generation_chrono (type piece, cod_site, no_piece, jour, mois, annee)

Mapping des champs metier :
  OBJET            = @TITRE
  MEMO_P           = @DESCRIPTION
  COD_ETAT         = 'NEW'        (statut initial)
  COD_PRIORITE     = @PRIORITE
  NO_SOCIETE       = recupere depuis INTERLOC
  NO_INTERLO       = @ID_INTERLOCUTEUR
  CREER_PAR        = @UTILISATEUR
  MODIF_PAR        = @UTILISATEUR
  CREER_LE         = GETDATE()
  MODIF_LE         = GETDATE()

Retourne :
  @DEMANDE_ID      = NO_INCIDENT genere
  @REFERENCE       = CHRONO_INCIDENT genere

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v002 - VABE - 06/03/2026 - Refactoring : insertion dans INCIDENT
                           Utilisation de sp_COMPTEUR pour NO_INCIDENT
                           Utilisation de sp_generation_chrono pour CHRONO_INCIDENT

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
    @COD_SITE           varchar(10)     = NULL,
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
        @COD_SITE,
        @DEMANDE_ID OUTPUT,
        @REFERENCE  OUTPUT
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_UTILISATEUR      char(10)
    DECLARE @V_NO_INCIDENT      int
    DECLARE @V_CHRONO           varchar(50)
    DECLARE @V_NO_SOCIETE       int

    -- ──────────────────────────────────────────────────────────
    -- Normalisation utilisateur
    -- ──────────────────────────────────────────────────────────
    SET @V_UTILISATEUR = LEFT(ISNULL(@UTILISATEUR, 'OUTLOOK'), 10)

    -- ──────────────────────────────────────────────────────────
    -- Recuperation du NO_SOCIETE depuis l'interlocuteur
    -- ──────────────────────────────────────────────────────────
    SELECT @V_NO_SOCIETE = NO_SOCIETE
    FROM INTERLOC
    WHERE NO_INTERLO = @ID_INTERLOCUTEUR

    -- ──────────────────────────────────────────────────────────
    -- Generation du NO_INCIDENT via sp_COMPTEUR
    -- (equivalent de AtheneoGenerator.generate avec compteur = 'NO_INCIDENT')
    -- ──────────────────────────────────────────────────────────
    EXEC sp_COMPTEUR
        @CHAMP    = 'NO_INCIDENT',
        @COMPTEUR = @V_NO_INCIDENT OUTPUT,
        @PAS      = 1

    -- ──────────────────────────────────────────────────────────
    -- Generation du CHRONO_INCIDENT via sp_generation_chrono
    -- (equivalent de AtheneoGenerator.generateChrono)
    -- ──────────────────────────────────────────────────────────
    --EXEC sp_generation_chrono
    --    @TYPE_PIECE = 'INCIDENT',
    --    @COD_SITE   = @COD_SITE,
    --    @NO_PIECE   = @V_NO_INCIDENT,
    --    @JOUR       = CAST(DAY(GETDATE())   AS varchar(2)),
    --    @MOIS       = CAST(MONTH(GETDATE()) AS varchar(2)),
    --    @ANNEE      = CAST(YEAR(GETDATE())  AS varchar(4)),
    --    @CHRONO     = @V_CHRONO OUTPUT

    -- ──────────────────────────────────────────────────────────
    -- Insertion dans INCIDENT
    -- ──────────────────────────────────────────────────────────
    INSERT INTO INCIDENT (
        NO_INCIDENT,
        OBJET,
        MEMO_P,
        COD_ETAT,
        COD_PRIORITE,
        -- CHRONO_INCIDENT,
        NO_SOCIETE,
        NO_INTERLO,
        CREER_LE,
        CREER_PAR,
        MODIF_LE,
        MODIF_PAR
    )
    VALUES (
        @V_NO_INCIDENT,
        LEFT(ISNULL(@TITRE, ''), 254),              -- OBJET
        @DESCRIPTION,                               -- MEMO_P
        'NEW',                                      -- COD_ETAT : statut initial
        LEFT(ISNULL(@PRIORITE, 'normale'), 20),     -- COD_PRIORITE
        -- @V_CHRONO,                                  -- CHRONO_INCIDENT
        @V_NO_SOCIETE,                              -- NO_SOCIETE
        @ID_INTERLOCUTEUR,                          -- NO_INTERLO
        GETDATE(),                                  -- CREER_LE
        @V_UTILISATEUR,                             -- CREER_PAR
        GETDATE(),                                  -- MODIF_LE
        @V_UTILISATEUR                              -- MODIF_PAR
    )

    -- ──────────────────────────────────────────────────────────
    -- Valeurs de retour
    -- ──────────────────────────────────────────────────────────
    SET @DEMANDE_ID = @V_NO_INCIDENT
    SET @REFERENCE  = @V_CHRONO

END
GO