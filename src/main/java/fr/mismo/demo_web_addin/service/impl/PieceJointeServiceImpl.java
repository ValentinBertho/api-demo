package fr.mismo.demo_web_addin.service.impl;

import fr.mismo.demo_web_addin.service.PieceJointeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class PieceJointeServiceImpl implements PieceJointeService {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> enregistrerPieceJointe(
            String nomFichier,
            Long taille,
            String typeMime,
            String idOutlook,
            String emailSource,
            String sujetMail
    ) {
        log.info("📎 Enregistrement pièce jointe: {}", nomFichier);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_ATHENEO_ENREGISTRER_PIECE_JOINTE")
                .declareParameters(
                        new SqlParameter("NOM_FICHIER", Types.VARCHAR),
                        new SqlParameter("TAILLE", Types.BIGINT),
                        new SqlParameter("TYPE_MIME", Types.VARCHAR),
                        new SqlParameter("ID_OUTLOOK", Types.VARCHAR),
                        new SqlParameter("EMAIL_SOURCE", Types.VARCHAR),
                        new SqlParameter("SUJET_MAIL", Types.NVARCHAR),
                        new SqlParameter("UTILISATEUR", Types.VARCHAR),
                        new SqlOutParameter("PIECE_JOINTE_ID", Types.BIGINT)
                );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("NOM_FICHIER", nomFichier)
                .addValue("TAILLE", taille)
                .addValue("TYPE_MIME", typeMime)
                .addValue("ID_OUTLOOK", idOutlook)
                .addValue("EMAIL_SOURCE", emailSource)
                .addValue("SUJET_MAIL", sujetMail)
                .addValue("UTILISATEUR", "SYSTEM");

        Map<String, Object> result = jdbcCall.execute(params);
        Long pieceJointeId = (Long) result.get("PIECE_JOINTE_ID");

        log.info("✅ Pièce jointe enregistrée avec ID: {}", pieceJointeId);

        return Map.of(
                "id", pieceJointeId,
                "nomFichier", nomFichier,
                "taille", taille
        );
    }

    @Override
    public List<Map<String, Object>> enregistrerPiecesJointes(
            List<Map<String, Object>> attachments,
            String emailSource,
            String sujetMail
    ) {
        log.info("📎 Enregistrement de {} pièces jointes", attachments.size());

        return attachments.stream()
                .map(att -> enregistrerPieceJointe(
                        (String) att.get("name"),
                        ((Number) att.get("size")).longValue(),
                        (String) att.get("contentType"),
                        (String) att.get("id"),
                        emailSource,
                        sujetMail
                ))
                .toList();
    }

    @Override
    public List<Map<String, Object>> listerPiecesJointes() {
        log.info("📎 Listage des pièces jointes");

        String sql = "SELECT * FROM PIECE_JOINTE ORDER BY DATE_CREATION DESC";
        return jdbcTemplate.queryForList(sql);
    }
}
