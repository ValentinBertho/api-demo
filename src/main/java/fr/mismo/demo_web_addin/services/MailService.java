package fr.mismo.demo_web_addin.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final DataSource dataSource;

    public Map<String,Object> enregistrerMail(
            MultipartFile file,
            String from,
            String fromName,
            String subject,
            String body,
            String date
    ) {

        try {

            OffsetDateTime odt = OffsetDateTime.parse(date);
            LocalDateTime dateReception = odt.toLocalDateTime();

            byte[] emlBytes = file.getBytes();

            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_ENREGISTRER_MAIL");

            Map<String,Object> result = jdbcCall.execute(
                    Map.of(
                            "EXPEDITEUR", from,
                            "EXPEDITEUR_NOM", fromName,
                            "SUJET", subject,
                            "CONTENU", body,
                            "DATE_RECEPTION", dateReception,
                            "UTILISATEUR", "SYSTEM"
                    )
            );

            Long emailId = ((Number)result.get("EMAIL_ID")).longValue();

            Path path = Paths.get("archives/" + emailId + ".eml");
            Files.createDirectories(path.getParent());
            Files.write(path, emlBytes);

            return Map.of(
                    "id", emailId,
                    "expediteur", from,
                    "sujet", subject
            );

        } catch (Exception e) {
            log.error("Erreur enregistrement mail", e);
            throw new RuntimeException(e);
        }

    }

}