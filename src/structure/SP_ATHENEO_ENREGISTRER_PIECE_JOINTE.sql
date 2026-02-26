IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_ENREGISTRER_PIECE_JOINTE]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_ENREGISTRER_PIECE_JOINTE]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_ENREGISTRER_PIECE_JOINTE

No Version : 001

Description :
Enregistre une piece jointe Outlook dans la table PIECE de la base ATHENEO.
Mapping des champs metier vers PIECE :
  REFERENCE   = Nom du fichier (50 car max)
  OBJET       = Nom complet du fichier (200 car max)
  C1          = Type MIME
  C2          = Taille en octets (char)
  C3          = ID Outlook (identifiant unique piece jointe)
  C4          = Email source (expediteur)
  C5          = Sujet du mail d'origine
Retourne l'ID de la piece jointe creee dans @PIECE_JOINTE_ID.

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

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
