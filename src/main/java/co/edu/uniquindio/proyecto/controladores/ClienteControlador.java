package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.servicios.interfaces.ClienteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clientes")
public class ClienteControlador {

    private final ClienteServicio clienteServicio;

    @PostMapping("/registrarse")
    public ResponseEntity<MensajeDTO<String>> registrarse(@Valid @RequestBody RegistroClienteDTO registroClienteDTO) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, clienteServicio.registrarse(registroClienteDTO)) );
    }
    @PutMapping("/editar-perfil")
    public ResponseEntity<MensajeDTO<String>> editarPerfil(@Valid @RequestBody ActualizarClienteDTO actualizarClienteDTO)throws Exception{
        clienteServicio.editarPerfil(actualizarClienteDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Perfil editado correctamente"));
    }
    @DeleteMapping("/eliminar-cuenta/{idCliente}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuenta(@PathVariable String idCliente)throws Exception{
        clienteServicio.eliminarCuenta(idCliente);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Cuenta eliminada correctamente"));
    }

    public ResponseEntity<MensajeDTO<String>> recuperarPassword(@Valid @RequestBody CambioPasswordDTO cambioPasswordDTO)throws Exception{
        clienteServicio.recuperarPassword(cambioPasswordDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Se ha cambiado la password correctamente"));
    }

    public ResponseEntity<MensajeDTO<String>> iniciarSesion(@Valid @RequestBody InicioSesionDTO inicioSesionDTO)throws Exception{
        clienteServicio.iniciarSesion(inicioSesionDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Se ha iniciado sesión correctamente"));
    }
    @GetMapping("/obtener-cliente/{id}")
    public ResponseEntity<MensajeDTO<DetalleClienteDTO>> obtenerCliente(@PathVariable String id)throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, clienteServicio.obtenerCliente(id) ) );
    }
    @GetMapping("/listar-clientes/{pagina}")
    public ResponseEntity<MensajeDTO<List<ItemClienteDTO>>> listarClientes(@PathVariable int pagina)throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, clienteServicio.listarClientes(pagina) ) );
    }
    @PostMapping("/agregar-negocio-favorito")
    public ResponseEntity<MensajeDTO<String>> agregarNegocioFavoritos(@Valid @RequestBody FavoritoDTO favoritoDTO) throws Exception{
        clienteServicio.agregarNegocioFavoritos(favoritoDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Se ha agregado el negocio a favoritos correctamente"));
    }
    @DeleteMapping("/quitar-negocio-favorito")
    public ResponseEntity<MensajeDTO<String>> quitarNegocioFavoritos(@Valid @RequestBody FavoritoDTO favoritoDTO) throws Exception{
        clienteServicio.quitarNegocioFavoritos(favoritoDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Se ha quitado el negocio de favoritos correctamente"));
    }
    @GetMapping("/recomendar-negocio-busqueda/{codigoUsuario}")
    public ResponseEntity<MensajeDTO<List<NegocioEncontradoDTO>>> recomendarNegocioBusqueda(@PathVariable String codigoUsuario) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, clienteServicio.recomendarNegocioBusqueda(codigoUsuario) ) );
    }
}