package gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario;

import gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.model.FruitEntity;
import gt.edu.umg.ingenieria.sistemas.is.examen.recuperacion.inventario.model.FruitListWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InventarioApplicationTests {
    
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    @Test
    public void whenMultipleInsert_thenMultipleListedItem() {
        System.out.println("Test #1 - When multiple insert then multiple listed items.");
        
        // given
        FruitEntity banano = new FruitEntity("Banano", 2.50f, 100l);
        FruitEntity papaya = new FruitEntity("Papaya", 15.00f, 15l);
        FruitEntity melon = new FruitEntity("Melon", 14.00f, 10l);
        FruitEntity sandia = new FruitEntity("Sandia", 35.50f, 17l);
        String expectedResult = "[Banano, Papaya, Melon, Sandia]";
                
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano = this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);
        FruitEntity insertPapaya = this.testRestTemplate.postForObject("/inventory/fruit/register", papaya, FruitEntity.class);
        FruitEntity insertMelon = this.testRestTemplate.postForObject("/inventory/fruit/register", melon, FruitEntity.class);
        FruitEntity insertSandia = this.testRestTemplate.postForObject("/inventory/fruit/register", sandia, FruitEntity.class);
        String actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getAllFruitNames", String.class);
        
        // then
        Assert.assertEquals("Test #1 failed!!!", expectedResult, actualResult);
    }
    
    @Test
    public void whenSingleInsert_thenSingleListedItem() {
        System.out.println("Test #2 - When single insert then single listed item.");
        
        // given
        FruitEntity banano = new FruitEntity("Banano", 10.50f, 100l);
        String expectedResult = "[Banano]";
                
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano = this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);
        String actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getAllFruitOrderedByNameAsc", String.class);
        
        // then
        Assert.assertEquals("Test #2 failed!!!", expectedResult, actualResult);
    }
    
    @Test
    public void whenMultipleInsert_thenMultipleListedItemOrdered() {
        System.out.println("Test #3 - When multiple insert then multiple listed items ordered by name asc.");
        
        // given
        FruitEntity papaya = new FruitEntity("Papaya", 10.50f, 100l);
        FruitEntity melon = new FruitEntity("Melon", 25.00f, 150l);
        String expectedResult = "[Melon, Papaya]";
                
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertPapaya = this.testRestTemplate.postForObject("/inventory/fruit/register", papaya, FruitEntity.class);
        FruitEntity insertMelon = this.testRestTemplate.postForObject("/inventory/fruit/register", melon, FruitEntity.class);
        String actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getAllFruitOrderedByNameAsc", String.class);
        
        // then
        Assert.assertEquals("Test #3 failed!!!", expectedResult, actualResult);
    }
    
    @Test
    public void whenDeleteSpecificFruit_thenListMustNotContainDeletedFruit() {
        System.out.println("Test #4 - When delete specific fruit, then fruits list must not contain deleted fruit.");
        
        // given
        FruitEntity banano = new FruitEntity("Banano", 2.50f, 100l);
        FruitEntity papaya = new FruitEntity("Papaya", 15.00f, 15l);
        FruitEntity melon = new FruitEntity("Melon", 14.00f, 10l);
        FruitEntity sandia = new FruitEntity("Sandia", 35.50f, 17l);
        String expectedResult = "[Banano, Papaya, Sandia]";
                
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano = this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);
        FruitEntity insertPapaya = this.testRestTemplate.postForObject("/inventory/fruit/register", papaya, FruitEntity.class);
        FruitEntity insertMelon = this.testRestTemplate.postForObject("/inventory/fruit/register", melon, FruitEntity.class);
        FruitEntity insertSandia = this.testRestTemplate.postForObject("/inventory/fruit/register", sandia, FruitEntity.class);        
        this.testRestTemplate.delete("/inventory/fruit/remove?name=Melon");
        String actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getAllFruitNames", String.class);
                
        // then
        Assert.assertEquals("Test #4 failed!!!", expectedResult, actualResult);
    }

    @Test
    public void whenDeleteAllFruit_thenlistallFruits(){
        System.out.println("Test #5 - When delete all fruits, then list all fruits must not contain any fruit.");

        // given
        FruitEntity banano = new FruitEntity("Banano", 2.50f, 100l);
        FruitEntity papaya = new FruitEntity("Papaya", 15.00f, 15l);
        FruitEntity melon = new FruitEntity("Melon", 14.00f, 10l);
        FruitEntity sandia = new FruitEntity("Sandia", 35.50f, 17l);
        String expectedResult = "[]";
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano = this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);
        FruitEntity insertPapaya= this.testRestTemplate.postForObject("/inventory/fruit/register", papaya, FruitEntity.class);
        FruitEntity insertMelon= this.testRestTemplate.postForObject("/inventory/fruit/register", melon, FruitEntity.class);
        FruitEntity insertSandia= this.testRestTemplate.postForObject("/inventory/fruit/register", sandia, FruitEntity.class);
        this.testRestTemplate.delete("/inventory/fruit/remove?name=Banano");
        this.testRestTemplate.delete("/inventory/fruit/remove?name=Melon");
        this.testRestTemplate.delete("/inventory/fruit/remove?name=Papaya");
        this.testRestTemplate.delete("/inventory/fruit/remove?name=Sandia");
        String actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getAllFruitNames", String.class);

        // then
        Assert.assertEquals("Test #5 failed!!!", expectedResult, actualResult);
    }

    @Test
    public void whenGetDiscountedPrice_thenPriceIsDiscounted() {
        System.out.println("Test #6 - When asking for a discount, then the price is discounted.");

        // given
        FruitEntity banano= new FruitEntity("Banano", 20.00f, 150l);
        Double expectedResult= 10.00d;
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano= this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);
        Double actualResult= this.testRestTemplate.getForObject("/inventory/fruit/getReducedPrice/Melon?discount=10", Double.class);
        // then
        Assert.assertEquals("Test #6 failed!!!", expectedResult, actualResult);
    }



    @Test
    public void whenFruitsInserted_GetSubtotal() {
        System.out.println("Test #7 - When Fruits inserted, get subtotal.");
        // given
        FruitEntity banano=new FruitEntity("Banano", 15.00f, 10l);
        Double expectedResult= 84.00d;
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertMelon= this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);
        Double actualResult= this.testRestTemplate.getForObject("/inventory/fruit/getSubtotal/Banano/6?discount=6", Double.class);
        // then
        Assert.assertEquals("Test #7 failed!!!", expectedResult, actualResult);
    }

    @Test
    public void whenFruitisInserted_thenUpdateStock() {
        System.out.println("Test #8 - When Fruit is inserted, then update stock.");
        // given
        FruitEntity banano= new FruitEntity("Banano", 15.00f, 10l);
        Long expectedResult= 10l;
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano = this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);
        Long actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getFruitStock/1", Long.class);
        // then
        Assert.assertEquals("Test #8 failed!!!", expectedResult, actualResult);
    }


    @Test
    public void whenFruitInserted_thenUpdatedStock() {
        System.out.println("Test #9 - When Fruits inserted and reset, then list must be empty.");
        // given
        FruitEntity banano= new FruitEntity("Banano", 2.50f, 100l);
        FruitEntity papaya= new FruitEntity("Papaya", 15.00f, 15l);
        FruitEntity melon= new FruitEntity("Melon", 14.00f, 10l);
        FruitEntity sandia= new FruitEntity("Sandia", 35.50f, 17l);
        FruitEntity platano= new FruitEntity("Plantano",22.22f,90l);
        int expectedResult= new FruitListWrapper(new ArrayList<FruitEntity>()).getFruits().size();
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        int actualResult= this.testRestTemplate.getForObject("/inventory/fruit//getAllFruits", FruitListWrapper.class).getFruits().size();
        // then
        Assert.assertEquals("Test #9 failed!!!", expectedResult, actualResult);
    }

    @Test
    public void whenGetDiscountedPrice_thenPriceIsDiscounted1() {
        System.out.println("Test #6 - When asking for a discount, then the price is discounted.");

        // given
        FruitEntity melon= new FruitEntity("Melon", 30.00f, 150l);
        Double expectedResult= 10.00d;
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertMelon=this.testRestTemplate.postForObject("/inventory/fruit/register", melon, FruitEntity.class);
        Double actualResult= this.testRestTemplate.getForObject("/inventory/fruit/getReducedPrice/Melon?discount=20", Double.class);
        // then
        Assert.assertEquals("Test #10 failed!!!", expectedResult, actualResult);
    }

    @Test
    public void a(){
        System.out.println("Test #11 - When asking for a discount in price, then the price is updated with a discount.");
        // given
        FruitEntity melon= new FruitEntity("Melon", 30.00f, 150l);
        Double expectedResult= 10.00d;
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertMelon= this.testRestTemplate.postForObject("/inventory/fruit/register", melon, FruitEntity.class);
        Double actualResult= this.testRestTemplate.getForObject("/inventory/fruit/getReducedPrice/Melon?discount=20", Double.class);
        // then

        Assert.assertEquals("Test #11 failed!!!", expectedResult, actualResult);

    }

    @Test(expected = org.springframework.web.client.RestClientException.class)
    public void whenDiscountedPriceAndDiscountNotPresent_thenThrowsException() {
        System.out.println("Test #12 - When discounted price and discount not present, then throws exception.");

        // given
        FruitEntity banano = new FruitEntity("banano", 15.00f, 10l);
        Double expectedResult = 15.00d;
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano = this.testRestTemplate.postForObject("/inventory/fruit/register", banano, FruitEntity.class);

    Double actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getReducedPrice/banano", Double.class);

        // then
        Assert.assertEquals("Test #12 failed!!!", expectedResult, actualResult);
    }

     @Test(expected = org.springframework.web.client.RestClientException.class)
     public void whenGettingFruitStockWhileDiscountIsString_thenThrowsException() {
     System.out.println("Test #13 - When getting fruit stock while id is string, then throws exception.");

     // given
     FruitEntity Banano = new FruitEntity("Banano", 15.00f, 120l);
     Double expectedResult = 115.00d;
     // when
     this.testRestTemplate.delete("/inventory/fruit/reset");
     FruitEntity insertBanano= this.testRestTemplate.postForObject("/inventory/fruit/register", Banano, FruitEntity.class);

     Double actualResult = this.testRestTemplate.getForObject("/inventory/fruit/getFruitStock/EXCEPTION", Double.class);

     // then
     Assert.assertEquals("Test #13 failed!!!", expectedResult, actualResult);
     }


    @Test
    public void whenSameFruitIsInsertedMultipleTimes_thenFruitListShouldContainDuplicates() {
        System.out.println("Test #14 - When the same fruit is inserted multiple times, fruit list should contain duplicates.");

        // given
        FruitEntity banano1 = new FruitEntity("Banano", 2.50f, 100l);
        FruitEntity banano2 = new FruitEntity("Banano", 15.00f, 15l);
        FruitEntity banano3=new FruitEntity("Banano", 20.00f,20l);
        FruitEntity melon1=new FruitEntity("Melon", 22.00f,22l);
        FruitEntity melon2=new FruitEntity("Melon", 33.00f,33l);
        String expectedResult = "[Banano, Banano, Banano, Melon, Melon]";
        // when
        this.testRestTemplate.delete("/inventory/fruit/reset");
        FruitEntity insertBanano1= this.testRestTemplate.postForObject("/inventory/fruit/register", banano1, FruitEntity.class);
        FruitEntity insertBanano2=this.testRestTemplate.postForObject("/inventory/fruit/register", banano2, FruitEntity.class);
        FruitEntity insertBanano3=this.testRestTemplate.postForObject("/inventory/fruit/register", banano3,FruitEntity.class);
        FruitEntity insertMelon1=this.testRestTemplate.postForObject("/inventory/fruit/register", melon1,FruitEntity.class);
        FruitEntity insertMelon2=this.testRestTemplate.postForObject("/inventory/fruit/register", melon2,FruitEntity.class);
        String actualResult =this.testRestTemplate.getForObject("/inventory/fruit/getAllFruitNames", String.class);
        // then
        Assert.assertEquals("Test #14 failed!!!", expectedResult, actualResult);
    }


}




