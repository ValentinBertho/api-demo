IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_LISTER_ACTIONS]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_LISTER_ACTIONS]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_LISTER_ACTIONS

No Version : 001

Description :
Liste les actions de suivi creees depuis Outlook Add-in avec filtres optionnels.
Les actions sont identifiees par SOURCE = 'OUTLOOK_ADDIN' dans la table ACTION.
Mapping inverse :
  COMPTE_ACT   = titre      CO_T_CO_PA = statut
  CD_T_AC_PA   = type       COD_PRIORITE = priorite
  C1 = emailContact  C2 = contactNom  C4 = reference

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

CREATE PROCEDURE [SP_ATHENEO_LISTER_ACTIONS]
    @STATUT             varchar(10)     = NULL,
    @PRIORITE           varchar(10)     = NULL,
    @TYPE               varchar(10)     = NULL,
    @ID_INTERLOCUTEUR   int             = NULL,
    @LIMITE             int             = 100
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_LISTER_ACTIONS]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_LISTER_ACTIONS
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
        A.NO_ACTION         AS id,
        A.C4                AS reference,
        A.NO_INTERLO        AS idInterlocuteur,
        A.C1                AS emailContact,
        A.C2                AS contactNom,
        A.COMPTE_ACT        AS titre,
        A.MEMO              AS description,
        A.CD_T_AC_PA        AS type,
        A.COD_PRIORITE      AS priorite,
        A.CO_T_CO_PA        AS statut,
        A.C3                AS source,
        A.DAT_ACT_PR        AS datePrevue,
        A.CREER_LE          AS dateCreation,
        I.NOM               AS interlocuteurNom,
        I.PRENOM            AS interlocuteurPrenom,
        I.E_MAIL            AS interlocuteurEmail
    FROM ACTION A
    LEFT JOIN INTERLOC I ON I.NO_INTERLO = A.NO_INTERLO
    WHERE A.SOURCE = 'OUTLOOK_ADDIN'
    AND (@STATUT           IS NULL OR UPPER(A.CO_T_CO_PA) = UPPER(@STATUT))
    AND (@PRIORITE         IS NULL OR UPPER(A.COD_PRIORITE) = UPPER(@PRIORITE))
    AND (@TYPE             IS NULL OR UPPER(A.CD_T_AC_PA) = UPPER(@TYPE))
    AND (@ID_INTERLOCUTEUR IS NULL OR A.NO_INTERLO = @ID_INTERLOCUTEUR)
    ORDER BY A.CREER_LE DESC

END
GO
