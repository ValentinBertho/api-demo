/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_ENREGISTRER_PIECE_JOINTE
No Version : 001
Description : Enregistre une pièce jointe associée à un email
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_ENREGISTRER_PIECE_JOINTE]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_ENREGISTRER_PIECE_JOINTE]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_ENREGISTRER_PIECE_JOINTE(
    IN NOM_FICHIER VARCHAR(255),
    IN TAILLE BIGINT,
    IN TYPE_MIME VARCHAR(100),
    IN ID_OUTLOOK VARCHAR(255),
    IN EMAIL_SOURCE VARCHAR(255),
    IN SUJET_MAIL VARCHAR(500),
    IN UTILISATEUR VARCHAR(100),
    OUT PIECE_JOINTE_ID BIGINT
)
LANGUAGE SQL
BEGIN ATOMIC
    -- Insertion de la pièce jointe
    INSERT INTO PIECE_JOINTE (
        NOM_FICHIER, TAILLE, TYPE_MIME, ID_OUTLOOK,
        EMAIL_SOURCE, SUJET_MAIL, DATE_AJOUT
    )
    VALUES (
        NOM_FICHIER, TAILLE, TYPE_MIME, ID_OUTLOOK,
        EMAIL_SOURCE, SUJET_MAIL, CURRENT_TIMESTAMP
    );

    -- Récupération de l'ID généré
    SET PIECE_JOINTE_ID = IDENTITY();
END;
