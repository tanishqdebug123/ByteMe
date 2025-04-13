import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class InvalidLoginTest {
    @BeforeClass
    public static void setup(){
        Main.customer_ls.add(new Customer("cs1@gmail.com","cs1"));
        Main.customer_ls.add(new Customer("a2","a2"));
        Main.ad1=new Admin("a1","a1");
    }
    @Test
    public void customer_logintest1() {
        boolean found1 = Main.cust_login("cs1@gmail.com", "cs1");
        assertTrue(found1);
    }
    @Test
    public void customer_logintest2() {
        boolean found1 = Main.cust_login("cs1@gmail.com", "cs");
        assertFalse(found1);
    }
    @Test
    public void admin_logintest1() {
        boolean found3= Main.admin_login("a1","a1");
        assertTrue(found3);
    }
    @Test
    public void admin_logintest2() {
        boolean found4= Main.admin_login("a2","a1");
        System.out.println("Invalid login\n");
        assertFalse(found4);


    }
}




