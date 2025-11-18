package fr.mismo.demo_web_addin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "ATHENEO Demo API");
        response.put("version", "1.0.0");
        response.put("status", "✓ En ligne");
        response.put("endpoints", Map.of(
                "health", "/atheneo/api/health",
                "mails", "/atheneo/api/mails",
                "demandes", "/atheneo/api/demandes",
                "interlocuteurs", "/atheneo/api/interlocuteurs",
                "pieces-jointes", "/atheneo/api/pieces-jointes",
                "actions", "/atheneo/api/actions"
        ));
        return response;
    }
}