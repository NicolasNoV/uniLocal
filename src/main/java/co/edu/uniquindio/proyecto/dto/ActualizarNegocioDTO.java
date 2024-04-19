package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.enums.TipoNegocio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ActualizarNegocioDTO(

        @NotBlank String codigoNegocio,
        @NotNull double longitud,
        @NotNull double latitud,
        @NotBlank String nombre,
        @NotBlank String descripcion,
        @NotNull List<HorarioDTO> horarios,
        @NotNull List<String> imagenes,
        @NotBlank String codigoCliente,
        @NotNull TipoNegocio tipoNegocio,
        @NotNull List<String> telefonos
) {
}
