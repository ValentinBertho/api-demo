/* ////////////////////////////////////////////////////////////////////////
Nom de la procedure stockee : SP_ATHENEO_ENREGISTRER_PIECE_JOINTE
No Version : 001
Description : Enregistre une pièce jointe associée à un email
//////////////////////////////////////////////////////////////////////// */

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SP_ATHENEO_ENREGISTRER_PIECE_JOINTE]') AND is_ms_shipped = 0 AND [type] IN ('P'))
DROP PROCEDURE [SP_ATHENEO_ENREGISTRER_PIECE_JOINTE]
GO

SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROCEDURE SP_ATHENEO_ENREGISTRER_PIECE_JOINTE
    @NOM_FICHIER VARCHAR(255),
    @TAILLE BIGINT,
    @TYPE_MIME VARCHAR(100),
    @ID_OUTLOOK VARCHAR(255) = NULL,
    @EMAIL_SOURCE VARCHAR(255) = NULL,
    @SUJET_MAIL NVARCHAR(500) = NULL,
    @UTILISATEUR VARCHAR(100) = NULL,
    @PIECE_JOINTE_ID BIGINT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    -- TODO: Implémenter l'enregistrement d'une pièce jointe
    -- Retourner l'ID de la pièce jointe créée dans @PIECE_JOINTE_ID

END
GO
