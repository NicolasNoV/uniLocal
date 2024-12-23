package co.edu.uniquindio.proyecto.servicios.implementaciones;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documentos.Cliente;
import co.edu.uniquindio.proyecto.model.documentos.Negocio;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.model.enums.TipoNegocio;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.interfaces.ClienteServicio;
import co.edu.uniquindio.proyecto.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClienteServicioImpl implements ClienteServicio {
    private final ClienteRepo clienteRepo;
    private final EmailServicioImpl emailServicio;
    private final NegocioRepo negocioRepo;
    private final NegocioServicioImpl negocioServicio;
    private final JWTUtils jwtUtils;

    @Override
    public ClienteDTO cargarPerfil(String codigoCliente) throws Exception{
        Optional<Cliente> clienteOptional = clienteRepo.findById(codigoCliente);
        if(clienteOptional.isEmpty()){
            throw new Exception(("no existe un cliente con el id" + codigoCliente));
        }
        Cliente cliente = clienteOptional.get();

        return new ClienteDTO(cliente.getNombre(),cliente.getCorreoElectronico(),cliente.getCiudad(),
                                cliente.getFotoPerfil());

    }

    @Override
    public String registrarse(RegistroClienteDTO registroClienteDTO) throws Exception {
        List<Negocio> negociosFavorritos = new ArrayList<>();
        List<String> historialBusquedaNombre = new ArrayList<>();
        List<TipoNegocio> historialBusquedaTipo = new ArrayList<>();
        if(existeEmail(registroClienteDTO.correoElectronico())){
            throw new Exception("El correo ya está en uso por otra persona");
        }
        if(existeNickname(registroClienteDTO.nickname())){
            throw new Exception("El nickname ya está en uso por otra persona");
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(registroClienteDTO.nombre());
        cliente.setCorreoElectronico(registroClienteDTO.correoElectronico());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode( registroClienteDTO.password());
        cliente.setPassword(passwordEncriptada);
        cliente.setFotoPerfil(registroClienteDTO.fotoPerfil());
        cliente.setNickname(registroClienteDTO.nickname());
        cliente.setCiudad(registroClienteDTO.ciudad());
        cliente.setEstado(EstadoRegistro.ACTIVO);
        cliente.setFavoritos(negociosFavorritos);
        cliente.setHistorialBusquedaNombre(historialBusquedaNombre);
        cliente.setHistorialBusquedaTipo(historialBusquedaTipo);
        Cliente clienteGuardado = clienteRepo.save(cliente);
        return clienteGuardado.getCodigo();
    }


    @Override
    public void editarPerfil(ActualizarClienteDTO actualizarClienteDTO) throws Exception {
        Optional<Cliente> clienteOptional = clienteRepo.findById(actualizarClienteDTO.id());
        if(clienteOptional.isEmpty()){
            throw new Exception(("no existe un cliente con el id" + actualizarClienteDTO.id()));
        }
        Cliente cliente = clienteOptional.get();
        cliente.setNombre(actualizarClienteDTO.nombre());
        cliente.setCiudad(actualizarClienteDTO.ciudad());
        cliente.setFotoPerfil(actualizarClienteDTO.fotoPerfil());
        cliente.setCorreoElectronico(actualizarClienteDTO.correoElectronico());

        clienteRepo.save(cliente);
    }

    @Override
    public void eliminarCuenta(String idCliente) throws Exception {
        Optional<Cliente> clienteOptional = clienteRepo.findById(idCliente);
        if(clienteOptional.isEmpty()){
            throw new Exception(("no existe un cliente con el id" + idCliente));
        }
        Cliente cliente = clienteOptional.get();
        cliente.setEstado(EstadoRegistro.INACTIVO);
        clienteRepo.save(cliente);
    }

    @Override
    public void enviarLinkRecuperacion(String correoElectronico) throws Exception {
        Cliente cliente = clienteRepo.findByCorreoElectronico(correoElectronico);

        String tokenEmail = jwtUtils.generarToken(correoElectronico, null);

        if(cliente == null){
            throw new Exception("Escriba un correo electronico valido");
        }
        if(cliente.getEstado().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("La cuenta asociada a este correo se encuentra inactiva");
        }

        emailServicio.enviarCorreo(new EmailDTO("Cambio contraseña - Unilocal",
                "Para cambiar su contraseña acceda al link que está a continuación: link"+tokenEmail,
                correoElectronico));
    }

    @Override
    public void recuperarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

        Optional<Cliente> optionalCliente = clienteRepo.findById(cambioPasswordDTO.id());

        if(optionalCliente.isEmpty()){
            throw new Exception("El id no existe");
        }

        jwtUtils.parseJwt(cambioPasswordDTO.token() );

        Cliente cliente = optionalCliente.get();
        cliente.setPassword( new BCryptPasswordEncoder().encode( cambioPasswordDTO.nuevaPassword() ) );
        clienteRepo.save(cliente);

    }

    @Override
    public DetalleClienteDTO obtenerCliente(String id) throws Exception {

        Optional<Cliente> optionalCliente = clienteRepo.findById(id);

        if(optionalCliente.isEmpty()){
            throw new Exception("el id no existe");
        }
        if(optionalCliente.get().getEstado().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("La cuenta asociada a este id se encuentra inactiva");
        }

        Cliente cliente =optionalCliente.get();

        return new DetalleClienteDTO(cliente.getCodigo(),
                cliente.getNombre(),
                cliente.getFotoPerfil(),
                cliente.getNickname(),
                cliente.getCorreoElectronico(),
                cliente.getCiudad());
    }

    @Override
    public List<ItemClienteDTO> listarClientes(int pagina) throws Exception {

        List<Cliente> clientes = clienteRepo.findByEstado(EstadoRegistro.ACTIVO);

        return clientes.stream().filter(c ->c.getEstado()==EstadoRegistro.ACTIVO ).map(c -> new ItemClienteDTO(c.getCodigo(),
                c.getNombre(),
                c.getFotoPerfil(),
                c.getNickname(),
                c.getCiudad())).toList();
    }

    @Override
    public void agregarNegocioFavoritos(FavoritoDTO favoritoDTO) throws Exception {
        Optional<Cliente> optionalCliente = clienteRepo.findById(favoritoDTO.codigoCliente());
        Optional<Negocio> optionalNegocio = negocioRepo.findById(favoritoDTO.codigoNegocio());
        if(optionalCliente.isEmpty()){throw new Exception("El id del cliente no existe");
        }else if(optionalNegocio.isEmpty()){ throw new Exception("El id del negocio no existe"); }
        if(optionalNegocio.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("La negocio asociado a este id se encuentra inactivo");
        }
        for (Negocio negocio:optionalCliente.get().getFavoritos()) {
            if(negocio.getCodigo().equals(favoritoDTO.codigoNegocio())){
                throw new Exception("El negocio ya está en favoritos");
            }

        }
        Cliente cliente = optionalCliente.get();
        Negocio negocio = optionalNegocio.get();

        cliente.getFavoritos().add(negocio);

        clienteRepo.save(cliente);
    }

    @Override
    public void quitarNegocioFavoritos(FavoritoDTO favoritoDTO) throws Exception {
        Optional<Cliente> optionalCliente = clienteRepo.findById(favoritoDTO.codigoCliente());
        Optional<Negocio> optionalNegocio = negocioRepo.findById(favoritoDTO.codigoNegocio());
        if(optionalCliente.isEmpty()){throw new Exception("El id del cliente no existe");
        }else if(optionalNegocio.isEmpty()){ throw new Exception("El id del negocio no existe"); }
        if(optionalNegocio.get().getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("La negocio asociado a este id se encuentra inactivo");
        }
        Cliente cliente = optionalCliente.get();
        Negocio negocio = optionalNegocio.get();

        cliente.getFavoritos().remove(negocio);

        clienteRepo.save(cliente);
    }

    @Override
    public List<NegocioEncontradoDTO> recomendarNegocioBusqueda(String codigoUsuario) throws Exception {
        Optional<Cliente> clienteOptional = clienteRepo.findById(codigoUsuario);
        Cliente cliente = clienteOptional.get();

        Map<String, Integer> frecuencia = new HashMap<>();

        // Calcular la frecuencia de cada elemento en la lista de búsqueda
        for (String item : cliente.getHistorialBusquedaNombre()) {
            frecuencia.put(item, frecuencia.getOrDefault(item, 0) + 1);
        }
        for (TipoNegocio tipoNegocio : cliente.getHistorialBusquedaTipo()){
            String tipoNegocioAux = tipoNegocio.toString();
            frecuencia.put(tipoNegocioAux, frecuencia.getOrDefault(tipoNegocioAux, 0) + 1);
        }

        // Ordenar los elementos por frecuencia (de mayor a menor)
        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(frecuencia.entrySet());
        listaOrdenada.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Obtener las recomendaciones basadas en los elementos más frecuentes
        List<NegocioEncontradoDTO> listaRecomendaciones = new ArrayList<>();
        int maxRecomendaciones = 5; // Número máximo de la prioridad de la busqueda
        for (int i = 0; i < Math.min(maxRecomendaciones, listaOrdenada.size()); i++) {
            List<NegocioEncontradoDTO> recomendacionesAux = negocioServicio.buscarNeogocios( new BuscarNegocioDTO( listaOrdenada.get(i).getKey(), null, codigoUsuario));
            System.out.println(recomendacionesAux.size());
            if(!(recomendacionesAux.size() < 5)){
                listaRecomendaciones.add(recomendacionesAux.get(i));
            }

        }
        return listaRecomendaciones;
    }

    private boolean existeEmail(String correo) {
        return clienteRepo.findByCorreoElectronico(correo) != null;
    }
    private boolean existeNickname(String nickname) {
        return clienteRepo.findByNickname(nickname) != null ;
    }
}
