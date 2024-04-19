package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.CrearChatDTO;
import co.edu.uniquindio.proyecto.dto.EnviarMensajeChatDTO;
import co.edu.uniquindio.proyecto.model.documentos.Chat;
import co.edu.uniquindio.proyecto.model.entidades.Mensaje;
import co.edu.uniquindio.proyecto.repositorios.ChatRepo;
import co.edu.uniquindio.proyecto.servicios.implementaciones.ChatServicioImpl;
import co.edu.uniquindio.proyecto.servicios.interfaces.ChatServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ChatTest {
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private ChatServicio chatServicio;

    @Test
    public void testCrearChat() throws Exception{
        boolean bandera = chatServicio.crearChat(new CrearChatDTO("66218c15163faf5e50f4b517","66218c9c9f2f010eb3d51536"));

        assertTrue(bandera);
    }

    @Test
    public void testEnviarMensajeChat() throws Exception{
        List<Mensaje> mensajes = chatServicio.enviarMensajeChat(new EnviarMensajeChatDTO(
                "662197aac9297168a5e37998",
                "Hola",
                "66218c553658da7925d8c2b2"
        ));

        assertNotNull(mensajes);
    }
}