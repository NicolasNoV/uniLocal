package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.documentos.Cliente;
import co.edu.uniquindio.proyecto.model.enums.EstadoRegistro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepo extends MongoRepository<Cliente, String> {
    Cliente findByCorreoElectronico(String correoElectronico);

    Cliente findByNickname(String nickname);

    List<Cliente> findByEstado(EstadoRegistro estadoRegistro);
}
