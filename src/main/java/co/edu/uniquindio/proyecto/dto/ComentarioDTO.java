package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ComentarioDTO(

        @NotBlank @Length(max = 5) int calificacion,
        @NotBlank String codigoCliente,
        @NotBlank String mensaje,
        @NotBlank String respuesta

) {
}
