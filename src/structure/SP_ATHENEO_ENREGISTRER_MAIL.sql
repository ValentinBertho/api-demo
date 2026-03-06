IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_ENREGISTRER_MAIL]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_ENREGISTRER_MAIL]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_ENREGISTRER_MAIL

No Version : 001

Description :
Enregistre un email Outlook dans la table MAIL de la base ATHENEO.
Si un interlocuteur correspondant a l'expediteur existe, lie l'email
a cet interlocuteur via NO_INTERLO.
Retourne l'ID du mail cree dans le parametre de sortie EMAIL_ID.

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

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
        C1
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
        LEFT(ISNULL(@EXPEDITEUR_NOM, ''), 100)
    )

    SET @EMAIL_ID = SCOPE_IDENTITY()

END
GO
