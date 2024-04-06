package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.servicios.interfaces.ImagenesServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/imagenes")
public class ImagenesControlador {

    private final ImagenesServicio imagenesServicio;
    @PostMapping("/subir-imagen")
    public ResponseEntity<MensajeDTO<Map>> subirImagen(MultipartFile imagen) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, imagenesServicio.subirImagen(imagen) ) );
    }
    @DeleteMapping("/eliminar-imagen/{idImagen}")
    public ResponseEntity<MensajeDTO<Map>> eliminarImagen(@PathVariable String idImagen) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, imagenesServicio.eliminarImagen(idImagen) ) );
    }

}
