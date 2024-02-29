package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.Moderador;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepo extends MongoRepository<Moderador, String> {
}
