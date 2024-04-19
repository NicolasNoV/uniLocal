package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.ComentarioDTO;
import co.edu.uniquindio.proyecto.dto.CrearComentarioDTO;
import co.edu.uniquindio.proyecto.dto.ResponderComentarioDTO;
import co.edu.uniquindio.proyecto.model.documentos.Comentario;
import co.edu.uniquindio.proyecto.model.entidades.Mensaje;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.servicios.implementaciones.ComentarioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.interfaces.ComentarioServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ComentarioTest {
    @Autowired
    private ComentarioRepo comentarioRepo;
    @Autowired
    private ComentarioServicio comentarioServicio;

    @Test
    public void testCrearComentario() throws Exception{
        CrearComentarioDTO crearComentarioDTO = new CrearComentarioDTO(
                4,
                "66218c7cb76e2763c497075a",
                "662192a0118f3a678da41036",
                "Buen Servicio"
        );


        boolean bandera = comentarioServicio.crearComentario(crearComentarioDTO);
        assertTrue(bandera);
    }

    @Test
    public void testResponderComentario() throws Exception{
       boolean bandera = comentarioServicio.responderComentario(new ResponderComentarioDTO("6621943f42f923635bbc0e21","Hola"));

        assertTrue(bandera);
    }

    @Test
    public void testListarComentarioNegocio() throws Exception{
        List<ComentarioDTO> comentarios = comentarioServicio.listarComentarioNegocio("66218e5cb3180d00bbe622a6");

        assertNotNull(comentarios);
    }

    @Test
    public void testCalcularPromedioCalificaciones() throws Exception{
        int promedioEstrellas = comentarioServicio.calcularPromedioCalificaciones("66218e5cb3180d00bbe622a6");

        assertEquals(4,promedioEstrellas);
    }



}
