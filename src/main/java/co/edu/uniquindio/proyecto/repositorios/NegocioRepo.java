package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.model.documentos.Negocio;
import co.edu.uniquindio.proyecto.model.enums.TipoNegocio;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NegocioRepo extends MongoRepository<Negocio, String> {

    List<Negocio> findAllByNombre(String nombre);
    List<Negocio> findAllByTipoNegocio(TipoNegocio tipoNegocio);
    List<Negocio> findAllByCodigoCliente(String codigoCliente);

}
