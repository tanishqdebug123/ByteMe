import java.io.Serializable;
import java.util.*;

public class Cart implements Serializable {
    public HashMap<String,Integer> cart;
    public int ordervalue;

    public Cart(){
        this.cart=new HashMap<>();
        this.ordervalue=0;
    }

    public void additem(String name,Integer quantity){
        cart.put(name,quantity);
    }

    public int getquantity(String name){
        return cart.get(name);
    }

    public void changequantity(String name,Integer newquantity){
        cart.put(name,newquantity);
    }

    public void removeitem(String name){
        cart.remove(name);
    }

    public HashMap<String, Integer> getCart(){
        return cart;
    }

    public void clearcart(){
        cart.clear();
    }


}
