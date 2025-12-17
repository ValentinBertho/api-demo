package fr.mismo.demo_web_addin.service.impl;

import fr.mismo.demo_web_addin.service.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final DataSource dataSource;

    @Override
    public Map<String, Object> obtenirStatistiques() {
        log.info("📊 Récupération des statistiques");

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_STATS");

            Map<String, Object> stats = jdbcCall.execute();
            log.info("✅ Statistiques récupérées");
            return stats;

        } catch (Exception e) {
            log.error("❌ Erreur récupération statistiques", e);
            return Map.of();
        }
    }
}
