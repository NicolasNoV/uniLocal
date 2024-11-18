package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ClienteDTO(
        String nombre,
        String correoElectronico,
        String ciudad,
        String fotoPerfil
) {
}
