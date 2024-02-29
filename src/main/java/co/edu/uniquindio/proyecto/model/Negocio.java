package co.edu.uniquindio.proyecto.model;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.enums.TipoNegocio;
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
@Document
public class Negocio implements Serializable {

    private Ubicacion ubicacion;
    private String nombre;
    private String descripcion;
    private List<Horario> horarios;
    private EstadoRegistro estadoRegistro;
    private List<String> imagenes;
    private List<HistorialRevision> historialRevisiones;
    @Id
    @EqualsAndHashCode.Include
    private String codigo;
    private String codigoCliente;
    private TipoNegocio tipoNegocio;
    private List<String> telefonos;

}
