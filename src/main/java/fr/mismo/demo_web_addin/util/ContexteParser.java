package fr.mismo.demo_web_addin.util;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContexteParser {

    // Mapping entre le libellé du contexte et la clé ATHENEO
    private static final Map<String, String> MAPPING_CONTEXTE = Map.of(
            "Devis",        "NO_DEVIS",
            "Intervention", "NO_INTERVENTION",
            "Incident",     "NO_INCIDENT",
            "Contrat",      "NO_CONTRAT",
            "Parc",         "NO_PARC",
            "Societe",      "NO_SOCIETE"
    );

    private static final Pattern PATTERN = Pattern.compile("Fiche (\\w+) - N°(\\d+)");

    /**
     * Parse "Fiche Devis - N°69392" → {"NO_DEVIS": "69392"}
     */
    public static Optional<Map.Entry<String, String>> parse(String contexte) {
        if (contexte == null) return Optional.empty();

        Matcher matcher = PATTERN.matcher(contexte);
        if (!matcher.find()) return Optional.empty();

        String type   = matcher.group(1); // "Devis"
        String numero = matcher.group(2); // "69392"

        String cle = MAPPING_CONTEXTE.get(type);
        if (cle == null) return Optional.empty();

        return Optional.of(Map.entry(cle, numero));
    }
}