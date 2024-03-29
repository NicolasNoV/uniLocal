package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record ResponderComentarioDTO (

        @NotBlank String codigoComentario,
        @NotBlank String mensaje

){
}
