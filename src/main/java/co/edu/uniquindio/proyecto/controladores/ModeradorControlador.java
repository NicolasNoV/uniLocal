package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.CambiarEstadoDTO;
import co.edu.uniquindio.proyecto.dto.HistorialRevisionDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.NegocioEncontradoDTO;
import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.servicios.interfaces.NegocioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moderador")
public class ModeradorControlador {

    private final NegocioServicio negocioServicio;

    @GetMapping("/filtrar-por-estado/{estadoNegocio}")
    public ResponseEntity<MensajeDTO<List<NegocioEncontradoDTO>>> filtrarPorEstado(@PathVariable EstadoNegocio estadoNegocio)throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, negocioServicio.filtrarPorEstado(estadoNegocio)));
    }
    @GetMapping("/listar-negocios-propietario/{nombrePropietario}")
    public ResponseEntity<MensajeDTO<List<NegocioEncontradoDTO>>> listarNegociosPropietario(@PathVariable String nombrePropietario)throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, negocioServicio.listarNegociosPropietario(nombrePropietario)));
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
