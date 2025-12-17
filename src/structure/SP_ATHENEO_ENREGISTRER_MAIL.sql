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

CREATE PROCEDURE SP_ATHENEO_ENREGISTRER_MAIL
    @EXPEDITEUR VARCHAR(255),
    @EXPEDITEUR_NOM VARCHAR(255) = NULL,
    @SUJET NVARCHAR(500) = NULL,
    @CONTENU NVARCHAR(MAX) = NULL,
    @DATE_RECEPTION DATETIME,
    @UTILISATEUR VARCHAR(100) = NULL,
    @EMAIL_ID BIGINT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    -- TODO: Implémenter la logique d'insertion dans la table EMAIL
    -- Retourner l'ID de l'email créé dans @EMAIL_ID

END
GO