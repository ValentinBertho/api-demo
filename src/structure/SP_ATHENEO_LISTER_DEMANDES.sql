IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_LISTER_DEMANDES]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_LISTER_DEMANDES]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_LISTER_DEMANDES

No Version : 001

Description :
Liste les demandes creees depuis Outlook Add-in avec filtres optionnels.
Les demandes sont stockees dans DEMANDE_P avec les champs libres :
  C1 = Titre   C2 = Statut   C3 = Priorite
  C4 = Type    C5 = Source   C8 = Reference

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

CREATE PROCEDURE [SP_ATHENEO_LISTER_DEMANDES]
    @STATUT             varchar(20)     = NULL,
    @PRIORITE           varchar(20)     = NULL,
    @TYPE               varchar(50)     = NULL,
    @ID_INTERLOCUTEUR   int             = NULL,
    @LIMITE             int             = 100
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_LISTER_DEMANDES]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_LISTER_DEMANDES
        @STATUT,
        @PRIORITE,
        @TYPE,
        @ID_INTERLOCUTEUR,
        @LIMITE
END
ELSE
BEGIN
    SET NOCOUNT ON;

    DECLARE @V_LIMITE int
    SET @V_LIMITE = ISNULL(@LIMITE, 100)
    IF @V_LIMITE > 1000 SET @V_LIMITE = 1000

    SELECT TOP (@V_LIMITE)
        D.NO_DEMANDE_P      AS id,
        D.C8                AS reference,
        D.NO_INTERLO        AS idInterlocuteur,
        D.C6                AS emailContact,
        D.C7                AS contactNom,
        D.C1                AS titre,
        D.MEMO              AS description,
        D.C2                AS statut,
        D.C3                AS priorite,
        D.C4                AS type,
        D.C5                AS source,
        D.DATE_DEMANDE_P    AS dateCreation,
        I.NOM               AS interlocuteurNom,
        I.PRENOM            AS interlocuteurPrenom,
        I.E_MAIL            AS interlocuteurEmail
    FROM DEMANDE_P D
    LEFT JOIN INTERLOC I ON I.NO_INTERLO = D.NO_INTERLO
    WHERE D.C5 = 'outlook_addin'
    AND (@STATUT           IS NULL OR UPPER(D.C2) = UPPER(@STATUT))
    AND (@PRIORITE         IS NULL OR UPPER(D.C3) = UPPER(@PRIORITE))
    AND (@TYPE             IS NULL OR UPPER(D.C4) = UPPER(@TYPE))
    AND (@ID_INTERLOCUTEUR IS NULL OR D.NO_INTERLO = @ID_INTERLOCUTEUR)
    ORDER BY D.DATE_DEMANDE_P DESC

END
GO
