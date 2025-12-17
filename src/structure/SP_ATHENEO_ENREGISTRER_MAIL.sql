/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_ENREGISTRER_MAIL
No Version : 001
Description : Enregistre un nouvel email dans la base de données ATHENEO
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_ENREGISTRER_MAIL]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_ENREGISTRER_MAIL]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_ENREGISTRER_MAIL(
    IN EXPEDITEUR VARCHAR(255),
    IN EXPEDITEUR_NOM VARCHAR(255),
    IN SUJET VARCHAR(500),
    IN CONTENU VARCHAR,
    IN DATE_RECEPTION TIMESTAMP,
    IN UTILISATEUR VARCHAR(100),
    OUT EMAIL_ID BIGINT
)
LANGUAGE SQL
BEGIN ATOMIC
    -- Insertion de l'email dans la table
    INSERT INTO EMAIL (EXPEDITEUR, EXPEDITEUR_NOM, SUJET, CONTENU, DATE_RECEPTION, DATE_CREATION)
    VALUES (EXPEDITEUR, EXPEDITEUR_NOM, SUJET, CONTENU, DATE_RECEPTION, CURRENT_TIMESTAMP);

    -- Récupération de l'ID généré
    SET EMAIL_ID = IDENTITY();
END;