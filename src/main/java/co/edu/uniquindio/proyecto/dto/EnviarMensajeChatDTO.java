package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record EnviarMensajeChatDTO(
        @NotBlank
        String codigoChat,
        @NotBlank
        String mensaje,
        @NotBlank
        String codigoRemitente) {
}
