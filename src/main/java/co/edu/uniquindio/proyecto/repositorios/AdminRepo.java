package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.documentos.Cliente;
import co.edu.uniquindio.proyecto.model.documentos.Moderador;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepo extends MongoRepository<Moderador, String> {

    Moderador findByCorreoElectronico(String correoElectronico);
}
