package fr.mismo.demo_web_addin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wsdocument")
public class WsDocumentProperties {
    private String defaultUri;
    private String login;
    private String password;
    private NewDocumentProperties proprietesMail;
    private NewDocumentProperties proprietesPieceJointe;

    private WsDocumentTimeouts timeouts;

    @Data
    public static class NewDocumentProperties {
        private String typeDocument;
        // private String auteurDocument;
    }

    @Data
    public static class WsDocumentTimeouts {
        private Integer readTimeout;
        private Integer connectionTimeout;
    }
}

