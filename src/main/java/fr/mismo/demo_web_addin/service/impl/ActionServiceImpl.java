package fr.mismo.demo_web_addin.service.impl;

import fr.mismo.demo_web_addin.service.ActionService;
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
public class ActionServiceImpl implements ActionService {

    private final DataSource dataSource;

    @Override
    public Map<String, Object> creerAction(
            Long idInterlocuteur,
            String emailContact,
            String contactNom,
            String titre,
            String description,
            String type,
            String priorite,
            String statut,
            String source
    ) {
        log.info("✅ Création action pour interlocuteur: {}", idInterlocuteur);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_ATHENEO_CREER_ACTION")
                .declareParameters(
                        new SqlParameter("ID_INTERLOCUTEUR", Types.BIGINT),
                        new SqlParameter("EMAIL_CONTACT", Types.VARCHAR),
                        new SqlParameter("CONTACT_NOM", Types.VARCHAR),
                        new SqlParameter("TITRE", Types.NVARCHAR),
                        new SqlParameter("DESCRIPTION", Types.NVARCHAR),
                        new SqlParameter("TYPE", Types.VARCHAR),
                        new SqlParameter("PRIORITE", Types.VARCHAR),
                        new SqlParameter("STATUT", Types.VARCHAR),
                        new SqlParameter("SOURCE", Types.VARCHAR),
                        new SqlParameter("UTILISATEUR", Types.VARCHAR),
                        new SqlOutParameter("ACTION_ID", Types.BIGINT),
                        new SqlOutParameter("REFERENCE", Types.VARCHAR)
                );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("ID_INTERLOCUTEUR", idInterlocuteur)
                .addValue("EMAIL_CONTACT", emailContact)
                .addValue("CONTACT_NOM", contactNom)
                .addValue("TITRE", titre)
                .addValue("DESCRIPTION", description)
                .addValue("TYPE", type)
                .addValue("PRIORITE", priorite)
                .addValue("STATUT", statut)
                .addValue("SOURCE", source)
                .addValue("UTILISATEUR", "SYSTEM");

        Map<String, Object> result = jdbcCall.execute(params);

        Long actionId = (Long) result.get("ACTION_ID");
        String reference = (String) result.get("REFERENCE");

        log.info("✅ Action créée: {}", reference);

        return Map.of(
                "id", actionId,
                "reference", reference,
                "idInterlocuteur", idInterlocuteur,
                "titre", titre
        );
    }

    @Override
    public List<Map<String, Object>> listerActions(
            String statut,
            String priorite,
            String type,
            Long idInterlocuteur,
            int limite
    ) {
        log.info("✅ Listage des actions");

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_ATHENEO_LISTER_ACTIONS")
                .declareParameters(
                        new SqlParameter("STATUT", Types.VARCHAR),
                        new SqlParameter("PRIORITE", Types.VARCHAR),
                        new SqlParameter("TYPE", Types.VARCHAR),
                        new SqlParameter("ID_INTERLOCUTEUR", Types.BIGINT),
                        new SqlParameter("LIMITE", Types.INTEGER)
                )
                .returningResultSet("actions", (rs, rowNum) -> Map.of(
                        "id", rs.getLong("ACTION_ID"),
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
        List<Map<String, Object>> actions = (List<Map<String, Object>>) result.get("actions");

        return actions;
    }
}
