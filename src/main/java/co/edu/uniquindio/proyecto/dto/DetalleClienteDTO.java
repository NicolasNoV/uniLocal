package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record DetalleClienteDTO(

        @NotBlank @Length(max = 10)String id,

        @NotBlank @Length(max = 100) String nombre,
       String foto,

        @NotBlank @Length(max = 100)String nickName,

        @NotBlank @Email @Length(max = 100)  String email,

        @NotBlank @Length(max = 50)  String ciudad




) {
}
