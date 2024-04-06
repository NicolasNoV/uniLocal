package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.servicios.interfaces.EmailServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailControlador {

    private final EmailServicio emailServicio;

    @PostMapping("/enviar-correo")
    public ResponseEntity<MensajeDTO<String>> enviarCorreo(@Valid @RequestBody EmailDTO emailDTO) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Coreo enviado correctamente"));
    }
}
