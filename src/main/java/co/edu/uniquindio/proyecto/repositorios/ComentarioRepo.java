package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.documentos.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ComentarioRepo extends MongoRepository<Comentario, String> {

    List<Comentario> findAllByCodigo(String codigo);

}
