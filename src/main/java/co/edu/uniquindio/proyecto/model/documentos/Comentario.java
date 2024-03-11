package co.edu.uniquindio.proyecto.model.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Comentario implements Serializable {
    private LocalDateTime fecha;
    private int calificacion;
    private String codigoCliente;
    private String codigoNegocio;
    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String mensaje;
    private String respuesta;

}
