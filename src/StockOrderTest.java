import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class StockOrderTest {
    static MenuFoodItem fd1;
    @BeforeClass
    public static void setup(){
        fd1=new MenuFoodItem("F1",120,"Veg",100);
    }


    @Test
    public void instockTest() {
        boolean found1 = Customer.check_stock(fd1,100);
        System.out.println("Item in stock");
        assertTrue(found1);
    }
    @Test
    public void outofstockTest() {
        boolean found1 = Customer.check_stock(fd1,101);
        System.out.println("Item out of stock");
        assertFalse(found1);
    }
}




