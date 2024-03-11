package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ActualizarClienteDTO(
        @NotBlank @Length(max = 100) String nombre,
        @NotBlank @Email @Length(max = 100)String correoElectronico,
        @NotBlank @Length(max = 50)String ciudad,
        String fotoPerfil,
        String id)
{
}