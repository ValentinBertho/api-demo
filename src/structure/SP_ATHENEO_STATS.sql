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

CREATE PROCEDURE SP_ATHENEO_STATS()
LANGUAGE SQL
READS SQL DATA
AS $$
    SELECT
        (SELECT COUNT(*) FROM EMAIL) AS TOTAL_EMAILS,
        (SELECT COUNT(*) FROM INTERLOCUTEUR) AS TOTAL_INTERLOCUTEURS,
        (SELECT COUNT(*) FROM DEMANDE) AS TOTAL_DEMANDES,
        (SELECT COUNT(*) FROM ACTION) AS TOTAL_ACTIONS,
        (SELECT COUNT(*) FROM PIECE_JOINTE) AS TOTAL_PIECES_JOINTES,
        (SELECT COUNT(*) FROM DEMANDE WHERE STATUT = 'nouvelle') AS DEMANDES_NOUVELLES,
        (SELECT COUNT(*) FROM ACTION WHERE STATUT = 'a_faire') AS ACTIONS_A_FAIRE
    FROM DUAL;
$$;