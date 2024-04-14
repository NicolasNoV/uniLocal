package co.edu.uniquindio.proyecto.model.documentos;

import co.edu.uniquindio.proyecto.model.entidades.Cuenta;
import co.edu.uniquindio.proyecto.model.enums.TipoNegocio;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document("CLIENTES")
public class Cliente extends Cuenta implements Serializable {

    private String fotoPerfil;
    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String nickname;
    private String ciudad;
    private String correoElectronico;
    private String password;
    List<Negocio> favoritos;
    List<String> historialBusquedaNombre;
    List<TipoNegocio> historialBusquedaTipo;

}
