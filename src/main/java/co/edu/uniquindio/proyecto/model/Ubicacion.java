package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Ubicacion implements Serializable {
    private double longitud;
    private double latitud;
}
