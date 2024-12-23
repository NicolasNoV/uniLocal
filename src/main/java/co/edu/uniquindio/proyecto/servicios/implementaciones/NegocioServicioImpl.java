package co.edu.uniquindio.proyecto.servicios.implementaciones;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documentos.Cliente;
import co.edu.uniquindio.proyecto.model.documentos.Negocio;
import co.edu.uniquindio.proyecto.model.entidades.HistorialRevision;
import co.edu.uniquindio.proyecto.model.entidades.Horario;
import co.edu.uniquindio.proyecto.model.entidades.Oferta;
import co.edu.uniquindio.proyecto.model.entidades.Ubicacion;
import co.edu.uniquindio.proyecto.model.enums.EstadoNegocio;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.model.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.interfaces.NegocioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NegocioServicioImpl implements NegocioServicio {

    private final NegocioRepo negocioRepo;
    private final ClienteRepo clienteRepo;

    @Override
    public boolean crearNegocio(NegocioDTO negocioDTO) throws Exception{
        Ubicacion ubicacion = new Ubicacion(negocioDTO.longitud(), negocioDTO.latitud());
        List<Oferta> ofertas = new ArrayList<>();

        List<Horario> horarios = new ArrayList<>();
        for(int i=0; i<negocioDTO.horarios().size();i++){
            Horario horario = new Horario();
            horario.setHoraInicio(negocioDTO.horarios().get(i).horaInicio());
            horario.setHoraFin(negocioDTO.horarios().get(i).horaFin());
            horario.setDia(negocioDTO.horarios().get(i).dia());
            horarios.add(horario);
        }
        List<HistorialRevision> historial = new ArrayList<>();

        Negocio negocio = new Negocio();
        negocio.setUbicacion(ubicacion);
        negocio.setNombre(negocioDTO.nombre());
        negocio.setDescripcion(negocioDTO.descripcion());
        negocio.setHorarios(horarios);
        negocio.setEstadoRegistro(EstadoRegistro.ACTIVO);
        negocio.setImagenes(negocioDTO.imagenes());
        negocio.setCodigoCliente(negocioDTO.codigoCliente());
        negocio.setTipoNegocio(negocioDTO.tipoNegocio());
        negocio.setTelefonos(negocioDTO.telefonos());
        negocio.setHistorialRevisiones(historial);
        negocio.setEstadoNegocio(EstadoNegocio.PENDIENTE);
        negocio.setOfertas(ofertas);

        Negocio negocioCreado = negocioRepo.save(negocio);
        return true;
    }

    @Override
    public void actualizarNegocio(ActualizarNegocioDTO actualizarNegocioDTO) throws Exception{
        Optional<Negocio> negocioOptional = negocioRepo.findById(actualizarNegocioDTO.codigoNegocio());
        if(negocioOptional.isEmpty()){throw new Exception("El id del negocio no existe");}
        if(negocioOptional.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){throw new Exception("El negocio se encuentra inactivo");}

        Negocio negocio = negocioOptional.get();

        List<Horario> horarios = new ArrayList<>();
        for(int i=0; i<actualizarNegocioDTO.horarios().size();i++){
            Horario horario = new Horario();
            horario.setHoraInicio(actualizarNegocioDTO.horarios().get(i).horaInicio());
            horario.setHoraFin(actualizarNegocioDTO.horarios().get(i).horaFin());
            horario.setDia(actualizarNegocioDTO.horarios().get(i).dia());
            horarios.add(horario);
        }

        Ubicacion ubicacion = new Ubicacion(actualizarNegocioDTO.longitud(), actualizarNegocioDTO.latitud());

        negocio.setUbicacion(ubicacion);
        negocio.setNombre(actualizarNegocioDTO.nombre());
        negocio.setDescripcion(actualizarNegocioDTO.descripcion());
        negocio.setHorarios(horarios);
        negocio.setImagenes(actualizarNegocioDTO.imagenes());
        negocio.setCodigoCliente(actualizarNegocioDTO.codigoCliente());
        negocio.setTipoNegocio(actualizarNegocioDTO.tipoNegocio());
        negocio.setTelefonos(actualizarNegocioDTO.telefonos());


        negocioRepo.save(negocio);

    }

    @Override
    public void eliminarNegocio(String codigoNegocio) throws Exception{
        Optional<Negocio> negocioOptional = negocioRepo.findById(codigoNegocio);
        if(negocioOptional.isEmpty()){throw new Exception("El id del negocio no existe");}
        if(negocioOptional.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){throw new Exception("El negocio se encuentra inactivo");}

        Negocio negocio = negocioOptional.get();

        negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
        negocioRepo.save(negocio);
    }

    @Override
    public List<NegocioEncontradoDTO> buscarNeogocios(BuscarNegocioDTO buscarNegocioDTO) throws Exception{
        List<Negocio> negocioNombre = negocioRepo.findAllByNombreContainingIgnoreCase(buscarNegocioDTO.busqueda());
        if(negocioNombre.isEmpty()){throw new Exception("No se han encontrado negocios");}

        List<NegocioEncontradoDTO> listaBusqueda = new ArrayList<>();
        List<NegocioEncontradoDTO> listaBusqueda1 = crearListaBusqueda(negocioNombre,"Nombre");

        if(buscarNegocioDTO.tipoNegocio() != null){
            List<Negocio> negocioTipo = negocioRepo.findAllByTipoNegocio(buscarNegocioDTO.tipoNegocio());
            List<NegocioEncontradoDTO> listaBusqueda2 = crearListaBusqueda(negocioTipo,"TipoNegocio");

            for (NegocioEncontradoDTO negocio : listaBusqueda1) {
                if (listaBusqueda2.contains(negocio)) {
                    listaBusqueda.add(negocio);
                }
            }
        }else{listaBusqueda = listaBusqueda1;}

        return listaBusqueda;
    }

    public List<NegocioEncontradoDTO> crearListaBusqueda(List<Negocio> negocios, String tipoBusqueda) throws Exception{
        List<NegocioEncontradoDTO> listaBusqueda = new ArrayList<>();
        for(Negocio negocio : negocios){
            if(negocio != null && negocio.getEstadoRegistro().equals(EstadoRegistro.ACTIVO)){
                List<HorarioDTO> horarios = new ArrayList<>();
                for(Horario horario : negocio.getHorarios()){
                    horarios.add(new HorarioDTO(horario.getHoraFin(),
                            horario.getHoraInicio(),
                            horario.getDia()));
                }
                listaBusqueda.add(new NegocioEncontradoDTO(negocio.getUbicacion().getLongitud(),
                        negocio.getUbicacion().getLatitud(),
                        negocio.getNombre(),
                        negocio.getDescripcion(),
                        horarios,
                        negocio.getImagenes(),
                        negocio.getCodigoCliente(),
                        negocio.getTipoNegocio(),
                        negocio.getTelefonos(),
                        tipoBusqueda));
            }
            eliminarNegocioXTiempo();
            }

        return listaBusqueda;
    }

    @Override
    public List<NegocioEncontradoDTO> filtrarPorEstado(EstadoNegocio estadoNegocio) throws Exception {
        List<Negocio> negocios = negocioRepo.findAll();

        List<Negocio> negociosAux = new ArrayList<>();
        if(negocios.isEmpty()){ throw new Exception("No se han encontrado negocios");}
        for(Negocio negocio : negocios){
            if(negocio.getEstadoNegocio().equals(estadoNegocio)){
                negociosAux.add(negocio);
            }
        }
        eliminarNegocioXTiempo();
        return crearListaBusqueda(negociosAux,"Filtrado");
    }

    @Override
    public List<NegocioEncontradoDTO> listarNegociosPropietario(String codigoCliente) throws Exception{
        List<Negocio> negocios = negocioRepo.findAllByCodigoCliente(codigoCliente);
        if(negocios.isEmpty()){throw new Exception("No se han encontrado negocios");}
        eliminarNegocioXTiempo();
        return crearListaBusqueda(negocios,"ListarPropietario");
    }

    @Override
    public void cambiarEstado(CambiarEstadoDTO cambiarEstadoDTO) throws Exception{
        Optional<Negocio> negocioOptional = negocioRepo.findById(cambiarEstadoDTO.codigoNegocio());
        if(negocioOptional.isEmpty()){throw new Exception("El id del negocio no existe");}
        if(negocioOptional.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){throw new Exception("El negocio se encuentra inactivo");}

        Negocio negocio = negocioOptional.get();
        negocio.setEstadoNegocio(cambiarEstadoDTO.estadoNegocio());
        negocioRepo.save(negocio);
    }

    @Override
    public void registrarRevision(HistorialRevisionDTO historialRevisionDTO) throws Exception{
        Optional<Negocio> negocioOptional = negocioRepo.findById(historialRevisionDTO.codigoNegocio());
        if(negocioOptional.isEmpty()){throw new Exception("El id del negocio no existe");}
        if(negocioOptional.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){throw new Exception("El negocio se encuentra inactivo");}

        Negocio negocio = negocioOptional.get();

        HistorialRevision historialRevision = new HistorialRevision();
        historialRevision.setDescripcion(historialRevisionDTO.descripcion());
        historialRevision.setEstadoNegocio(historialRevisionDTO.estadoNegocio());
        historialRevision.setFecha(LocalDateTime.now());
        historialRevision.setCodigoModerador(historialRevisionDTO.codigoModerador());

        negocio.setEstadoNegocio(historialRevisionDTO.estadoNegocio());
        negocio.getHistorialRevisiones().add(historialRevision);
        negocioRepo.save(negocio);

    }

    public void eliminarNegocioXTiempo() throws Exception{
        List<Negocio> negocios = negocioRepo.findAll();
        if(negocios.isEmpty()){
            throw new Exception("No hay negocios");
        }

        LocalDateTime fechaHoraActual = LocalDateTime.now();
        for (Negocio negocio: negocios) {
            int ultimaRevision = negocio.getHistorialRevisiones().size()-1;
            if(!(ultimaRevision < 0)){
                LocalDateTime dateTime = negocio.getHistorialRevisiones().get(ultimaRevision).getFecha();

                // Calcular la diferencia de días entre la fecha y hora dada y la fecha y hora actual
                long diasDiferencia = ChronoUnit.DAYS.between(dateTime, fechaHoraActual);

                if(diasDiferencia >= 5){
                    negocio.setEstadoRegistro(EstadoRegistro.INACTIVO);
                    negocioRepo.save(negocio);
            }
            }
        }
    }

    @Override
    public void crearOferta(CrearOfertaDTO crearOfertaDTO) throws Exception {
        Optional<Negocio> negocioOptional = negocioRepo.findById(crearOfertaDTO.codigoNegocio());
        if(negocioOptional.isEmpty()){throw new Exception("El id del negocio no existe");}
        if(negocioOptional.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("El negocio no existe");
        }

        Negocio negocio = negocioOptional.get();
        Oferta oferta = new Oferta();

        oferta.setCodigoNegocio(negocio.getCodigo());
        oferta.setDescripcion(crearOfertaDTO.descripcion());
        oferta.setDescuento(crearOfertaDTO.descuento());
        oferta.setInicioOferta(crearOfertaDTO.inicioOferta());
        oferta.setFinOferta(crearOfertaDTO.finOferta());

        negocio.getOfertas().add(oferta);

        negocioRepo.save(negocio);

    }

    public TipoNegocio[] listarTipoNegocios() throws Exception {
        return TipoNegocio.values();
    }

}