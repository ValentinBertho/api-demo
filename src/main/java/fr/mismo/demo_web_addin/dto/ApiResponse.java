package fr.mismo.demo_web_addin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Enveloppe générique pour toutes les réponses de l'API")
public class ApiResponse<T> {

    @Schema(description = "Indique si l'opération s'est terminée avec succès", example = "true")
    private boolean success;

    @Schema(description = "Message descriptif du résultat", example = "Mail enregistré")
    private String message;

    @Schema(description = "Données retournées par l'opération (null en cas d'erreur)")
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
