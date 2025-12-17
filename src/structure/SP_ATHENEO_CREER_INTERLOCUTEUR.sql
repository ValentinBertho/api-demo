/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_CREER_INTERLOCUTEUR
No Version : 001
Description : Crée automatiquement un nouvel interlocuteur à partir d'un email
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_CREER_INTERLOCUTEUR]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_CREER_INTERLOCUTEUR]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_CREER_INTERLOCUTEUR(
    IN EMAIL VARCHAR(255),
    IN NOM_COMPLET VARCHAR(255),
    IN PRENOM VARCHAR(100),
    IN NOM VARCHAR(100),
    IN SOCIETE VARCHAR(255),
    IN UTILISATEUR VARCHAR(100),
    OUT INTERLOCUTEUR_ID BIGINT
)
LANGUAGE SQL
BEGIN ATOMIC
    -- Insertion de l'interlocuteur
    INSERT INTO INTERLOCUTEUR (EMAIL, NOM, PRENOM, SOCIETE, DATE_CREATION)
    VALUES (LOWER(EMAIL), NOM, PRENOM, SOCIETE, CURRENT_TIMESTAMP);

    -- Récupération de l'ID généré
    SET INTERLOCUTEUR_ID = IDENTITY();
END;