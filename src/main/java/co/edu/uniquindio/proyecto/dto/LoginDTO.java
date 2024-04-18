package co.edu.uniquindio.proyecto.dto;import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record LoginDTO(
        @NotBlank
        String correoElectronico,
        @NotBlank
        @Length(min = 5)String password
) {
}
