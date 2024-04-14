package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CrearOfertaDTO(
        @NotNull
        double descuento,
        @NotBlank
        String codigoNegocio,
        @NotBlank
        String descripcion,
        @NotNull
        LocalDateTime inicioOferta,
        @NotNull
        LocalDateTime finOferta) {
}
