package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documentos.Cliente;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.servicios.implementaciones.ClienteServicioImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteTest {
    @Autowired
    private ClienteRepo clienteRepo;
    private ClienteServicioImpl clienteServicio;

    @Test
    public void testRegistrarse() throws Exception{
        String codigoCliente = clienteServicio.registrarse(new RegistroClienteDTO(
                "Pablito",
                "Pablo Hernandez",
                "pablo@gmail.com",
                "12345",
                "Armenia",
                "foto"
        ));
        assertNotNull(codigoCliente);
    }

    @Test
    public void testEditarPerfil()throws Exception{
        ActualizarClienteDTO actualizarClienteDTO = new ActualizarClienteDTO(
                "Pablo Hernandez",
                "pablo12@gmail.com",
                "Armenia",
                "foto",
                "001"
        );
        clienteServicio.editarPerfil(actualizarClienteDTO);
        Optional<Cliente> clienteOptional = clienteRepo.findById(actualizarClienteDTO.id());
        assertEquals("pablo12@gmail.com", clienteOptional.get().getCorreoElectronico());
    }

    @Test
    public void testEliminarCuenta()throws Exception{
        clienteServicio.eliminarCuenta("001");
        Optional<Cliente> clienteOptional = clienteRepo.findById("001");
        assertEquals(EstadoRegistro.INACTIVO,clienteOptional.get().getEstado());
    }

    @Test
    public void testEnviarLinkRecuperacion()throws Exception{
       clienteServicio.enviarLinkRecuperacion("pablo12@gmail.com");
        assertTrue(true);
    }

    @Test
    public void testObtenerCliente()throws Exception{
        DetalleClienteDTO detalleClienteDTO = clienteServicio.obtenerCliente("001");

        assertNotNull(detalleClienteDTO);
    }

    @Test
    public void testAgregarNegocioFavoritos() throws Exception{
        FavoritoDTO favoritoDTO = new FavoritoDTO("001","001");
        clienteServicio.agregarNegocioFavoritos(favoritoDTO);
        Optional<Cliente> clienteOptional = clienteRepo.findById(favoritoDTO.codigoCliente());

        assertEquals(clienteOptional.get().getFavoritos().get(0).getCodigo(),favoritoDTO.codigoNegocio());
    }

    @Test
    public void testQuitarNegocioFavoritos() throws Exception{
        FavoritoDTO favoritoDTO = new FavoritoDTO("001","001");
        clienteServicio.agregarNegocioFavoritos(favoritoDTO);
        Optional<Cliente> clienteOptional = clienteRepo.findById(favoritoDTO.codigoCliente());

        assertNull(clienteOptional.get().getFavoritos());
    }

    @Test
    public void testRecomendarNegocioBusqueda() throws Exception{
        List<NegocioEncontradoDTO> negocioRecomendados = clienteServicio.recomendarNegocioBusqueda("001");

        assertNotNull(negocioRecomendados);
    }
}