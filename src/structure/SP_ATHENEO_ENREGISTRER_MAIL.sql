IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_ENREGISTRER_MAIL]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_ENREGISTRER_MAIL]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_ENREGISTRER_MAIL

No Version : 002

Description :
Enregistre un email Outlook dans la table MAIL de la base ATHENEO.

Generation des identifiants :
  - NO_MAIL : via sp_COMPTEUR (compteur = 'NO_MAIL', pas = 1)

Liaison automatique au contexte ATHENEO :
  Le parametre @MODULE au format "Fiche Devis - N°69392" permet de renseigner
  la colonne de liaison correspondante (NO_INCIDENT, NO_DEVIS, NO_CONTRAT...).
  La resolution du NO_SOCIETE est faite depuis l'interlocuteur si trouve.

Mapping des champs metier :
  OBJET            = @SUJET
  CORPS            = @CONTENU
  EXPEDITEUR       = @EXPEDITEUR
  DESTINATAIRE     = @DESTINATAIRE
  ENVOYER_LE       = @DATE_RECEPTION
  SORTANT          = 0 (mail entrant)
  COD_ETAT         = 'NOUVEAU'
  COD_TYPE         = 'EMAIL'
  C1               = @EXPEDITEUR_NOM (nom affiché expéditeur)

Retourne :
  @EMAIL_ID        = NO_MAIL genere

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation
v002 - VABE - 06/03/2026 - Utilisation de sp_COMPTEUR pour NO_MAIL
                           Ajout liaison contexte ATHENEO (NO_INCIDENT, NO_DEVIS, etc.)
                           Ajout NO_SOCIETE, COD_ETAT, COD_TYPE, DESTINATAIRE
                           Ajout parametre @MODULE et @DESTINATAIRE

//////////////////////////////////////////////////////////////////////// */

CREATE PROCEDURE [SP_ATHENEO_ENREGISTRER_MAIL]
    @EXPEDITEUR         varchar(200),
    @EXPEDITEUR_NOM     varchar(200)    = NULL,
    @DESTINATAIRE       varchar(200)    = NULL,
    @SUJET              varchar(200)    = NULL,
    @CONTENU            text            = NULL,
    @DATE_RECEPTION     datetime        = NULL,
    @UTILISATEUR        varchar(10)     = NULL,
    -- Contexte ATHENEO au format "Fiche Devis - N°69392"
    -- Permet la liaison automatique a NO_INCIDENT, NO_DEVIS, NO_CONTRAT, etc.
    @MODULE             varchar(200)    = NULL,
    @EMAIL_ID           int             OUTPUT
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_ENREGISTRER_MAIL]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_ENREGISTRER_MAIL
        @EXPEDITEUR,
        @EXPEDITEUR_NOM,
        @DESTINATAIRE,
        @SUJET,
        @CONTENU,
        @DATE_RECEPTION,
        @UTILISATEUR,
        @MODULE,
        @EMAIL_ID OUTPUT
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_NO_MAIL          int
    DECLARE @V_NO_INTERLO       int
    DECLARE @V_NO_SOCIETE       int
    DECLARE @V_UTILISATEUR      char(10)
    DECLARE @V_DATE_ENV         datetime

    -- Colonnes de liaison contexte (une seule sera renseignee selon @MODULE)
    DECLARE @V_NO_INCIDENT      int         = NULL
    DECLARE @V_NO_DEVIS         int         = NULL
    DECLARE @V_NO_CONTRAT       int         = NULL
    DECLARE @V_NO_AFFAIRE       int         = NULL
    DECLARE @V_NO_PARC          int         = NULL

    -- Variables de parsing du module
    DECLARE @V_TYPE_MODULE      varchar(50)
    DECLARE @V_NO_MODULE        int
    DECLARE @V_DASH_POS         int
    DECLARE @V_NB_POS           int

    SET @V_UTILISATEUR  = LEFT(ISNULL(@UTILISATEUR, 'OUTLOOK'), 10)
    SET @V_DATE_ENV     = ISNULL(@DATE_RECEPTION, GETDATE())

    -- ──────────────────────────────────────────────────────────
    -- Generation du NO_MAIL via sp_COMPTEUR
    -- ──────────────────────────────────────────────────────────
    EXEC sp_COMPTEUR
        @CHAMP    = 'NO_MAIL',
        @COMPTEUR = @V_NO_MAIL OUTPUT,
        @PAS      = 1

    -- ──────────────────────────────────────────────────────────
    -- Recherche de l'interlocuteur depuis l'expediteur
    -- ──────────────────────────────────────────────────────────
    SELECT TOP 1
        @V_NO_INTERLO = NO_INTERLO,
        @V_NO_SOCIETE = NO_SOCIETE
    FROM INTERLOC
    WHERE UPPER(LTRIM(RTRIM(E_MAIL))) = UPPER(LTRIM(RTRIM(@EXPEDITEUR)))
    AND ISNULL(ACTIF, 1) = 1

    -- ──────────────────────────────────────────────────────────
    -- Parsing du contexte ATHENEO
    -- Format attendu : "Fiche <TYPE> - N°<NUMERO>"
    -- Exemples       : "Fiche Devis - N°69392"
    --                  "Fiche Incident - N°12345"
    --                  "Fiche Contrat - N°9876"
    -- ──────────────────────────────────────────────────────────
    IF @MODULE IS NOT NULL
    BEGIN
        -- Extraction du type : entre "Fiche " et " - N°"
        SET @V_DASH_POS = CHARINDEX(' - N°', @MODULE)
        IF @V_DASH_POS > 7  -- "Fiche " = 6 chars minimum
        BEGIN
            SET @V_TYPE_MODULE = LTRIM(RTRIM(SUBSTRING(@MODULE, 7, @V_DASH_POS - 7)))
            SET @V_NO_MODULE   = TRY_CAST(SUBSTRING(@MODULE, @V_DASH_POS + 5, LEN(@MODULE)) AS int)

            IF @V_NO_MODULE IS NOT NULL
            BEGIN
                IF @V_TYPE_MODULE = 'Incident'   SET @V_NO_INCIDENT = @V_NO_MODULE
                IF @V_TYPE_MODULE = 'Devis'       SET @V_NO_DEVIS    = @V_NO_MODULE
                IF @V_TYPE_MODULE = 'Contrat'     SET @V_NO_CONTRAT  = @V_NO_MODULE
                IF @V_TYPE_MODULE = 'Affaire'     SET @V_NO_AFFAIRE  = @V_NO_MODULE
                IF @V_TYPE_MODULE = 'Parc'        SET @V_NO_PARC     = @V_NO_MODULE
            END
        END
    END

    -- ──────────────────────────────────────────────────────────
    -- Insertion dans MAIL
    -- ──────────────────────────────────────────────────────────
    INSERT INTO MAIL (
        NO_MAIL,
        NO_INTERLO,
        NO_SOCIETE,
        NO_INCIDENT,
        NO_DEVIS,
        NO_CONTRAT,
        NO_AFFAIRE,
        NO_PARC,
        OBJET,
        CORPS,
        EXPEDITEUR,
        DESTINATAIRE,
        ENVOYER_LE,
        SORTANT,
        CREER_LE,
        CREER_PAR,
        MODIF_LE,
        MODIF_PAR,
        MODIFIABLE,
        COD_ETAT,
        COD_TYPE,
        C1
    )
    VALUES (
        @V_NO_MAIL,
        @V_NO_INTERLO,                                      -- NO_INTERLO  (NULL si non trouve)
        @V_NO_SOCIETE,                                      -- NO_SOCIETE  (depuis INTERLOC)
        @V_NO_INCIDENT,                                     -- NO_INCIDENT (depuis @MODULE)
        @V_NO_DEVIS,                                        -- NO_DEVIS    (depuis @MODULE)
        @V_NO_CONTRAT,                                      -- NO_CONTRAT  (depuis @MODULE)
        @V_NO_AFFAIRE,                                      -- NO_AFFAIRE  (depuis @MODULE)
        @V_NO_PARC,                                         -- NO_PARC     (depuis @MODULE)
        LEFT(ISNULL(@SUJET, ''), 200),                      -- OBJET
        @CONTENU,                                           -- CORPS
        LEFT(ISNULL(@EXPEDITEUR, ''), 200),                 -- EXPEDITEUR
        LEFT(ISNULL(@DESTINATAIRE, ''), 200),               -- DESTINATAIRE
        @V_DATE_ENV,                                        -- ENVOYER_LE
        0,                                                  -- SORTANT = 0 (mail entrant)
        GETDATE(),                                          -- CREER_LE
        @V_UTILISATEUR,                                     -- CREER_PAR
        GETDATE(),                                          -- MODIF_LE
        @V_UTILISATEUR,                                     -- MODIF_PAR
        1,                                                  -- MODIFIABLE
        'NOUVEAU',                                          -- COD_ETAT
        'EMAIL',                                            -- COD_TYPE
        LEFT(ISNULL(@EXPEDITEUR_NOM, ''), 100)              -- C1 = Nom affiche expediteur
    )

    SET @EMAIL_ID = @V_NO_MAIL

END
GO