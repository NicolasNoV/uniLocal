package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dto.ActualizarNegocioDTO;
import co.edu.uniquindio.proyecto.dto.HistorialRevisionDTO;
import co.edu.uniquindio.proyecto.dto.NegocioDTO;
import co.edu.uniquindio.proyecto.dto.NegocioEncontradoDTO;

import java.util.List;

public interface NegocioServicio {

    void crearNegocio(NegocioDTO negocioDTO);
    void actualizarNegocio(ActualizarNegocioDTO actualizarNegocioDTO);
    void eliminarNegocio(String codigoNegocio);
    List<NegocioEncontradoDTO> buscarNeogocios(String busqueda);
    void filtrarPorEstado(String estadoNegocio);
    List<NegocioDTO> listarNegociosPropietario(String nombrePropietario);
    void cambiarEstado(String codigoNegocio);
    void registrarRevision(HistorialRevisionDTO historialRevisionDTO);
    void aprobarNegocio(String codigoNegocio);
    void rechazarNegocio(String codigoNegocio);

}
