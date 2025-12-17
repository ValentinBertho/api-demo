package fr.mismo.demo_web_addin.service.impl;

import fr.mismo.demo_web_addin.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final DataSource dataSource;

    @Override
    public Map<String, Object> enregistrerEmail(
            String expediteur,
            String expediteurNom,
            String sujet,
            String contenu,
            LocalDateTime dateReception
    ) {
        log.info("📧 Enregistrement email de: {}", expediteur);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_ATHENEO_ENREGISTRER_MAIL")
                .declareParameters(
                        new SqlParameter("EXPEDITEUR", Types.VARCHAR),
                        new SqlParameter("EXPEDITEUR_NOM", Types.VARCHAR),
                        new SqlParameter("SUJET", Types.NVARCHAR),
                        new SqlParameter("CONTENU", Types.NVARCHAR),
                        new SqlParameter("DATE_RECEPTION", Types.TIMESTAMP),
                        new SqlParameter("UTILISATEUR", Types.VARCHAR),
                        new SqlOutParameter("EMAIL_ID", Types.BIGINT)
                );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("EXPEDITEUR", expediteur)
                .addValue("EXPEDITEUR_NOM", expediteurNom)
                .addValue("SUJET", sujet)
                .addValue("CONTENU", contenu)
                .addValue("DATE_RECEPTION", dateReception)
                .addValue("UTILISATEUR", "SYSTEM");

        Map<String, Object> result = jdbcCall.execute(params);
        Long emailId = (Long) result.get("EMAIL_ID");

        log.info("✅ Email enregistré avec ID: {}", emailId);

        return Map.of(
                "id", emailId,
                "expediteur", expediteur,
                "sujet", sujet,
                "dateReception", dateReception
        );
    }

    @Override
    public List<Map<String, Object>> listerEmails(
            LocalDateTime dateDebut,
            LocalDateTime dateFin,
            String expediteur,
            int limite
    ) {
        log.info("📋 Listage des emails");

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_ATHENEO_LISTER_EMAILS")
                .declareParameters(
                        new SqlParameter("DATE_DEBUT", Types.TIMESTAMP),
                        new SqlParameter("DATE_FIN", Types.TIMESTAMP),
                        new SqlParameter("EXPEDITEUR", Types.VARCHAR),
                        new SqlParameter("LIMITE", Types.INTEGER)
                )
                .returningResultSet("emails", (rs, rowNum) -> Map.of(
                        "id", rs.getLong("EMAIL_ID"),
                        "expediteur", rs.getString("EXPEDITEUR"),
                        "expediteurNom", rs.getString("EXPEDITEUR_NOM"),
                        "sujet", rs.getString("SUJET"),
                        "dateReception", rs.getTimestamp("DATE_RECEPTION").toLocalDateTime()
                ));

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("DATE_DEBUT", dateDebut)
                .addValue("DATE_FIN", dateFin)
                .addValue("EXPEDITEUR", expediteur)
                .addValue("LIMITE", limite);

        Map<String, Object> result = jdbcCall.execute(params);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> emails = (List<Map<String, Object>>) result.get("emails");

        return emails;
    }
}
