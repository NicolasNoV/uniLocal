package co.edu.uniquindio.proyecto.controladores;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.servicios.interfaces.AutenticacionServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/autenticacion")
public class AutenticacionControlador {

    private final AutenticacionServicio autenticacionServicio;

    @PostMapping("/iniciar-sesion-cliente")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionCliente(LoginDTO loginDTO) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, autenticacionServicio.iniciarSesionCliente(loginDTO) ) );
    }

    @PostMapping("/iniciar-sesion-admin")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionAdmin(LoginDTO loginDTO) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, autenticacionServicio.iniciarSesionAdmin(loginDTO) ) );
    }

}
