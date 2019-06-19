package gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.model;

import java.util.List;

/**
 *
 * @author Josué Barillas (jbarillas)
 */
public class FruitListWrapper {

    private List<FruitEntity> fruits;

    public FruitListWrapper() {
    }
    
    public FruitListWrapper(List<FruitEntity> fruits) {
        this.fruits = fruits;
    }

    public List<FruitEntity> getFruits() {
        return fruits;
    }

    public void setFruits(List<FruitEntity> fruits) {
        this.fruits = fruits;
    }    
    
}
