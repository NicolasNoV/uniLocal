package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dto.*;

public interface ClienteServicio {

    String registrarse(RegistroClienteDTO registroClienteDTO) throws Exception;
    void editarPerfil(ActualizarClienteDTO actualizarClienteDTO)throws Exception;
    void eliminarCuenta(String idCliente)throws Exception;
    void enviarLinkRecuperacion(String correoElectronico)throws Exception;
    void recuperarPassword(CambioPasswordDTO cambioPasswordDTO)throws Exception;
    void iniciarSesion(InicioSesionDTO inicioSesionDTO)throws Exception;
}
