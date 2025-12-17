package fr.mismo.demo_web_addin.service.impl;

import fr.mismo.demo_web_addin.service.DemandeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemandeServiceImpl implements DemandeService {

    private final DataSource dataSource;

    @Override
    public Map<String, Object> creerDemande(
            Long idInterlocuteur,
            String emailContact,
            String contactNom,
            String titre,
            String description,
            String source,
            String priorite,
            String type
    ) {
        log.info("📋 Création demande pour interlocuteur: {}", idInterlocuteur);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_ATHENEO_CREER_DEMANDE")
                .declareParameters(
                        new SqlParameter("ID_INTERLOCUTEUR", Types.BIGINT),
                        new SqlParameter("EMAIL_CONTACT", Types.VARCHAR),
                        new SqlParameter("CONTACT_NOM", Types.VARCHAR),
                        new SqlParameter("TITRE", Types.NVARCHAR),
                        new SqlParameter("DESCRIPTION", Types.NVARCHAR),
                        new SqlParameter("SOURCE", Types.VARCHAR),
                        new SqlParameter("PRIORITE", Types.VARCHAR),
                        new SqlParameter("TYPE", Types.VARCHAR),
                        new SqlParameter("UTILISATEUR", Types.VARCHAR),
                        new SqlOutParameter("DEMANDE_ID", Types.BIGINT),
                        new SqlOutParameter("REFERENCE", Types.VARCHAR)
                );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("ID_INTERLOCUTEUR", idInterlocuteur)
                .addValue("EMAIL_CONTACT", emailContact)
                .addValue("CONTACT_NOM", contactNom)
                .addValue("TITRE", titre)
                .addValue("DESCRIPTION", description)
                .addValue("SOURCE", source)
                .addValue("PRIORITE", priorite)
                .addValue("TYPE", type)
                .addValue("UTILISATEUR", "SYSTEM");

        Map<String, Object> result = jdbcCall.execute(params);

        Long demandeId = (Long) result.get("DEMANDE_ID");
        String reference = (String) result.get("REFERENCE");

        log.info("✅ Demande créée: {}", reference);

        return Map.of(
                "id", demandeId,
                "reference", reference,
                "idInterlocuteur", idInterlocuteur,
                "titre", titre
        );
    }

    @Override
    public List<Map<String, Object>> listerDemandes(
            String statut,
            String priorite,
            String type,
            Long idInterlocuteur,
            int limite
    ) {
        log.info("📋 Listage des demandes");

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_ATHENEO_LISTER_DEMANDES")
                .declareParameters(
                        new SqlParameter("STATUT", Types.VARCHAR),
                        new SqlParameter("PRIORITE", Types.VARCHAR),
                        new SqlParameter("TYPE", Types.VARCHAR),
                        new SqlParameter("ID_INTERLOCUTEUR", Types.BIGINT),
                        new SqlParameter("LIMITE", Types.INTEGER)
                )
                .returningResultSet("demandes", (rs, rowNum) -> Map.of(
                        "id", rs.getLong("DEMANDE_ID"),
                        "reference", rs.getString("REFERENCE"),
                        "idInterlocuteur", rs.getLong("ID_INTERLOCUTEUR"),
                        "titre", rs.getString("TITRE"),
                        "priorite", rs.getString("PRIORITE"),
                        "statut", rs.getString("STATUT")
                ));

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("STATUT", statut)
                .addValue("PRIORITE", priorite)
                .addValue("TYPE", type)
                .addValue("ID_INTERLOCUTEUR", idInterlocuteur)
                .addValue("LIMITE", limite);

        Map<String, Object> result = jdbcCall.execute(params);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> demandes = (List<Map<String, Object>>) result.get("demandes");

        return demandes;
    }
}
