package co.edu.uniquindio.proyecto.servicios.implementaciones;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documentos.Cliente;
import co.edu.uniquindio.proyecto.model.documentos.Negocio;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.repositorios.NegocioRepo;
import co.edu.uniquindio.proyecto.servicios.interfaces.ClienteServicio;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClienteServicioImpl implements ClienteServicio {
    private ClienteRepo clienteRepo;
    private EmailServicioImpl emailServicio;
    private NegocioRepo negocioRepo;
    private NegocioServicioImpl negocioServicio;

    @Override
    public String registrarse(RegistroClienteDTO registroClienteDTO) throws Exception {
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
        cliente.setEstado (EstadoRegistro.INACTIVO);
        clienteRepo.save(cliente);
    }

    @Override
    public void enviarLinkRecuperacion(String correoElectronico) throws Exception {
        Cliente cliente = clienteRepo.findByCorreoElectronico(correoElectronico);

        if(cliente == null){
            throw new Exception("Escriba un correo electronico valido");
        }
        if(cliente.getEstado().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("La cuenta asociada a este correo se encuentra inactiva");
        }

        emailServicio.enviarCorreo(new EmailDTO("Cambio contraseña - Unilocal",
                "Para cambiar su contraseña acceda al link que está a continuación: link",
                correoElectronico));
    }

    @Override
    public void recuperarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

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
                cliente.getEmail(),
                cliente.getCiudad());
    }

    @Override
    public List<ItemClienteDTO> listarClientes(int pagina) throws Exception {

        List<Cliente> clientes = clienteRepo.finByEstado(EstadoRegistro.ACTIVO);

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
        for (String item : cliente.getHistorialBusqueda()) {
            frecuencia.put(item, frecuencia.getOrDefault(item, 0) + 1);
        }

        // Ordenar los elementos por frecuencia (de mayor a menor)
        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(frecuencia.entrySet());
        listaOrdenada.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Obtener las recomendaciones basadas en los elementos más frecuentes
        List<NegocioEncontradoDTO> listaRecomendaciones = new ArrayList<>();
        int maxRecomendaciones = 5; // Número máximo de la prioridad de la busqueda
        for (int i = 0; i < Math.min(maxRecomendaciones, listaOrdenada.size()); i++) {
            List<NegocioEncontradoDTO> recomendacionesAux = negocioServicio.buscarNeogocios(listaOrdenada.get(i).getKey());
            for(int j = 0; j < 3; j++){
                listaRecomendaciones.add(recomendacionesAux.get(j));
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
