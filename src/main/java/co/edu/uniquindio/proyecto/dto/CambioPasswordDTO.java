package co.edu.uniquindio.proyecto.dto;

public record CambioPasswordDTO(
        String nuevaPassword,
        String id,
        String token
) {
}
