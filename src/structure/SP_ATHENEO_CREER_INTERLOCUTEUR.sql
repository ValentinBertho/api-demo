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

CREATE PROCEDURE SP_ATHENEO_CREER_INTERLOCUTEUR
    @EMAIL VARCHAR(255),
    @NOM_COMPLET VARCHAR(255) = NULL,
    @PRENOM VARCHAR(100) = NULL,
    @NOM VARCHAR(100) = NULL,
    @SOCIETE VARCHAR(255) = NULL,
    @UTILISATEUR VARCHAR(100) = NULL,
    @INTERLOCUTEUR_ID BIGINT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    -- TODO: Implémenter la création automatique d'un interlocuteur
    -- Extraction du nom/prénom si NOM_COMPLET fourni
    -- Extraction de la société à partir du domaine email
    -- Retourner l'ID de l'interlocuteur créé dans @INTERLOCUTEUR_ID

END
GO