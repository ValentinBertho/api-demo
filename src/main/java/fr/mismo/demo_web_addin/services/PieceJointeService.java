package fr.mismo.demo_web_addin.services;

import fr.mismo.demo_web_addin.dto.PieceJointeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PieceJointeService {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> enregistrer(PieceJointeRequest request){

        return request.getAttachments().stream().map(att -> {

            SimpleJdbcCall jdbcCall =
                    new SimpleJdbcCall(dataSource)
                            .withProcedureName("SP_ATHENEO_ENREGISTRER_PIECE_JOINTE");

            Map<String,Object> result =
                    jdbcCall.execute(
                            Map.of(
                                    "NOM_FICHIER", att.getName(),
                                    "TAILLE", att.getSize(),
                                    "TYPE_MIME", att.getContentType(),
                                    "ID_OUTLOOK", att.getId(),
                                    "EMAIL_SOURCE", request.getEmailFrom(),
                                    "SUJET_MAIL", request.getEmailSubject(),
                                    "UTILISATEUR", "SYSTEM"
                            )
                    );

            return Map.of(
                    "id", result.get("PIECE_JOINTE_ID"),
                    "nomFichier", att.getName(),
                    "taille", att.getSize()
            );

        }).toList();

    }

}