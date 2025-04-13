import java.io.Serializable;

public class Order implements Serializable {
    int order_no;
    String food_item;
    int quantity;
    Customer customer_order;
    String descriptions;
    String order_status;
    boolean priority;

    public Order(int order_no,String food_item,int quantity,Customer customer_order,String descriptions,String order_status,boolean priority){
        this.food_item=food_item;
        this.order_no=order_no;
        this.quantity=quantity;
        this.customer_order=customer_order;
        this.descriptions=descriptions;
        this.order_status=order_status;
        this.priority=priority;
    }
}
