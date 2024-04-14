package co.edu.uniquindio.proyecto.model.entidades;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Oferta {

    private double descuento;
    private String codigoNegocio;
    private String descripcion;
    private LocalDateTime inicioOferta;
    private LocalDateTime finOferta;

}