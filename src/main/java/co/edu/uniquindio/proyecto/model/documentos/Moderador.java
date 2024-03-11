package co.edu.uniquindio.proyecto.model.documentos;

import co.edu.uniquindio.proyecto.model.entidades.Cuenta;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Moderador extends Cuenta implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    private String codigo;
}
