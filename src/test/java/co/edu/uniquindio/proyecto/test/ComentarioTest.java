package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.dto.ResponderComentarioDTO;
import co.edu.uniquindio.proyecto.model.documentos.Comentario;
import co.edu.uniquindio.proyecto.model.entidades.Mensaje;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.servicios.implementaciones.ComentarioServicioImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ComentarioTest {
    @Autowired
    private ComentarioRepo comentarioRepo;
    private ComentarioServicioImpl comentarioServicio;

    @Test
    public void testCrearComentario() throws Exception{
        CrearComentarioDTO crearComentarioDTO = new CrearComentarioDTO(
                4,
                "002",
                "001",
                "Buen Servicio"
        );

        comentarioServicio.crearComentario(crearComentarioDTO);

        Optional<Comentario> comentarioOptional = comentarioRepo.findById("001");

        assertNotNull(comentarioOptional.get());
    }

    @Test
    public void testResponderComentario() throws Exception{
        comentarioServicio.responderComentario(new ResponderComentarioDTO("001","Hola"));

        Optional<Comentario> comentarioOptional = comentarioRepo.findById("001");

        assertNotNull(comentarioOptional.get().getRespuesta());
    }

    @Test
    public void testListarComentarioNegocio() throws Exception{
        List<ComentarioDTO> comentarios = comentarioServicio.listarComentarioNegocio("001");

        assertNotNull(comentarios);
    }

    @Test
    public void testCalcularPromedioCalificaciones(String codigoNegocio) throws Exception{
        int promedioEstrellas = comentarioServicio.calcularPromedioCalificaciones("001");

        assertEquals(4,promedioEstrellas);
    }



}
