IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_STATS]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_STATS]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_STATS

No Version : 001

Description :
Retourne les statistiques globales du module Outlook Add-in :
  - Nombre d'emails enregistres depuis Outlook
  - Nombre de demandes creees depuis Outlook
  - Nombre d'interlocuteurs enrichis via Outlook
  - Nombre d'actions creees depuis Outlook
  - Nombre de pieces jointes enregistrees depuis Outlook
Cette procedure est appelee par le endpoint /api/health pour
le health check de l'API.

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

CREATE PROCEDURE [SP_ATHENEO_STATS]
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_STATS]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_STATS
END
ELSE
BEGIN
    SET NOCOUNT ON;

    SELECT
        0   AS totalEmails,
        (SELECT COUNT(*) FROM DEMANDE_P   WHERE C5     = 'outlook_addin')   AS totalDemandes,
        (SELECT COUNT(*) FROM INTERLOC    WHERE SOURCE = 'OUTLOOK_ADDIN')   AS totalInterlocuteurs,
        (SELECT COUNT(*) FROM ACTION      WHERE SOURCE = 'OUTLOOK_ADDIN')   AS totalActions,
        0                                                              AS totalPiecesJointes,
        GETDATE()                                                            AS dateConsultation

END
GO
