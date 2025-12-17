/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_LISTER_DEMANDES
No Version : 001
Description : Liste toutes les demandes avec filtres optionnels
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_LISTER_DEMANDES]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_LISTER_DEMANDES]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_LISTER_DEMANDES
    @STATUT VARCHAR(20) = NULL,
    @PRIORITE VARCHAR(20) = NULL,
    @TYPE VARCHAR(50) = NULL,
    @ID_INTERLOCUTEUR BIGINT = NULL,
    @LIMITE INT = 100
AS
BEGIN
    SET NOCOUNT ON;

    -- TODO: Implémenter la récupération de la liste des demandes
    -- Avec filtres optionnels et pagination

END
GO