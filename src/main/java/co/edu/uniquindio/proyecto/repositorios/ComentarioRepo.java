package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.documentos.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComentarioRepo extends MongoRepository<Comentario, String> {
}
