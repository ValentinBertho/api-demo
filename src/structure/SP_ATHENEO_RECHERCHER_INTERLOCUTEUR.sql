/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_RECHERCHER_INTERLOCUTEUR
No Version : 001
Description : Recherche un interlocuteur par son adresse email
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_RECHERCHER_INTERLOCUTEUR]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_RECHERCHER_INTERLOCUTEUR]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_RECHERCHER_INTERLOCUTEUR
    @EMAIL VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

    -- TODO: Implémenter la recherche d'interlocuteur par email
    -- Retourner les informations de l'interlocuteur si trouvé

END
GO