package co.edu.uniquindio.proyecto.servicios.implementaciones;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documentos.Cliente;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import co.edu.uniquindio.proyecto.servicios.interfaces.ClienteServicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public class ClienteServicioImp implements ClienteServicio {
    private ClienteRepo clienteRepo;
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
        cliente.setPassword(registroClienteDTO.password());
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
        //Asunto, Cuerpo Mensaje, Para
        //enviarCorreoElectronico("Cambio contraseña - Unilocal", "Para cambiar su contraseña acceda al link que está a continuación: link", correoElectronico);
    }

    @Override
    public void recuperarPassword(CambioPasswordDTO cambioPasswordDTO) throws Exception {

    }

    @Override
    public void iniciarSesion(InicioSesionDTO inicioSesionDTO) throws Exception {

    }

    @Override
    public DetalleClienteDTO obtenerCliente(String id) throws Exception {


        Optional<Cliente> optionalCliente = clienteRepo.findById(id);

        if(optionalCliente.isEmpty()){

            throw new Exception("el id no existe");



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

    private boolean existeEmail(String correo) {
        return clienteRepo.findByCorreoElectronico(correo) != null;
    }
    private boolean existeNickname(String nickname) {
        return clienteRepo.findByNickname(nickname) != null ;
    }
}
