/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_LISTER_EMAILS
No Version : 001
Description : Liste tous les emails enregistrés avec filtres optionnels
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_LISTER_EMAILS]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_LISTER_EMAILS]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_LISTER_EMAILS(
    IN DATE_DEBUT TIMESTAMP,
    IN DATE_FIN TIMESTAMP,
    IN EXPEDITEUR VARCHAR(255),
    IN LIMITE INT
)
LANGUAGE SQL
READS SQL DATA
AS $$
    SELECT
        ID AS EMAIL_ID,
        EXPEDITEUR,
        EXPEDITEUR_NOM,
        SUJET,
        DATE_RECEPTION,
        DATE_CREATION
    FROM EMAIL
    WHERE (DATE_DEBUT IS NULL OR DATE_RECEPTION >= DATE_DEBUT)
      AND (DATE_FIN IS NULL OR DATE_RECEPTION <= DATE_FIN)
      AND (EXPEDITEUR IS NULL OR LOWER(EMAIL.EXPEDITEUR) LIKE LOWER('%' || EXPEDITEUR || '%'))
    ORDER BY DATE_RECEPTION DESC
    LIMIT LIMITE;
$$;