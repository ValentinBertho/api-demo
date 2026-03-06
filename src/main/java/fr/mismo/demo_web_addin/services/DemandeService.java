package fr.mismo.demo_web_addin.services;

import fr.mismo.demo_web_addin.dto.CreateDemandeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DemandeService {

    private final InterlocuteurService interlocuteurService;
    private final DataSource dataSource;

    public Map<String,Object> creerDemande(CreateDemandeRequest request){

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_ATHENEO_CREER_DEMANDE");

        Map<String,Object> result = jdbcCall.execute(
                Map.of(
                        "EMAIL_CONTACT", request.getEmail(),
                        "CONTACT_NOM", request.getContactName(),
                        "TITRE", request.getSubject(),
                        "DESCRIPTION", request.getDescription(),
                        "SOURCE", request.getSource(),
                        "PRIORITE", request.getPriority(),
                        "TYPE", request.getType(),
                        "UTILISATEUR", "SYSTEM"
                )
        );

        return Map.of(
                "id", result.get("DEMANDE_ID"),
                "reference", result.get("REFERENCE")
        );

    }

}