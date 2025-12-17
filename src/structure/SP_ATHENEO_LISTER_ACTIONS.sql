/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_LISTER_ACTIONS
No Version : 001
Description : Liste toutes les actions avec filtres optionnels
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_LISTER_ACTIONS]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_LISTER_ACTIONS]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_LISTER_ACTIONS(
    IN STATUT VARCHAR(20),
    IN PRIORITE VARCHAR(20),
    IN TYPE VARCHAR(50),
    IN ID_INTERLOCUTEUR BIGINT,
    IN LIMITE INT
)
LANGUAGE SQL
READS SQL DATA
AS $$
    SELECT
        ID AS ACTION_ID,
        REFERENCE,
        ID_INTERLOCUTEUR,
        EMAIL_CONTACT,
        CONTACT_NOM,
        TITRE,
        DESCRIPTION,
        TYPE,
        PRIORITE,
        STATUT,
        SOURCE,
        DATE_CREATION
    FROM ACTION
    WHERE (STATUT IS NULL OR ACTION.STATUT = STATUT)
      AND (PRIORITE IS NULL OR ACTION.PRIORITE = PRIORITE)
      AND (TYPE IS NULL OR ACTION.TYPE = TYPE)
      AND (ID_INTERLOCUTEUR IS NULL OR ACTION.ID_INTERLOCUTEUR = ID_INTERLOCUTEUR)
    ORDER BY DATE_CREATION DESC
    LIMIT LIMITE;
$$;