/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_LISTER_EMAILS
No Version : 001
Description : Liste tous les emails enregistrés avec filtres optionnels
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_LISTER_EMAILS]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_LISTER_EMAILS]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_LISTER_EMAILS
    @DATE_DEBUT DATETIME = NULL,
    @DATE_FIN DATETIME = NULL,
    @EXPEDITEUR VARCHAR(255) = NULL,
    @LIMITE INT = 100
AS
BEGIN
    SET NOCOUNT ON;

    -- TODO: Implémenter la récupération de la liste des emails
    -- Avec filtres optionnels et pagination

END
GO