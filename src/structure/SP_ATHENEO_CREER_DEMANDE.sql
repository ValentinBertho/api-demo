/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_CREER_DEMANDE
No Version : 001
Description : Crée une nouvelle demande liée à un interlocuteur
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_CREER_DEMANDE]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_CREER_DEMANDE]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_CREER_DEMANDE(
    IN ID_INTERLOCUTEUR BIGINT,
    IN EMAIL_CONTACT VARCHAR(255),
    IN CONTACT_NOM VARCHAR(255),
    IN TITRE VARCHAR(500),
    IN DESCRIPTION VARCHAR,
    IN SOURCE VARCHAR(50),
    IN PRIORITE VARCHAR(20),
    IN TYPE VARCHAR(50),
    IN UTILISATEUR VARCHAR(100),
    OUT DEMANDE_ID BIGINT,
    OUT REFERENCE VARCHAR(50)
)
LANGUAGE SQL
BEGIN ATOMIC
    DECLARE seq_num INT;

    -- Génération d'un numéro séquentiel basé sur le nombre de demandes
    SELECT COALESCE(MAX(ID), 0) + 1 INTO seq_num FROM DEMANDE;

    -- Génération de la référence unique (DEM-YYYY-XXXXX)
    SET REFERENCE = CONCAT('DEM-', YEAR(CURRENT_TIMESTAMP), '-', LPAD(seq_num, 5, '0'));

    -- Insertion de la demande
    INSERT INTO DEMANDE (
        REFERENCE, ID_INTERLOCUTEUR, EMAIL_CONTACT, CONTACT_NOM,
        TITRE, DESCRIPTION, STATUT, PRIORITE, TYPE, SOURCE, DATE_CREATION
    )
    VALUES (
        REFERENCE, ID_INTERLOCUTEUR, EMAIL_CONTACT, CONTACT_NOM,
        TITRE, DESCRIPTION, 'nouvelle', PRIORITE, TYPE, SOURCE, CURRENT_TIMESTAMP
    );

    -- Récupération de l'ID généré
    SET DEMANDE_ID = IDENTITY();
END;