IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_LISTER_EMAILS]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_LISTER_EMAILS]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_LISTER_EMAILS

No Version : 001

Description :
Liste les emails enregistres depuis Outlook Add-in avec filtres optionnels.
Retourne les N derniers emails (parametre LIMITE) enregistres via l'add-in,
avec possibilite de filtrer par date et expediteur.

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

CREATE PROCEDURE [SP_ATHENEO_LISTER_EMAILS]
    @DATE_DEBUT     datetime    = NULL,
    @DATE_FIN       datetime    = NULL,
    @EXPEDITEUR     varchar(200) = NULL,
    @LIMITE         int         = 100
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_LISTER_EMAILS]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_LISTER_EMAILS
        @DATE_DEBUT,
        @DATE_FIN,
        @EXPEDITEUR,
        @LIMITE
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_LIMITE int
    SET @V_LIMITE = ISNULL(@LIMITE, 100)
    IF @V_LIMITE > 1000 SET @V_LIMITE = 1000

    SELECT TOP (@V_LIMITE)
        M.NO_MAIL           AS id,
        M.EXPEDITEUR        AS expediteur,
        M.C1                AS expediteurNom,
        M.OBJET             AS sujet,
        M.CORPS             AS contenu,
        M.ENVOYER_LE        AS dateReception,
        M.CREER_LE          AS dateCreation,
        M.NO_INTERLO        AS idInterlocuteur,
        I.NOM               AS interlocuteurNom,
        I.PRENOM            AS interlocuteurPrenom
    FROM MAIL M
    LEFT JOIN INTERLOC I ON I.NO_INTERLO = M.NO_INTERLO
    WHERE M.SOURCE = 'OUTLOOK_ADDIN'
    AND (@DATE_DEBUT IS NULL OR M.CREER_LE >= @DATE_DEBUT)
    AND (@DATE_FIN   IS NULL OR M.CREER_LE <= @DATE_FIN)
    AND (@EXPEDITEUR IS NULL OR UPPER(M.EXPEDITEUR) LIKE '%' + UPPER(@EXPEDITEUR) + '%')
    ORDER BY M.CREER_LE DESC

END
GO
