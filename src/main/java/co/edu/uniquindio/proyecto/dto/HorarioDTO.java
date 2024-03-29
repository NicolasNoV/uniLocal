package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record HorarioDTO(
        @NotNull LocalTime horaFin,
        @NotNull LocalTime horaInicio,
        @NotBlank String dia
) {
}
