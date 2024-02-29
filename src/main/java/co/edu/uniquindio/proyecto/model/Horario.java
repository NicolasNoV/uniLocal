package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Horario implements Serializable {
    private LocalTime horaFin;
    private LocalTime horaInicio;
    private String dia;

}
