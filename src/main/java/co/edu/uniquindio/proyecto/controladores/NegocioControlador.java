package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.servicios.interfaces.NegocioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/negocios")
public class NegocioControlador {

    private final NegocioServicio negocioServicio;

    @PostMapping("/crear-negocio")
    public ResponseEntity<MensajeDTO<String>> editarPerfil(@Valid @RequestBody NegocioDTO negocioDTO) throws Exception {
        negocioServicio.crearNegocio(negocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio creado correctamente"));
    }

    @PutMapping("/actualizar-negocio")
    public ResponseEntity<MensajeDTO<String>> editarPerfil(@Valid @RequestBody ActualizarNegocioDTO actualizarNegocioDTODTO)throws Exception {
        negocioServicio.actualizarNegocio(actualizarNegocioDTODTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio editado correctamente"));
    }

    @DeleteMapping("/eliminar-negocio/{codigoNegocio}")
    public ResponseEntity<MensajeDTO<String>> eliminarNegocio(@PathVariable String codigoNegocio)throws Exception{
        negocioServicio.eliminarNegocio(codigoNegocio);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Negocio eliminado correctamente"));
    }
    @GetMapping("/buscar-negocios/{busqueda}")
    public ResponseEntity<MensajeDTO<List<NegocioEncontradoDTO>>> buscarNeogocios(@PathVariable String busqueda)throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, negocioServicio.buscarNeogocios(busqueda) ) );
    }
    @GetMapping("/filtrar-por-estado/{estadoNegocio}")
    public ResponseEntity<MensajeDTO<List<NegocioEncontradoDTO>>> filtrarPorEstado(@PathVariable EstadoNegocio estadoNegocio)throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, negocioServicio.buscarNeogocios(String.valueOf(estadoNegocio)) ) );
    }
    @GetMapping("/listar-negocios-propietario/{nombrePropietario}")
    public ResponseEntity<MensajeDTO<List<NegocioEncontradoDTO>>> listarNegociosPropietario(@PathVariable String nombrePropietario)throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, negocioServicio.buscarNeogocios(nombrePropietario) ) );
    }

    @PutMapping("/cambiar-estado")
    public ResponseEntity<MensajeDTO<String>> cambiarEstado(@Valid @RequestBody CambiarEstadoDTO cambiarEstadoDTO)throws Exception{
        negocioServicio.cambiarEstado(cambiarEstadoDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "estado cambiado correctamente"));
    }
    @PostMapping("/registrar-revision")
    public ResponseEntity<MensajeDTO<String>> registrarRevision(@Valid @RequestBody HistorialRevisionDTO historialRevisionDTO) throws Exception{
        negocioServicio.registrarRevision(historialRevisionDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "revisión registrada correctamente"));
    }
}


