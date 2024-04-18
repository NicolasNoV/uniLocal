package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.documentos.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ComentarioRepo extends MongoRepository<Comentario, String> {

    List<Comentario> findAllByCodigo(String codigo);

}
