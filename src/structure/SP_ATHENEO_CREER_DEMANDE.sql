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

CREATE PROCEDURE SP_ATHENEO_CREER_DEMANDE
    @ID_INTERLOCUTEUR BIGINT,
    @EMAIL_CONTACT VARCHAR(255),
    @CONTACT_NOM VARCHAR(255) = NULL,
    @TITRE NVARCHAR(500),
    @DESCRIPTION NVARCHAR(MAX) = NULL,
    @SOURCE VARCHAR(50) = 'outlook_addin',
    @PRIORITE VARCHAR(20) = 'normale',
    @TYPE VARCHAR(50) = 'email',
    @UTILISATEUR VARCHAR(100) = NULL,
    @DEMANDE_ID BIGINT OUTPUT,
    @REFERENCE VARCHAR(50) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    -- TODO: Implémenter la création d'une demande
    -- Générer une référence unique (DEM-YYYY-XXXXX)
    -- Retourner l'ID et la référence de la demande créée

END
GO