package gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.dao;

import gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.model.FruitEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Josue
 */
@Repository
public interface FruitRepository extends CrudRepository<FruitEntity, Integer> {
    
    public FruitEntity findByName(String name);
    
}
