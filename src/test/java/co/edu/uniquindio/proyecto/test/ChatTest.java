package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.CrearChatDTO;
import co.edu.uniquindio.proyecto.dto.EnviarMensajeChatDTO;
import co.edu.uniquindio.proyecto.model.documentos.Chat;
import co.edu.uniquindio.proyecto.model.entidades.Mensaje;
import co.edu.uniquindio.proyecto.repositorios.ChatRepo;
import co.edu.uniquindio.proyecto.servicios.implementaciones.ChatServicioImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ChatTest {
    @Autowired
    private ChatRepo chatRepo;
    private ChatServicioImpl chatServicio;

    @Test
    public void testCrearChat() throws Exception{
        chatServicio.crearChat(new CrearChatDTO("002","001"));

        Optional<Chat> chatOptional = chatRepo.findById("001");

        assertNotNull(chatOptional.get());
    }

    @Test
    public void testEnviarMensajeChat() throws Exception{
        List<Mensaje> mensajes = chatServicio.enviarMensajeChat(new EnviarMensajeChatDTO(
                "001",
                "Hola",
                "002"
        ));

        assertNotNull(mensajes);
    }
}