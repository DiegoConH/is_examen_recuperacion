package gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.service;

import gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.dao.FruitRepository;
import gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.model.FruitEntity;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FruitService {

    @Autowired
    private FruitRepository fruitRepo;
        
    public FruitEntity registerFruit(FruitEntity newFruit) {
        return this.fruitRepo.save(newFruit);
    }
    
    public FruitEntity getFruitByName(String name) {
        return this.fruitRepo.findByName(name);
    }
        
    public Long getFruitStock(Integer fruidId) {
        return this.fruitRepo.findById(fruidId).get().getStock();
    }
    
    public Double getReducedPrice(String fruitName, Double discount) {
       return this.fruitRepo.findByName(fruitName).getUnitPrice() - discount;
    }
    
    public Double getSubtotal(String fruitName, Double discount, Integer quantity) {
        return this.fruitRepo.findByName(fruitName).getUnitPrice() * quantity - discount;
    }
    
    public String getAllFruitsOrderedByNameAsc() {
        List<FruitEntity> allFruits = (List<FruitEntity>) this.fruitRepo.findAll();
        String[] fruitNames = new String[allFruits.size()];
        int f = 0;
        
        for (FruitEntity fruit : allFruits) {
            fruitNames[f++] = fruit.getName();
        }
        
        for (int i = 0; i < fruitNames.length; i++) {
            for (int j = i + 1; j < fruitNames.length; j++) {
                if (fruitNames[i].compareToIgnoreCase(fruitNames[j]) > 0) {
                    String aux = fruitNames[i];
                    fruitNames[i] = fruitNames[j];
                    fruitNames[j] = aux;
                }
            }
        }
        
        return Arrays.toString(fruitNames);
    }
}
