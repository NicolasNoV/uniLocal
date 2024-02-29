package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.Negocio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NegocioRepo extends MongoRepository<Negocio, String> {
}
