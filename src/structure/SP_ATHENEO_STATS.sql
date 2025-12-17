/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_STATS
No Version : 001
Description : Retourne les statistiques globales pour le health check
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_STATS]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_STATS]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_STATS
AS
BEGIN
    SET NOCOUNT ON;

    -- TODO: Implémenter le retour des statistiques
    -- Nombre d'emails, demandes, interlocuteurs, actions, pièces jointes

END
GO