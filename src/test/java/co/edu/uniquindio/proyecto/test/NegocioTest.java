package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documentos.Negocio;
import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.model.enums.TipoNegocio;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                "Mejor Cafeteria Mundo",
                "La mejor cafeteria que te puedas encontrar :)",
                horarios,
                imagenes,
                "001",
                TipoNegocio.CAFETERIA,
                telefones);

        negocioServicio.crearNegocio(negocioDTO);

        Optional<Negocio> negocio = negocioRepo.findById("001");

        assertNotNull(negocio);
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
                "001",
                0,
                0,
                "Mejor Cafeteria",
                "La mejor cafeteria que te puedas encontrar :)",
                horarios,
                imagenes,
                "001",
                TipoNegocio.CAFETERIA,
                telefones);

        negocioServicio.actualizarNegocio(actualizarNegocioDTO);

        Optional<Negocio> negocio = negocioRepo.findById(actualizarNegocioDTO.codigoNegocio());

        assertEquals("Mejor Cafeteria",negocio.get().getNombre());
    }

    @Test
    void testEliminarNegocio() throws Exception{
        negocioServicio.eliminarNegocio("001");
        Optional<Negocio> negocio = negocioRepo.findById("001");
        assertEquals(EstadoRegistro.INACTIVO,negocio.get().getEstadoRegistro());
    }

    @Test
    public void testBuscarNeogocios() throws Exception{
        List<NegocioEncontradoDTO> negocioEncontrado = negocioServicio.buscarNeogocios(new BuscarNegocioDTO(
                "Mejor Cafeteria",
                TipoNegocio.CAFETERIA,
                "001"
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
        List<NegocioEncontradoDTO> negocioEncontrado = negocioServicio.listarNegociosPropietario("001");
        assertNotNull(negocioEncontrado);
    }

    @Test
    public void testCambiarEstado() throws Exception{
        negocioServicio.cambiarEstado(new CambiarEstadoDTO(
                "002",
                EstadoNegocio.APROBADO
        ));

        Optional<Negocio> negocio = negocioRepo.findById("002");

        assertEquals(EstadoNegocio.APROBADO,negocio.get().getEstadoNegocio());
    }

    @Test
    public void testRegistrarRevision(HistorialRevisionDTO historialRevisionDTO) throws Exception{
        negocioServicio.registrarRevision(new HistorialRevisionDTO(
                "El negocio no cumple con los estandares de esta plataforma",
                EstadoNegocio.RECHAZADO,
                "001",
                "003"
        ));

        Optional<Negocio> negocio = negocioRepo.findById("003");

        assertEquals(EstadoNegocio.RECHAZADO,negocio.get().getHistorialRevisiones().get(0).getEstadoNegocio());
    }

    @Test
    void testCrearOferta(CrearOfertaDTO crearOfertaDTO) throws Exception{
        negocioServicio.crearOferta(new CrearOfertaDTO(
                10,
                "001",
                "Un descuento del 10% en shampoo",
                LocalDateTime.of(2024,04,17,2,0,0),
                LocalDateTime.of(2024,04,17,7,0,0)
        ));

        Optional<Negocio> negocio = negocioRepo.findById("001");

        assertNotNull(negocio.get().getOfertas().get(0));
    }



}
