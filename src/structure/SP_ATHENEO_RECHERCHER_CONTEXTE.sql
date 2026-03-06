IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_RECHERCHER_CONTEXTE]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_RECHERCHER_CONTEXTE]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_RECHERCHER_CONTEXTE

No Version : 001

Description :
Recherche le contexte (module actif) d'un utilisateur dans la table T_SESSION
a partir de son adresse email. Retrouve d'abord le COD_USER correspondant
dans la table USERS, puis retourne le MODULE de sa session en cours.

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

CREATE PROCEDURE [SP_ATHENEO_RECHERCHER_CONTEXTE]
    @EMAIL varchar(100)
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_RECHERCHER_CONTEXTE]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_RECHERCHER_CONTEXTE @EMAIL
END
ELSE
BEGIN
    SET NOCOUNT ON;

    SELECT
        TS.MODULE               AS module
    FROM T_SESSION TS
    INNER JOIN USERS U ON U.COD_USER = TS.COD_USER
    WHERE UPPER(LTRIM(RTRIM(U.MAIL))) = UPPER(LTRIM(RTRIM(@EMAIL)))

END
GO