package fr.mismo.demo_web_addin.service.impl;

import fr.mismo.demo_web_addin.service.InterlocuteurService;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterlocuteurServiceImpl implements InterlocuteurService {

    private final DataSource dataSource;

    @Override
    public Optional<Map<String, Object>> rechercherParEmail(String email) {
        log.info("👤 Recherche interlocuteur: {}", email);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_ATHENEO_RECHERCHER_INTERLOCUTEUR")
                .declareParameters(new SqlParameter("EMAIL", Types.VARCHAR))
                .returningResultSet("interlocuteur", (rs, rowNum) -> Map.of(
                        "id", rs.getLong("INTERLOCUTEUR_ID"),
                        "email", rs.getString("EMAIL"),
                        "prenom", rs.getString("PRENOM"),
                        "nom", rs.getString("NOM"),
                        "societe", rs.getString("SOCIETE")
                ));

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("EMAIL", email.toLowerCase());

        Map<String, Object> result = jdbcCall.execute(params);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> interlocuteurs = (List<Map<String, Object>>) result.get("interlocuteur");

        if (interlocuteurs != null && !interlocuteurs.isEmpty()) {
            log.info("✅ Interlocuteur trouvé");
            return Optional.of(interlocuteurs.get(0));
        }

        log.info("⚠️ Interlocuteur non trouvé");
        return Optional.empty();
    }

    @Override
    public Long creerInterlocuteur(
            String email,
            String nomComplet,
            String prenom,
            String nom,
            String societe
    ) {
        log.info("🆕 Création interlocuteur: {}", email);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_ATHENEO_CREER_INTERLOCUTEUR")
                .declareParameters(
                        new SqlParameter("EMAIL", Types.VARCHAR),
                        new SqlParameter("NOM_COMPLET", Types.VARCHAR),
                        new SqlParameter("PRENOM", Types.VARCHAR),
                        new SqlParameter("NOM", Types.VARCHAR),
                        new SqlParameter("SOCIETE", Types.VARCHAR),
                        new SqlParameter("UTILISATEUR", Types.VARCHAR),
                        new SqlOutParameter("INTERLOCUTEUR_ID", Types.BIGINT)
                );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("EMAIL", email.toLowerCase())
                .addValue("NOM_COMPLET", nomComplet)
                .addValue("PRENOM", prenom)
                .addValue("NOM", nom)
                .addValue("SOCIETE", societe)
                .addValue("UTILISATEUR", "SYSTEM");

        Map<String, Object> result = jdbcCall.execute(params);
        Long interlocuteurId = (Long) result.get("INTERLOCUTEUR_ID");

        log.info("✅ Interlocuteur créé avec ID: {}", interlocuteurId);
        return interlocuteurId;
    }

    @Override
    public Long rechercherOuCreer(String email, String nomComplet) {
        // Rechercher d'abord
        Optional<Map<String, Object>> interlocuteur = rechercherParEmail(email);

        if (interlocuteur.isPresent()) {
            return (Long) interlocuteur.get().get("id");
        }

        // Sinon créer
        String prenom = null;
        String nom = null;

        if (nomComplet != null && nomComplet.contains(" ")) {
            String[] parts = nomComplet.split(" ", 2);
            prenom = parts[0];
            nom = parts[1];
        } else {
            nom = nomComplet != null ? nomComplet : email.split("@")[0];
        }

        String domaine = email.split("@")[1];
        String societe = domaine.split("\\.")[0].toUpperCase();

        return creerInterlocuteur(email, nomComplet, prenom, nom, societe);
    }
}
