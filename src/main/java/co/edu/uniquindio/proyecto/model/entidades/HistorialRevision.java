package co.edu.uniquindio.proyecto.model.entidades;

import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HistorialRevision implements Serializable {
    private String descripcion;
    private EstadoNegocio estadoNegocio;
    private LocalDateTime fecha;
    private String codigoModerador;

}
