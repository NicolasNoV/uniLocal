package co.edu.uniquindio.proyecto.servicios.interfaces;

import co.edu.uniquindio.proyecto.dto.CrearChatDTO;
import co.edu.uniquindio.proyecto.dto.EnviarMensajeChatDTO;
import co.edu.uniquindio.proyecto.model.entidades.Mensaje;

import java.util.List;

public interface ChatServicio {

    boolean crearChat(CrearChatDTO crearChatDTO) throws Exception;

    List<Mensaje> enviarMensajeChat(EnviarMensajeChatDTO enviarMensajeChatDTO) throws Exception;


}
