package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documentos.Cliente;
import co.edu.uniquindio.proyecto.model.documentos.Moderador;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.repositorios.AdminRepo;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.servicios.implementaciones.ClienteServicioImpl;
import co.edu.uniquindio.proyecto.servicios.interfaces.ClienteServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteTest {
    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private ClienteServicio clienteServicio;




    @Test
    public void testRegistrarse() throws Exception{
        String codigoCliente = clienteServicio.registrarse(new RegistroClienteDTO(
                "Steven",
                "Steven Hernandez",
                "steven@gmail.com",
                "12345",
                "Armenia",
                "foto"
        ));
        assertNotNull(codigoCliente);
    }

    @Test
    public void testEditarPerfil()throws Exception{
        ActualizarClienteDTO actualizarClienteDTO = new ActualizarClienteDTO(
                "Steven Hernandez",
                "stevenn@gmail.com",
                "Armenia",
                "foto",
                "66218c9c9f2f010eb3d51536"
        );
        clienteServicio.editarPerfil(actualizarClienteDTO);
        Optional<Cliente> clienteOptional = clienteRepo.findById(actualizarClienteDTO.id());
        assertEquals("Pablito Hernandez", clienteOptional.get().getCorreoElectronico());
    }

    @Test
    public void testEliminarCuenta()throws Exception{
        clienteServicio.eliminarCuenta("66218c553658da7925d8c2b2");
        Optional<Cliente> clienteOptional = clienteRepo.findById("66218c553658da7925d8c2b2");
        assertEquals(EstadoRegistro.INACTIVO,clienteOptional.get().getEstado());
    }

    @Test
    public void testEnviarLinkRecuperacion()throws Exception{
       clienteServicio.enviarLinkRecuperacion("nicolas325nv@gmail.com");
        assertTrue(true);
    }

    @Test
    public void testObtenerCliente()throws Exception{
        DetalleClienteDTO detalleClienteDTO = clienteServicio.obtenerCliente("66214328ea0311058f71b83c");

        assertNotNull(detalleClienteDTO);
    }

    @Test
    public void testAgregarNegocioFavoritos() throws Exception{
        FavoritoDTO favoritoDTO = new FavoritoDTO("662192fa250edd2ac301d76a","66214328ea0311058f71b83c");
        clienteServicio.agregarNegocioFavoritos(favoritoDTO);
        Optional<Cliente> clienteOptional = clienteRepo.findById(favoritoDTO.codigoCliente());

        assertEquals(clienteOptional.get().getFavoritos().get(0).getCodigo(),favoritoDTO.codigoNegocio());
    }

    @Test
    public void testQuitarNegocioFavoritos() throws Exception{
        FavoritoDTO favoritoDTO = new FavoritoDTO("662192fa250edd2ac301d76a","66214328ea0311058f71b83c");
        clienteServicio.quitarNegocioFavoritos(favoritoDTO);
        Optional<Cliente> clienteOptional = clienteRepo.findById(favoritoDTO.codigoCliente());

        assertNull(clienteOptional.get().getFavoritos());
    }

    @Test
    public void testRecomendarNegocioBusqueda() throws Exception{
        List<NegocioEncontradoDTO> negocioRecomendados = clienteServicio.recomendarNegocioBusqueda("66218c9c9f2f010eb3d51536");

        assertNotNull(negocioRecomendados);
    }
@Autowired
private AdminRepo moderadorRepo;
    @Test
    public void testEditarAdmins(){
        Optional<Moderador> moderador = moderadorRepo.findById("5");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode( "12223");
        moderador.get().setPassword(passwordEncriptada);
        moderadorRepo.save(moderador.get());

    }

}