import java.io.Serializable;
import java.util.*;

public class Customer implements Serializable {
    String customer_id;
    String customer_pass;
    String customer_type;
    Cart customer_cart;


    public Customer(String id,String pass){
        this.customer_id=id;
        this.customer_pass=pass;
        this.customer_cart=new Cart();
        this.customer_type="Regular";
    }

    public Customer(String id,String pass,String customer_type){
        this.customer_id=id;
        this.customer_pass=pass;
        this.customer_cart=new Cart();
        this.customer_type=customer_type;
    }


    public void browsemenu(ArrayList<MenuFoodItem> menu){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter one of the following commands\n1.View Complete Menu\n2.Search specific item\n3.Filter by category\n4.Sort by price\n9.Back to Main menu\n");
        int command=sc.nextInt();
        if(command==1){
            System.out.println("View complete menu");
            for(MenuFoodItem fd:menu){
                System.out.println(fd.name+" "+fd.category+" "+fd.price+" "+fd.availability);
            }
        }

        else if(command==2){
            System.out.println("Search specific menu");
            System.out.println("Enter the name of Food item: ");
            sc.nextLine();
            String name=sc.nextLine();
            boolean check=false;
            for (MenuFoodItem fd:menu){
                if(Objects.equals(fd.name, name)){
                    check=true;
                    System.out.println(fd.name+" "+fd.category+" "+fd.price+" "+fd.availability);
                }
            }
            if(!check) System.out.println("Not a valid Food Item name Try again");
        }

        else if(command==3){
            System.out.println("Filter by category");
            System.out.println("Enter the category of Food item: ");
            sc.nextLine();
            String category =sc.nextLine();
            boolean check=false;
            for (MenuFoodItem fd:menu){
                if(Objects.equals(fd.category,category)){
                    check=true;
                    System.out.println(fd.name+" "+fd.category+" "+fd.price+" "+fd.availability);
                }
            }
            if(!check) System.out.println("Not a valid Food Item category Try again");
        }

        else if(command==4){
            System.out.println("Sort by price");
            //copying the menu arraylist
            ArrayList<MenuFoodItem> sortedmenu = new ArrayList<>();
            for(MenuFoodItem fd:menu){
                sortedmenu.add(fd);
            }

            System.out.println("Enter the order of sort\n1.Ascending\n2.Descending");
            int inp=sc.nextInt();
            if(inp==1) {
                sortedmenu.sort(Comparator.comparing(MenuFoodItem -> MenuFoodItem.price));
            }
            else if(inp==2){
                sortedmenu.sort(Comparator.comparing((MenuFoodItem fd1) -> fd1.price).reversed());
            }
            for (MenuFoodItem fd1 : sortedmenu) {
                System.out.println(fd1.name + " " + fd1.price);
            }
        }

        else if(command==9) {
            System.out.println("Returning to Main menu\n");
        }
    }

    public void Item_Reviews(ArrayList<MenuFoodItem> menu){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter one of the following commands\n1.Provide Order review\n2.View Reviews\n9.Back to Main menu\n");
        int command=sc.nextInt();

        if(command==1){
            boolean check=false;
            System.out.println("Provide order Review");
            System.out.println("Enter the name of food item for which reviews need to be shown: ");
            sc.nextLine();
            String name=sc.nextLine();

            for(MenuFoodItem fd:menu){
                if(Objects.equals(fd.name, name)){
                    check=true;
                    System.out.println("Enter your feedback for the selected food item: ");
                    String feedback =sc.nextLine();
                    fd.reviews.add(feedback);
                    break;
                }
            }
            if(!check) System.out.println("Not a valid food Item");
        }
        else if(command==2){
            boolean check=false;
            System.out.println("View Reviews");
            System.out.println("Enter the name of food item for which reviews need to be shown: ");
            sc.nextLine();
            String name=sc.nextLine();

            for(MenuFoodItem fd:menu){
                if(Objects.equals(fd.name, name)){
                    check=true;
                    System.out.println(fd.reviews);
                    break;
                }
            }
            if(!check) System.out.println("Not a valid food Item");

        }
        else if(command==9) {
            System.out.println("Returning to Main menu\n");
        }
    }

    public void change_customer_type(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Your current Customer type is: "+ this.customer_type);

        if(Objects.equals(this.customer_type, "Regular")){
            System.out.println("Do you want to become a VIP customer,If Yes please pay the fees\n1.Yes\n2.No");
            int inp= sc.nextInt();
            if(inp==2) return;
            else{
                System.out.println("Congratulation you have become a VIP customer");
                this.customer_type="VIP";
            }
        }
        else {
            System.out.println("You are already a VIP customer");
        }
    }



    public static boolean check_stock(MenuFoodItem fd, int quantity){
        if (fd.availability<quantity) {
            return false;
        }
        else {
            return true;
        }
    }


    public void cart_operation(Cart customer_cart,ArrayList<MenuFoodItem> menu,TreeMap<Integer,Order> pending_orders,int orderno){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter one of the following commands\n1.Add items in cart\n2.Modify quantities\n3.Remove Items\n4.View Total\n5.Checkout processes\n9.Back to Main menu\n");
        int command=sc.nextInt();
        if(command==1){
            System.out.println("Add items in cart\n");
            while (true){
                System.out.println("Enter one of the following options\n1.Add item\n2.Exit");
                int option=sc.nextInt();
                if(option==2) break;

                System.out.println("Enter the name of food item: ");
                sc.nextLine();
                String name=sc.nextLine();

                boolean check=false;
                for(MenuFoodItem fd:menu){
                    if(Objects.equals(fd.name, name)){
                        check=true;
                        System.out.println("Enter the quantity of food item required(in units): ");
                        int quantity=sc.nextInt();


                        boolean enough=check_stock(fd,quantity);
                        if(enough){
                            customer_cart.additem(name, quantity);
                        }
                        else{
                            System.out.println("There is not enough available quantity for the specified food Item");
                        }
                        break;
                    }
                }
                if(!check) System.out.println("Not a valid food item try again");
            }
        }

        else if(command==2){
            System.out.println("Change quantity\n");
            System.out.println("Enter the name of food item for which quantity needs to be changed: ");
            sc.nextLine();
            String name=sc.nextLine();
            System.out.println("Current quantity is "+customer_cart.getquantity(name));
            System.out.println("Enter the new quantity of food item required(in units): ");
            int new_quantity=sc.nextInt();
            customer_cart.changequantity(name,new_quantity);
        }

        else if(command==3){
            System.out.println("Remove items");
            System.out.println("Enter the name of food item which needs to be removed: ");
            sc.nextLine();
            String name=sc.nextLine();
            customer_cart.removeitem(name);
        }

        else if(command==4){
            System.out.println("View Total");
            HashMap<String,Integer> l1=customer_cart.getCart();
            int total_cost=0;
            for (Map.Entry<String, Integer> entry : l1.entrySet()) {
                int cost=0;
                String item_name=entry.getKey();
                for(MenuFoodItem fd:menu){
                    if(Objects.equals(fd.name, item_name)){
                        cost=entry.getValue()*fd.price;
                        total_cost+=cost;
                        break;
                    }
                }
                System.out.println("Item: " + entry.getKey() + "Quantity: " + entry.getValue()+"Cost: "+cost);
            }
            System.out.println("Cart total is: "+ total_cost);
        }

        else if(command==5){
            System.out.println("Checkout process: ");
            System.out.println("Make the payment");
            System.out.println("Enter your delivery address");
            sc.nextLine();
            String address=sc.nextLine();

            HashMap<String,Integer> l1=customer_cart.getCart();

            for (Map.Entry<String, Integer> entry : l1.entrySet()) {

                String item_name=entry.getKey();
                int quantity=entry.getValue();
                Customer customer_order=this;
                String order_status="Order Received";
                int order_no=Main.orderno;
                String descriptions;
                System.out.println("Order no: "+order_no+"Food Item: "+entry.getKey()+"Quantity: "+entry.getValue());
                System.out.println("Do you wish to add special requests for this order\n1.Yes\n2.No");
                int inp=sc.nextInt();
                if(inp==1){
                    System.out.println("Enter special request: ");
                    sc.nextLine();
                    String hd=sc.nextLine();
                    descriptions=hd;
                }
                else {
                    descriptions="";
                }
                boolean priority= Objects.equals(this.customer_type, "VIP");
                Main.orderno+=1;

                Order o_temp=new Order(orderno,item_name,quantity,customer_order,descriptions,order_status,priority);
                pending_orders.put(orderno,o_temp);
            }
            System.out.println("Your order has been successfully placed");
            customer_cart.clearcart();
        }
        else if(command==9) {
            System.out.println("Returning to Main menu\n");
        }
    }



    public void order_tracking(TreeMap<Integer,Order> pending_orders,TreeMap<Integer,Order> completed_orders,int orderno){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter one of the following commands\n1.View Order status\n2.Cancel order\n3.Order history\n9.Back to Main menu\n");
        int command=sc.nextInt();
        if(command==1){
            System.out.println("View Order status");

            System.out.println("Your pending orders are: ");
            for(Map.Entry<Integer,Order> entry : pending_orders.entrySet()){
                if(entry.getValue().customer_order==this){
                    System.out.println(entry.getValue().order_no+" "+entry.getValue().order_status);
                }
            }

        }

        else if(command==2){
            System.out.println("Cancel order");
            System.out.println("Enter the order no which needs to be cancelled: ");
            int inp=sc.nextInt();
            if(pending_orders.containsKey(inp)){
                pending_orders.remove(inp);
            }
            else{
                System.out.println("Either order is completed or Invalid order no");
            }
        }



        else if(command==3){
            System.out.println("Order history");
            for(Map.Entry<Integer,Order> entry : completed_orders.entrySet()){
                if(entry.getValue().customer_order==this){
                    System.out.println(entry.getValue().order_no+" "+entry.getValue().food_item);
                    System.out.println("Do you wish to reorder this\n1.Yes\n2.No");
                    int inp=sc.nextInt();
                    if(inp==1){
                        String item_name=entry.getValue().food_item;
                        int quantity=entry.getValue().quantity;
                        Customer customer_order=entry.getValue().customer_order;
                        String order_status="Order Received";
                        int order_no=orderno;
                        String descriptions=entry.getValue().descriptions;
                        boolean priority=entry.getValue().priority;
                        orderno+=1;

                        Order o_temp=new Order(order_no,item_name,quantity,customer_order,descriptions,order_status,priority);
                        System.out.println("New order no is "+ o_temp.order_no);
                        pending_orders.put(o_temp.order_no,o_temp);


                        for(Map.Entry<Integer,Order> a1 : pending_orders.entrySet()){
                            System.out.println("Order no: "+a1.getKey()+"order "+a1.getValue().food_item );
                        }

                        System.out.println("Congratulations! Your order has been placed");
                    }
                }
            }
        }

        else if(command==9) {
            System.out.println("Returning to Main menu\n");
        }



    }


}
