package co.edu.uniquindio.proyecto.servicios.implementaciones;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.dto.ResponderComentarioDTO;
import co.edu.uniquindio.proyecto.model.documentos.Comentario;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.interfaces.ComentarioServicio;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {

    private ComentarioRepo comentarioRepo;
    private NegocioRepo negocioRepo;

    @Override
    public void crearComentario(CrearComentarioDTO crearComentarioDTO) throws Exception{
        Comentario comentario = new Comentario();
        comentario.setCalificacion(crearComentarioDTO.calificacion());
        comentario.setCodigoCliente(crearComentarioDTO.codigoCliente());
        comentario.setCodigoNegocio(crearComentarioDTO.codigoNegocio());
        comentario.setMensaje(crearComentarioDTO.mensaje());
        comentario.setFecha(LocalDateTime.now());

        comentarioRepo.save(comentario);
    }

    @Override
    public void responderComentario(ResponderComentarioDTO responderComentarioDTO) throws Exception{
        Optional<Comentario> comentarioOptional = comentarioRepo.findById(responderComentarioDTO.codigoComentario());
        if(comentarioOptional.isEmpty()){
            throw new Exception("El id del comentario no existe");
        }

        Comentario comentario = comentarioOptional.get();
        comentario.setRespuesta(responderComentarioDTO.mensaje());

        comentarioRepo.save(comentario);
    }

    @Override
    public List<ComentarioDTO> listarComentarioNegocio(String codigoNegocio) throws Exception{
        List<Comentario> comentariosNegocio = comentarioRepo.findAllByCodigo(codigoNegocio);

        List<ComentarioDTO> comentarios = new ArrayList<>();
        for(Comentario comentario: comentariosNegocio){
            comentarios.add(new ComentarioDTO(comentario.getCalificacion(),
                    comentario.getCodigoCliente(),
                    comentario.getMensaje(),
                    comentario.getRespuesta()));
        }
        return comentarios;
    }

    @Override
    public int calcularPromedioCalificaciones(String codigoNegocio) throws Exception{
        List<Comentario> comentariosNegocio = comentarioRepo.findAllByCodigo(codigoNegocio);

        int suma = 0;
        for(Comentario comentario : comentariosNegocio){
            suma = comentario.getCalificacion()+suma;
        }

        return suma/comentariosNegocio.size()+1;
    }
}
