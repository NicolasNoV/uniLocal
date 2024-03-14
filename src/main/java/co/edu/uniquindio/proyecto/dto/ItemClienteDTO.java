package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ItemClienteDTO(

        String codigo,
        @NotBlank @Length(max = 10) String nombre,
        String fotoPerfil,

        @NotBlank @Length(max = 100)  String nickName,

        @NotBlank @Length(max = 50) String ciudad



) {
}
