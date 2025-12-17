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

CREATE PROCEDURE SP_ATHENEO_CREER_ACTION
    @ID_INTERLOCUTEUR BIGINT,
    @EMAIL_CONTACT VARCHAR(255),
    @CONTACT_NOM VARCHAR(255) = NULL,
    @TITRE NVARCHAR(500),
    @DESCRIPTION NVARCHAR(MAX) = NULL,
    @TYPE VARCHAR(50) = 'email_follow_up',
    @PRIORITE VARCHAR(20) = 'normale',
    @STATUT VARCHAR(20) = 'a_faire',
    @SOURCE VARCHAR(50) = 'outlook_addin',
    @UTILISATEUR VARCHAR(100) = NULL,
    @ACTION_ID BIGINT OUTPUT,
    @REFERENCE VARCHAR(50) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    -- TODO: Implémenter la création d'une action
    -- Générer une référence unique (ACT-YYYY-XXXXX)
    -- Retourner l'ID et la référence de l'action créée

END
GO
