package fr.mismo.demo_web_addin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI atheneoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ATHENEO Demo API")
                        .description("""
                                API REST de l'Add-in Outlook ATHENEO.

                                Permet à l'add-in de :
                                - **Enregistrer** des e-mails et pièces jointes dans l'ERP ATHENEO
                                - **Créer** des demandes (incidents) depuis un e-mail
                                - **Rechercher** un interlocuteur par adresse e-mail

                                Les fichiers sont déposés via le service SOAP interne `WSDocument`.
                                Le contexte de rattachement (Devis, Intervention, Contrat…) est déduit \
                                automatiquement à partir de la session active de l'utilisateur destinataire.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("ATHENEO / MISMO")
                                .url("https://www.atheneo.fr")
                                .email("support@atheneo.fr"))
                        .license(new License()
                                .name("Propriétaire")
                                .url("https://www.atheneo.fr")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080/atheneo")
                                .description("Serveur local (développement)"),
                        new Server()
                                .url("https://api-demo-production-3184.up.railway.app/atheneo")
                                .description("Serveur de production (Railway)")))
                .tags(List.of(
                        new Tag().name("Health").description("Vérification de l'état du service"),
                        new Tag().name("Mails").description("Enregistrement des e-mails dans ATHENEO"),
                        new Tag().name("Interlocuteurs").description("Recherche de contacts dans ATHENEO"),
                        new Tag().name("Demandes").description("Création de demandes / incidents dans ATHENEO"),
                        new Tag().name("Pièces jointes").description("Enregistrement des pièces jointes dans ATHENEO")
                ));
    }
}
