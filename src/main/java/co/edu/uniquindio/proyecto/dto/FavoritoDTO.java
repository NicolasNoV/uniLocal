package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record FavoritoDTO(
        @NotBlank String codigoNegocio,
        @NotBlank String codigoCliente
) {
}
