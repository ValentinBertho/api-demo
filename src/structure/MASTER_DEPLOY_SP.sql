/* ============================================================
   ATHENEO ERP - Script maitre de deploiement
   Procedures stockees Outlook Add-in

   Version     : 001
   Auteur      : VABE
   Date        : 26/02/2026

   Description :
     Ce script deploie en sequence toutes les procedures
     stockees SP_ATHENEO_* necessaires a l'interface
     Outlook Add-in ATHENEO.
     Il est idempotent : chaque procedure est droppee
     puis recree via la directive :r de SQLCMD.

   Execution depuis SQLCMD :
     sqlcmd -S NA-ATHERP\SQL2022 ^
            -U atheneo_sql -P M@ster2016 ^
            -d ATH_ERP_V17_1 ^
            -i MASTER_DEPLOY_SP.sql ^
            -b

   Ou via le script maitre Windows :
     DEPLOY_ALL_SP.cmd

   Historique des mises a jour :
     v001 - VABE - 26/02/2026 - Creation
============================================================ */

SET NOCOUNT ON;
GO

PRINT '============================================================';
PRINT ' ATHENEO - Deploiement Procedures Stockees Outlook Add-in';
PRINT ' Serveur  : ' + @@SERVERNAME;
PRINT ' Base     : ' + DB_NAME();
PRINT ' Debut    : ' + CONVERT(varchar(20), GETDATE(), 120);
PRINT '============================================================';
PRINT '';
GO

/* --------------------------------------------------------
   1. SP_ATHENEO_STATS
      Stats globales pour le endpoint /api/health
-------------------------------------------------------- */
PRINT '[1/10] SP_ATHENEO_STATS ...';
GO
:r SP_ATHENEO_STATS.sql
GO
PRINT '       -> OK';
PRINT '';
GO

/* --------------------------------------------------------
   2. SP_ATHENEO_RECHERCHER_INTERLOCUTEUR
      Recherche un interlocuteur par email (SELECT INTERLOC)
-------------------------------------------------------- */
PRINT '[2/10] SP_ATHENEO_RECHERCHER_INTERLOCUTEUR ...';
GO
:r SP_ATHENEO_RECHERCHER_INTERLOCUTEUR.sql
GO
PRINT '       -> OK';
PRINT '';
GO

/* --------------------------------------------------------
   3. SP_ATHENEO_CREER_INTERLOCUTEUR
      Insert dans INTERLOC, idempotent sur E_MAIL
      Depend de : INTERLOC, SOCIETE
-------------------------------------------------------- */
PRINT '[3/10] SP_ATHENEO_CREER_INTERLOCUTEUR ...';
GO
:r SP_ATHENEO_CREER_INTERLOCUTEUR.sql
GO
PRINT '       -> OK';
PRINT '';
GO

/* --------------------------------------------------------
   4. SP_ATHENEO_ENREGISTRER_MAIL
      Insert dans MAIL, lien automatique NO_INTERLO
      Depend de : MAIL, INTERLOC
-------------------------------------------------------- */
PRINT '[4/10] SP_ATHENEO_ENREGISTRER_MAIL ...';
GO
:r SP_ATHENEO_ENREGISTRER_MAIL.sql
GO
PRINT '       -> OK';
PRINT '';
GO

/* --------------------------------------------------------
   5. SP_ATHENEO_LISTER_EMAILS
      Select MAIL filtre SOURCE = OUTLOOK_ADDIN
      Depend de : MAIL, INTERLOC
-------------------------------------------------------- */
PRINT '[5/10] SP_ATHENEO_LISTER_EMAILS ...';
GO
:r SP_ATHENEO_LISTER_EMAILS.sql
GO
PRINT '       -> OK';
PRINT '';
GO

/* --------------------------------------------------------
   6. SP_ATHENEO_CREER_DEMANDE
      Insert dans DEMANDE_P, reference DEM-YYYY-XXXXX
      Depend de : DEMANDE_P, INTERLOC
-------------------------------------------------------- */
PRINT '[6/10] SP_ATHENEO_CREER_DEMANDE ...';
GO
:r SP_ATHENEO_CREER_DEMANDE.sql
GO
PRINT '       -> OK';
PRINT '';
GO

/* --------------------------------------------------------
   7. SP_ATHENEO_LISTER_DEMANDES
      Select DEMANDE_P filtre C5 = outlook_addin
      Depend de : DEMANDE_P, INTERLOC
-------------------------------------------------------- */
PRINT '[7/10] SP_ATHENEO_LISTER_DEMANDES ...';
GO
:r SP_ATHENEO_LISTER_DEMANDES.sql
GO
PRINT '       -> OK';
PRINT '';
GO

/* --------------------------------------------------------
   8. SP_ATHENEO_CREER_ACTION
      Insert dans ACTION, reference ACT-YYYY-XXXXX
      Mapping : COMPTE_ACT = titre, CO_T_CO_PA = statut
      Depend de : ACTION, INTERLOC
-------------------------------------------------------- */
PRINT '[8/10] SP_ATHENEO_CREER_ACTION ...';
GO
:r SP_ATHENEO_CREER_ACTION.sql
GO
PRINT '       -> OK';
PRINT '';
GO

/* --------------------------------------------------------
   9. SP_ATHENEO_LISTER_ACTIONS
      Select ACTION filtre SOURCE = OUTLOOK_ADDIN
      Depend de : ACTION, INTERLOC
-------------------------------------------------------- */
PRINT '[9/10] SP_ATHENEO_LISTER_ACTIONS ...';
GO
:r SP_ATHENEO_LISTER_ACTIONS.sql
GO
PRINT '       -> OK';
PRINT '';
GO

/* --------------------------------------------------------
   10. SP_ATHENEO_ENREGISTRER_PIECE_JOINTE
       Insert dans PIECE, lien interlocuteur via email
       Depend de : PIECE, INTERLOC
-------------------------------------------------------- */
PRINT '[10/10] SP_ATHENEO_ENREGISTRER_PIECE_JOINTE ...';
GO
:r SP_ATHENEO_ENREGISTRER_PIECE_JOINTE.sql
GO
PRINT '        -> OK';
PRINT '';
GO

/* --------------------------------------------------------
   Verification finale : liste les SPs deployees
-------------------------------------------------------- */
PRINT '============================================================';
PRINT ' Verification - Procedures deployees :';
PRINT '============================================================';
GO

SELECT
    name                                    AS procedure_name,
    CONVERT(varchar(20), create_date, 120)  AS creee_le,
    CONVERT(varchar(20), modify_date, 120)  AS modifiee_le
FROM sys.objects
WHERE name LIKE 'SP_ATHENEO_%'
AND [type] = 'P'
AND is_ms_shipped = 0
ORDER BY name;
GO

PRINT '';
PRINT 'Deploiement termine le : ' + CONVERT(varchar(20), GETDATE(), 120);
PRINT '============================================================';
GO
