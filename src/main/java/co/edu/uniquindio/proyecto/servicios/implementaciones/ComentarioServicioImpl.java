package co.edu.uniquindio.proyecto.servicios.implementaciones;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.ResponderComentarioDTO;
import co.edu.uniquindio.proyecto.model.documentos.Cliente;
import co.edu.uniquindio.proyecto.model.documentos.Comentario;
import co.edu.uniquindio.proyecto.model.documentos.Negocio;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.interfaces.ComentarioServicio;
import org.eclipse.angus.mail.handlers.text_html;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {

    private ComentarioRepo comentarioRepo;
    private NegocioRepo negocioRepo;
    private ClienteRepo clienteRepo;
    private EmailServicioImpl emailServicio;

    @Override
    public void crearComentario(CrearComentarioDTO crearComentarioDTO) throws Exception{
        Comentario comentario = new Comentario();
        comentario.setCalificacion(crearComentarioDTO.calificacion());
        comentario.setCodigoCliente(crearComentarioDTO.codigoCliente());
        comentario.setCodigoNegocio(crearComentarioDTO.codigoNegocio());
        comentario.setMensaje(crearComentarioDTO.mensaje());
        comentario.setFecha(LocalDateTime.now());

        Optional<Cliente> cliente = clienteRepo.findById(crearComentarioDTO.codigoCliente());
        Optional<Negocio> negocio = negocioRepo.findById(crearComentarioDTO.codigoNegocio());

        if(cliente.isEmpty()){throw new Exception("El codigo del cliente no existe");
        }else if(negocio.isEmpty()){throw new Exception("El codigo del negocio no existe");}
        if(negocio.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("La negocio asociado a este id se encuentra inactivo");
        }
        Optional<Cliente> propietario = clienteRepo.findById(negocio.get().getCodigoCliente());

        emailServicio.enviarCorreo(new EmailDTO("Tienen un nuevo comentario",
                "El usuario "+cliente.get().getNickname()+" ha comentado en su negocio "
                        +negocio.get().getNombre()+": "+crearComentarioDTO.mensaje(),
                propietario.get().getCorreoElectronico()));

        comentarioRepo.save(comentario);
    }

    @Override
    public void responderComentario(ResponderComentarioDTO responderComentarioDTO) throws Exception{
        Optional<Comentario> comentarioOptional = comentarioRepo.findById(responderComentarioDTO.codigoComentario());
        if(comentarioOptional.isEmpty()){throw new Exception("El id del comentario no existe");}

        Optional<Negocio> negocio = negocioRepo.findById(comentarioOptional.get().getCodigoNegocio());
        Optional<Cliente> cliente = clienteRepo.findById(comentarioOptional.get().getCodigoCliente());

        if(cliente.isEmpty()){throw new Exception("El codigo del cliente no existe");
        }else if(negocio.isEmpty()){throw new Exception("El codigo del negocio no existe");}
        if(cliente.get().getEstado().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("El cliente asociado a este id se encuentra inactivo");
        }
        Comentario comentario = comentarioOptional.get();
        comentario.setRespuesta(responderComentarioDTO.mensaje());

        emailServicio.enviarCorreo(new EmailDTO("Tienen una respuesta",
                "El negocio "+negocio.get().getNombre()+" ha respondido a su comentario: "
                        +comentario.getMensaje()+" lo siguiente: "+comentario.getRespuesta(),
                cliente.get().getCorreoElectronico()));

        comentarioRepo.save(comentario);
    }

    @Override
    public List<ComentarioDTO> listarComentarioNegocio(String codigoNegocio) throws Exception{
        Optional<Negocio> negocio = negocioRepo.findById(codigoNegocio);
        if(negocio.isEmpty()){
            throw new Exception("El negocio no existe");
        }
        if (negocio.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("El negocio se encuentra inactivo");
        }
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
        Optional<Negocio> negocio = negocioRepo.findById(codigoNegocio);
        List<Comentario> comentariosNegocio = comentarioRepo.findAllByCodigo(codigoNegocio);
        if(negocio.isEmpty()){
            throw new Exception("El negocio no existe");
        }
        if (negocio.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("El negocio se encuentra inactivo");
        }
        int suma = 0;
        for(Comentario comentario : comentariosNegocio){
            suma = comentario.getCalificacion()+suma;
        }

        return suma/comentariosNegocio.size()+1;
    }
}
