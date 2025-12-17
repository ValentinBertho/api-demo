/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_CREER_ACTION
No Version : 001
Description : Crée une nouvelle action de suivi liée à un interlocuteur
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_CREER_ACTION]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_CREER_ACTION]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_CREER_ACTION(
    IN ID_INTERLOCUTEUR BIGINT,
    IN EMAIL_CONTACT VARCHAR(255),
    IN CONTACT_NOM VARCHAR(255),
    IN TITRE VARCHAR(500),
    IN DESCRIPTION VARCHAR,
    IN TYPE VARCHAR(50),
    IN PRIORITE VARCHAR(20),
    IN STATUT VARCHAR(20),
    IN SOURCE VARCHAR(50),
    IN UTILISATEUR VARCHAR(100),
    OUT ACTION_ID BIGINT,
    OUT REFERENCE VARCHAR(50)
)
LANGUAGE SQL
BEGIN ATOMIC
    DECLARE seq_num INT;

    -- Génération d'un numéro séquentiel basé sur le nombre d'actions
    SELECT COALESCE(MAX(ID), 0) + 1 INTO seq_num FROM ACTION;

    -- Génération de la référence unique (ACT-YYYY-XXXXX)
    SET REFERENCE = CONCAT('ACT-', YEAR(CURRENT_TIMESTAMP), '-', LPAD(seq_num, 5, '0'));

    -- Insertion de l'action
    INSERT INTO ACTION (
        REFERENCE, ID_INTERLOCUTEUR, EMAIL_CONTACT, CONTACT_NOM,
        TITRE, DESCRIPTION, TYPE, PRIORITE, STATUT, SOURCE, DATE_CREATION
    )
    VALUES (
        REFERENCE, ID_INTERLOCUTEUR, EMAIL_CONTACT, CONTACT_NOM,
        TITRE, DESCRIPTION, TYPE, PRIORITE, STATUT, SOURCE, CURRENT_TIMESTAMP
    );

    -- Récupération de l'ID généré
    SET ACTION_ID = IDENTITY();
END;
