package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record HistorialRevisionDTO(

        @NotBlank String descripcion,
        @NotNull EstadoNegocio estadoNegocio,
        @NotNull LocalDateTime fecha,
        @NotBlank String codigoModerador
) {
}
