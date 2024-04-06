package co.edu.uniquindio.proyecto.dto;import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotBlank
        String correoElectronico,
        @NotBlank
        String password
) {
}
