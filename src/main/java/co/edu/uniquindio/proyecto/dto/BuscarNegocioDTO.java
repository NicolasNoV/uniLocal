package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.enums.TipoNegocio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BuscarNegocioDTO(
        @NotBlank
        String busqueda,
        @NotNull
        TipoNegocio tipoNegocio,
        @NotBlank
        String idCliente
) {
}
