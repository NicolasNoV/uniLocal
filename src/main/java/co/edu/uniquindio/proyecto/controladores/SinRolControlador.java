package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.servicios.interfaces.AutenticacionServicio;
import co.edu.uniquindio.proyecto.servicios.interfaces.ClienteServicio;
import co.edu.uniquindio.proyecto.servicios.interfaces.ComentarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sinRol")
public class SinRolControlador {

    private final AutenticacionServicio autenticacionServicio;
    private final ClienteServicio clienteServicio;
    private final ComentarioServicio comentarioServicio;

    @PostMapping("/iniciar-sesion-cliente")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionCliente(@Valid @RequestBody LoginDTO loginDTO) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, autenticacionServicio.iniciarSesionCliente(loginDTO) ) );
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
}