package co.edu.uniquindio.proyecto.model;

import co.edu.uniquindio.proyecto.enums.EstadoRegistro;

import java.io.Serializable;

public class Cuenta implements Serializable {
    private String nombre;
    private String password;
    private String email;
    private EstadoRegistro estado;
}
