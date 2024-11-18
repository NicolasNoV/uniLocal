package co.edu.uniquindio.proyecto.servicios.interfaces;import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;

public interface AutenticacionServicio {
    public String iniciarSesionCliente(LoginDTO loginDTO) throws Exception;
    public TokenDTO iniciarSesionAdmin(LoginDTO loginDTO) throws Exception;
}
