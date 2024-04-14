package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearChatDTO(
        @NotBlank
        String codigoCliente,
        @NotBlank
        String codigoPropietario) {
}
