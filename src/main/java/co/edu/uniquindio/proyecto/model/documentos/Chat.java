package co.edu.uniquindio.proyecto.model.documentos;

import co.edu.uniquindio.proyecto.model.entidades.Mensaje;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document("CHATS")
public class Chat {
    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private List<Mensaje> mensajes;
    private String codigoCliente;
    private String codigoPropietario;
    private EstadoRegistro estadoRegistro;

}