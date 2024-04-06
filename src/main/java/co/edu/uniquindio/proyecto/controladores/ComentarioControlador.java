package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.servicios.interfaces.ComentarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comentarios")
public class ComentarioControlador {

    private final ComentarioServicio comentarioServicio;

    @PostMapping("/crear-comentario")
    public ResponseEntity<MensajeDTO<String>> crearComentario(@Valid @RequestBody CrearComentarioDTO crearComentarioDTO) throws Exception{
        comentarioServicio.crearComentario(crearComentarioDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Comentario creado correctamente"));
    }
    @PutMapping("/responder-comentario")
    public ResponseEntity<MensajeDTO<String>> responderComentario(@Valid @RequestBody ResponderComentarioDTO responderComentarioDTO) throws Exception{
        comentarioServicio.responderComentario(responderComentarioDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Respuesta creada correctamente"));
    }
    @GetMapping("/listar-comentario-negocio/{codigoNegocio}")
    public ResponseEntity<MensajeDTO<List<ComentarioDTO>>> listarComentarioNegocio(@PathVariable String codigoNegocio) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, comentarioServicio.listarComentarioNegocio(codigoNegocio) ) );
    }
    @GetMapping("/calcular-promedio-calificaciones/{codigoNegocio}")
    public ResponseEntity<MensajeDTO<Integer>> calcularPromedioCalificaciones(@PathVariable String codigoNegocio) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, comentarioServicio.calcularPromedioCalificaciones(codigoNegocio) ) );
    }



}
