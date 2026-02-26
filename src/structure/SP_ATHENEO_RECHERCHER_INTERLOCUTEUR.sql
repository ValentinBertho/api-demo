IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_RECHERCHER_INTERLOCUTEUR]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_RECHERCHER_INTERLOCUTEUR]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/* ////////////////////////////////////////////////////////////////////////

Nom de la procedure stockee : SP_ATHENEO_RECHERCHER_INTERLOCUTEUR

No Version : 001

Description :
Recherche un interlocuteur dans la table INTERLOC par son adresse email.
Retourne les informations de l'interlocuteur et sa societe de rattachement
si une correspondance exacte (insensible a la casse) est trouvee.

Procedure appelee par :
Interface ATHENEO Outlook Add-in.

Historique des mises a jour :

v001 - VABE - 26/02/2026 - Creation

//////////////////////////////////////////////////////////////////////// */

CREATE PROCEDURE [SP_ATHENEO_RECHERCHER_INTERLOCUTEUR]
    @EMAIL varchar(100)
AS
IF EXISTS (SELECT * FROM sysobjects
           WHERE id = OBJECT_ID(N'[spe_SP_ATHENEO_RECHERCHER_INTERLOCUTEUR]')
           AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
    EXEC spe_SP_ATHENEO_RECHERCHER_INTERLOCUTEUR @EMAIL
END
ELSE
BEGIN
    SET NOCOUNT ON;

    SELECT
        I.NO_INTERLO            AS id,
        I.NOM                   AS nom,
        I.PRENOM                AS prenom,
        I.E_MAIL                AS email,
        I.TELEPHONE             AS telephone,
        I.TELPORTAB             AS telephonePortable,
        S.NOM                   AS societe,
        I.NO_SOCIETE            AS noSociete,
        I.CREER_LE              AS dateCreation,
        I.MODIF_LE              AS dateModification,
        I.SOURCE                AS source,
        I.NOM_SOCIETE_OUTLOOK   AS nomSocieteOutlook,
        I.ACTIF                 AS actif
    FROM INTERLOC I
    LEFT JOIN SOCIETE S ON S.NO_SOCIETE = I.NO_SOCIETE
    WHERE UPPER(LTRIM(RTRIM(I.E_MAIL))) = UPPER(LTRIM(RTRIM(@EMAIL)))
    AND ISNULL(I.ACTIF, 1) = 1

END
GO
