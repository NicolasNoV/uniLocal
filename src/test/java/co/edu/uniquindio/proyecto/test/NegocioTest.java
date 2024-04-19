package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documentos.Moderador;
import co.edu.uniquindio.proyecto.model.documentos.Negocio;
import co.edu.uniquindio.proyecto.model.entidades.Oferta;
import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.model.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.repositorios.AdminRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.implementaciones.NegocioServicioImpl;
import co.edu.uniquindio.proyecto.servicios.interfaces.NegocioServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NegocioTest {
    @Autowired
    private NegocioRepo negocioRepo;
    @Autowired
    private NegocioServicio negocioServicio;

    @Test
    void testCrearNegocio() throws Exception{
        LocalTime horaInicio = LocalTime.of(8,0,0);
        LocalTime horaFin = LocalTime.of(20,0,0);
        HorarioDTO horarioDTO = new HorarioDTO(horaFin,horaInicio,"Lunes");
        List<HorarioDTO> horarios = new ArrayList<>();
        horarios.add(horarioDTO);

        List<String> imagenes = new ArrayList<>();
        imagenes.add("https://imagen.com/");

        List<String> telefones = new ArrayList<>();
        telefones.add("1234");

        NegocioDTO negocioDTO = new NegocioDTO(
                0,
                0,
                "CAFETERIA5",
                "La quinta mejor cafeteria que te puedas encontrar :)",
                horarios,
                imagenes,
                "66218c9c9f2f010eb3d51536",
                TipoNegocio.CAFETERIA,
                telefones);

        boolean bandera = negocioServicio.crearNegocio(negocioDTO);

        assertTrue(bandera);
    }

    @Test
    public void testActualizarNegocio() throws Exception{
        LocalTime horaInicio = LocalTime.of(8,0,0);
        LocalTime horaFin = LocalTime.of(20,0,0);
        HorarioDTO horarioDTO = new HorarioDTO(horaFin,horaInicio,"Lunes");
        List<HorarioDTO> horarios = new ArrayList<>();
        horarios.add(horarioDTO);

        List<String> imagenes = new ArrayList<>();
        imagenes.add("https://imagen.com/");

        List<String> telefones = new ArrayList<>();
        telefones.add("1234");

        ActualizarNegocioDTO actualizarNegocioDTO= new ActualizarNegocioDTO(
                "66218e5cb3180d00bbe622a6",
                0,
                0,
                "Mejor Cafeteria",
                "La mejor cafeteria que te puedas encontrar :)",
                horarios,
                imagenes,
                "66214328ea0311058f71b83c",
                TipoNegocio.CAFETERIA,
                telefones);

        negocioServicio.actualizarNegocio(actualizarNegocioDTO);

        Optional<Negocio> negocio = negocioRepo.findById(actualizarNegocioDTO.codigoNegocio());

        assertEquals("Mejor Cafeteria",negocio.get().getNombre());
    }

    @Test
    void testEliminarNegocio() throws Exception{
        negocioServicio.eliminarNegocio("662192fa250edd2ac301d76a");
        Optional<Negocio> negocio = negocioRepo.findById("662192fa250edd2ac301d76a");
        assertEquals(EstadoRegistro.INACTIVO,negocio.get().getEstadoRegistro());
    }

    @Test
    public void testBuscarNeogocios() throws Exception{
        List<NegocioEncontradoDTO> negocioEncontrado = negocioServicio.buscarNeogocios(new BuscarNegocioDTO(
                "Mejor Cafeteria",
                TipoNegocio.CAFETERIA,
                "66218c9c9f2f010eb3d51536"
        ));

        assertNotNull(negocioEncontrado);
    }

    @Test
    public void testFiltrarPorEstado() throws Exception{
        List<NegocioEncontradoDTO> negocioEncontrado = negocioServicio.filtrarPorEstado(EstadoNegocio.PENDIENTE);
        assertNotNull(negocioEncontrado);
    }

    @Test
    public void testListarNegociosPropietario() throws Exception{
        List<NegocioEncontradoDTO> negocioEncontrado = negocioServicio.listarNegociosPropietario("66214328ea0311058f71b83c");
        assertNotNull(negocioEncontrado);
    }

    @Test
    public void testCambiarEstado() throws Exception{
        negocioServicio.cambiarEstado(new CambiarEstadoDTO(
                "66218e5cb3180d00bbe622a6",
                EstadoNegocio.APROBADO
        ));

        Optional<Negocio> negocio = negocioRepo.findById("66218e5cb3180d00bbe622a6");

        assertEquals(EstadoNegocio.APROBADO,negocio.get().getEstadoNegocio());
    }

    @Test
    public void testRegistrarRevision() throws Exception{
        negocioServicio.registrarRevision(new HistorialRevisionDTO(
                "El negocio no cumple con los estandares de esta plataforma",
                EstadoNegocio.RECHAZADO,
                "1",
                "662191c12884cf2ff0e50c89"
        ));

        Optional<Negocio> negocio = negocioRepo.findById("662191c12884cf2ff0e50c89");

        assertEquals(EstadoNegocio.RECHAZADO,negocio.get().getHistorialRevisiones().get(0).getEstadoNegocio());
    }

    @Test
    void testCrearOferta() throws Exception{
        negocioServicio.crearOferta(new CrearOfertaDTO(
                10,
                "66218e5cb3180d00bbe622a6",
                "Un descuento del 10% en shampoo",
                LocalDateTime.of(2024,04,17,2,0,0),
                LocalDateTime.of(2024,04,17,7,0,0)
        ));

        Optional<Negocio> negocio = negocioRepo.findById("66218e5cb3180d00bbe622a6");

        assertNotNull(negocio.get().getOfertas().get(0));
    }

}
