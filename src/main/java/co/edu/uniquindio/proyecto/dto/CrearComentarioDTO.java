package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CrearComentarioDTO (

        @NotNull int calificacion,
        @NotBlank String codigoCliente,
        @NotBlank String codigoNegocio,
        @NotBlank String mensaje

){}
