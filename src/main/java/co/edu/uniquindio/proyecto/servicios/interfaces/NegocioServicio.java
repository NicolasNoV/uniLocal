package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;

import java.util.List;

public interface NegocioServicio {

    void crearNegocio(NegocioDTO negocioDTO) throws Exception;
    void actualizarNegocio(ActualizarNegocioDTO actualizarNegocioDTO) throws Exception;
    void eliminarNegocio(String codigoNegocio) throws Exception;
    List<NegocioEncontradoDTO> buscarNeogocios(String busqueda) throws Exception;
    List<NegocioEncontradoDTO> filtrarPorEstado(EstadoNegocio estadoNegocio) throws Exception;
    List<NegocioEncontradoDTO> listarNegociosPropietario(String nombrePropietario) throws Exception;
    void cambiarEstado(CambiarEstadoDTO cambiarEstadoDTO) throws Exception;
    void registrarRevision(HistorialRevisionDTO historialRevisionDTO) throws Exception;
    
}
