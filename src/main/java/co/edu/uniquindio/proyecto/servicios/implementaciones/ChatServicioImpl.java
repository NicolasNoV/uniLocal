package co.edu.uniquindio.proyecto.servicios.implementaciones;

import co.edu.uniquindio.proyecto.dto.CrearChatDTO;
import co.edu.uniquindio.proyecto.dto.EnviarMensajeChatDTO;
import co.edu.uniquindio.proyecto.model.documentos.Chat;
import co.edu.uniquindio.proyecto.model.entidades.Mensaje;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.repositorios.ChatRepo;
import co.edu.uniquindio.proyecto.servicios.interfaces.ChatServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServicioImpl implements ChatServicio {

    private final ChatRepo chatRepo;

    @Override
    public void crearChat(CrearChatDTO crearChatDTO) {
        Chat chat = new Chat();
        List<Mensaje> mensajes = new ArrayList<>();

        chat.setMensajes(mensajes);
        chat.setCodigoCliente(crearChatDTO.codigoCliente());
        chat.setCodigoPropietario(crearChatDTO.codigoPropietario());
        chat.setEstadoRegistro(EstadoRegistro.ACTIVO);

        chatRepo.save(chat);
    }

    @Override
    public List<Mensaje> enviarMensajeChat(EnviarMensajeChatDTO enviarMensajeChatDTO) throws Exception {
        return run(enviarMensajeChatDTO);
    }

    public List<Mensaje> run(EnviarMensajeChatDTO enviarMensajeChatDTO) throws Exception {
        List<Mensaje> mensajesNuevo = new ArrayList<>();

        Optional<Chat> chatOptional = chatRepo.findById(enviarMensajeChatDTO.codigoChat());
        if(chatOptional.isEmpty()){throw new Exception("El chat no existe");}
        if (chatOptional.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("El chat se encuentra eliminado");
        }

        Mensaje mensaje = new Mensaje(enviarMensajeChatDTO.codigoRemitente(), enviarMensajeChatDTO.mensaje());
        chatOptional.get().getMensajes().add(mensaje);

        chatRepo.save(chatOptional.get());

        // Hilo para recibir y mostrar nuevos mensajes
        Thread hiloRecepcion = new Thread(() -> {
            while (true) {
                mensajesNuevo.addAll(chatOptional.get().getMensajes());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        hiloRecepcion.start();
        return mensajesNuevo;
    }
}