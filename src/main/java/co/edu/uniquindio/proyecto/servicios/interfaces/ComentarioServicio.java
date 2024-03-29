package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.dto.ResponderComentarioDTO;

import java.util.List;

public interface ComentarioServicio {
    void crearComentario(CrearComentarioDTO crearComentarioDTO) throws Exception;
    void responderComentario(ResponderComentarioDTO responderComentarioDTO) throws Exception;
    List<ComentarioDTO> listarComentarioNegocio(String codigoNegocio) throws Exception;
    int calcularPromedioCalificaciones(String codigoNegocio) throws Exception;
}
