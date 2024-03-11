package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.documentos.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepo extends MongoRepository<Cliente, String> {
    Cliente findByCorreoElectronico(String correoElectronico);

    Cliente findByNickname(String nickname);
}
