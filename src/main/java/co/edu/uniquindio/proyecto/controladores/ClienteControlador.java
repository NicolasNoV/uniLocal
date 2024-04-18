package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.*;
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
@RequestMapping("/api/clientes")
public class ClienteControlador {

    private final ClienteServicio clienteServicio;
    private final ComentarioServicio comentarioServicio;
    private final EmailServicio emailServicio;
    private final ImagenesServicio imagenesServicio;
    private final NegocioServicio negocioServicio;

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
    @PutMapping("/cambiar-password")
    public ResponseEntity<MensajeDTO<String>> recuperarPassword(@Valid @RequestBody CambioPasswordDTO cambioPasswordDTO)throws Exception{
        clienteServicio.recuperarPassword(cambioPasswordDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Se ha cambiado la password correctamente"));
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
    @PostMapping("/enviar-correo")
    public ResponseEntity<MensajeDTO<String>> enviarCorreo(@Valid @RequestBody EmailDTO emailDTO) throws Exception{
        emailServicio.enviarCorreo(emailDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Coreo enviado correctamente"));
    }
    @PostMapping("/subir-imagen")
    public ResponseEntity<MensajeDTO<Map>> subirImagen(MultipartFile imagen) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, imagenesServicio.subirImagen(imagen) ) );
    }
    @DeleteMapping("/eliminar-imagen/{idImagen}")
    public ResponseEntity<MensajeDTO<Map>> eliminarImagen(@PathVariable String idImagen) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, imagenesServicio.eliminarImagen(idImagen) ) );
    }
    @PostMapping("/crear-negocio")
    public ResponseEntity<MensajeDTO<String>> crearNegocio(@Valid @RequestBody NegocioDTO negocioDTO) throws Exception {
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
    @GetMapping("/buscar-negocios")
    public ResponseEntity<MensajeDTO<List<NegocioEncontradoDTO>>> buscarNeogocios(@Valid @RequestBody BuscarNegocioDTO buscarNegocioDTO)throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, negocioServicio.buscarNeogocios(buscarNegocioDTO) ) );
    }
}