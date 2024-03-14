package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClienteServicio {

    String registrarse(RegistroClienteDTO registroClienteDTO) throws Exception;
    void editarPerfil(ActualizarClienteDTO actualizarClienteDTO)throws Exception;
    void eliminarCuenta(String idCliente)throws Exception;
    void enviarLinkRecuperacion(String correoElectronico)throws Exception;
    void recuperarPassword(CambioPasswordDTO cambioPasswordDTO)throws Exception;
    void iniciarSesion(InicioSesionDTO inicioSesionDTO)throws Exception;

    DetalleClienteDTO obtenerCliente(String id)throws Exception;

    List<ItemClienteDTO> listarClientes(int pagina)throws Exception;


}
