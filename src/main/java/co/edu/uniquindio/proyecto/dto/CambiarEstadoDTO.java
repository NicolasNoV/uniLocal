package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CambiarEstadoDTO(
        @NotBlank String codigoNegocio,
        @NotNull EstadoNegocio estadoNegocio
        ) {
}
