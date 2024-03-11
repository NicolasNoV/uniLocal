package co.edu.uniquindio.proyecto.model.entidades;

import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Cuenta implements Serializable {

    private String nombre;
    private String password;
    private String email;
    private EstadoRegistro estado;
}
