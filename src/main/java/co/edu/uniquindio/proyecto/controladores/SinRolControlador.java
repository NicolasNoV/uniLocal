package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.servicios.interfaces.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sinRol")
public class SinRolControlador {

    private final AutenticacionServicio autenticacionServicio;
    private final ClienteServicio clienteServicio;
    private final ComentarioServicio comentarioServicio;
    private final NegocioServicio negocioServicio;
    private final ImagenesServicio imagenesServicio;

    @PostMapping("/iniciar-sesion-cliente")
    public ResponseEntity<String> iniciarSesionCliente(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        // Obtenemos el código del cliente desde el servicio de autenticación
        String codigoCliente = autenticacionServicio.iniciarSesionCliente(loginDTO);

        // Retornamos el código del cliente en la respuesta
        return ResponseEntity.ok(codigoCliente);
    }
    @PostMapping("/iniciar-sesion-admin")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionAdmin(@Valid @RequestBody LoginDTO loginDTO) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, autenticacionServicio.iniciarSesionAdmin(loginDTO) ) );
    }
    @PostMapping("/registrarse")
    public ResponseEntity<MensajeDTO<String>> registrarse(@Valid @RequestBody RegistroClienteDTO registroClienteDTO) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, clienteServicio.registrarse(registroClienteDTO)) );
    }
    @GetMapping("/listar-comentario-negocio/{codigoNegocio}")
    public ResponseEntity<MensajeDTO<List<ComentarioDTO>>> listarComentarioNegocio(@PathVariable String codigoNegocio) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, comentarioServicio.listarComentarioNegocio(codigoNegocio) ) );
    }
    @GetMapping("/calcular-promedio-calificaciones/{codigoNegocio}")
    public ResponseEntity<MensajeDTO<Integer>> calcularPromedioCalificaciones(@PathVariable String codigoNegocio) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, comentarioServicio.calcularPromedioCalificaciones(codigoNegocio) ) );
    }
    @GetMapping("/listar-tipo-negocio")
    public ResponseEntity<MensajeDTO<TipoNegocio[]>> listarTipoNegocios() throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, negocioServicio.listarTipoNegocios() ) );
    }
    @PutMapping("/subir-imagen")
    public ResponseEntity<MensajeDTO<Map>> subirImagen(MultipartFile imagen) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, imagenesServicio.subirImagen(imagen) ) );
    }
    @DeleteMapping("/eliminar-imagen")
    public ResponseEntity<MensajeDTO<Map>> subirImagen(String idImagen) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, imagenesServicio.eliminarImagen(idImagen) ) );
    }

}