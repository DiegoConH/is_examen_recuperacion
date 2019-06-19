package gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.controller;

import gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.model.FruitEntity;
import gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.model.FruitListWrapper;
import gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.service.FruitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory/fruit")
public class FruitController {

    @Autowired
    private FruitService fruitService;
    
    @PostMapping("/register")
    public FruitEntity register(@RequestBody(required = true) FruitEntity fruit) {
        return this.fruitService.registerFruit(fruit);
    }
    
    @GetMapping("/getFruitByName")
    public FruitEntity getFruitByName(@RequestParam(required = true) String fruit) {
        return this.fruitService.getFruitByName(fruit);
    }
    
    @GetMapping("/getFruitStock/{id}")
    public Long getFruitStock(@PathVariable(required = true) Integer id) {
        return this.fruitService.getFruitStock(id);
    }
    
    @GetMapping("/getReducedPrice/{fruit}")
    public Double getReducedPrice(@PathVariable(required = true) String fruit, @RequestParam(required = true) Double discount) {
        return this.fruitService.getReducedPrice(fruit, discount);
    }
    
    @GetMapping("/getSubtotal/{fruit}/{quantity}")
    public Double getSubtotal(@PathVariable(required = true) String fruit, @PathVariable(required = true) Integer quantity, @RequestParam(required = true) Double discount) {
        return this.fruitService.getSubtotal(fruit, discount, quantity);
    }
    
    @GetMapping("/getAllFruitOrderedByNameAsc")
    public String getAllFruitOrderedByNameAsc() {
        return this.fruitService.getAllFruitsOrderedByNameAsc();
    }
    
    @DeleteMapping("/reset")
    public void reset() {
        this.fruitService.reset();
    }
    
    @DeleteMapping("/remove")
    public void remove(@RequestParam(required = true) String name) {
        this.fruitService.remove(name);
    }
    
    @GetMapping("/getAllFruitNames")
    public String getAllFruitNames() {
        return this.fruitService.getAllFruitNames();
    }
    
    @GetMapping("/getAllFruits")
    public FruitListWrapper getAllFruits() {
        return this.fruitService.getAllFruits();
    }
}
