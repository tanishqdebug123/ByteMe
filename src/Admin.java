import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Admin{
    String admin_id;
    String admin_pass;

    Scanner sc=new Scanner(System.in);
    public Admin(String id,String pass){
        this.admin_id=id;
        this.admin_pass=pass;
    }

    public void menu_management(ArrayList<MenuFoodItem> Menu){
        // printing current menu
        System.out.println("Current Menu: \n");
        for (MenuFoodItem fd:Menu){
            System.out.println(fd.name+" "+fd.price+" "+fd.category+" "+fd.availability+" \n");
        }


        System.out.println("Enter one of the following commands\n1.Add new Items\n2.Update existing items\n3.Remove Items\n9.Back to Main menu\n");
        int command=sc.nextInt();
        if(command==1){
            System.out.println("Add new Item");
            System.out.println("Enter the name of the food item: ");
            sc.nextLine();
            String name=sc.nextLine();
            System.out.println("Enter the price for the food item: ");
            int price=sc.nextInt();
            System.out.println("Enter the category of the food item: ");
            String category=sc.next();
            System.out.println("Enter the availablity: ");
            int availability=sc.nextInt();
            MenuFoodItem food1=new MenuFoodItem(name,price,category,availability);
            Menu.add(food1);
            System.out.println("Food Item added to menu");
        }

        else if(command==2){
            System.out.println("Update existing items");
            System.out.println("Please enter the name of the food item you want to change: ");
            sc.nextLine();
            String name=sc.nextLine();
            boolean change=false;

            for(MenuFoodItem fd:Menu){
                if(Objects.equals(fd.name,name)){
                    System.out.println("Enter the quantity to be changed\n1.Price\n2.Availability");
                    int inp=sc.nextInt();
                    if(inp==1){
                        System.out.println("Enter the new price: ");
                        int new_price=sc.nextInt();
                        fd.price=new_price;
                    }
                    else if(inp==2){
                        System.out.println("Enter the new Availability: ");
                        int new_availibility=sc.nextInt();
                        fd.availability=new_availibility;
                    }
                    change=true;
                }
            }
            if(!change) System.out.println("Not a valid food item,Please try again");
        }

        else if(command==3){
            System.out.println("Remove Items");
            System.out.println("Please enter the name of the food item you want to remove: ");
            sc.nextLine();
            String name=sc.nextLine();

            MenuFoodItem To_Remove=null;
            for(MenuFoodItem fd:Menu){
                if(Objects.equals(fd.name, name)){
                    To_Remove=fd;
                    System.out.println("Food item is removed successfully");
                }
            }
            // To avoid ConcurrentModificationException
            if(To_Remove==null) System.out.println("Not a valid food item,Please try again");
            else Menu.remove(To_Remove);

        }
        else if(command==9) {
            System.out.println("Returning to Main menu\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/menu"))) {
            for (MenuFoodItem item : Menu) {
                writer.write(item.name + ":" + item.price + ":" + item.category+":"+item.availability);
                writer.newLine();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void order_management(TreeMap<Integer,Order> pending_orders, TreeMap<Integer,Order> completed_orders,TreeMap<Integer,Order> canceled_orders){
        System.out.println("Enter one of the following commands\n1.View Pending orders\n2.Update Order status\n3.Process refunds\n4.Handle special requests\n5.Order Priority\n9.Back to Main menu\n");
        int command=sc.nextInt();


        if(command==1){
            System.out.println("View Pending orders: ");
            for(Map.Entry<Integer,Order> entry : pending_orders.entrySet()){
                System.out.println("Order no:"+entry.getKey()+"  Food item: "+entry.getValue().food_item);
            }
        }

        else if(command==2){
            System.out.println("Update order status: ");
            System.out.println("Please enter the order number for which you need to update status: ");
            int order_no =sc.nextInt();
            Integer To_Remove=null;
            for(Map.Entry<Integer,Order> entry : pending_orders.entrySet()){
                if(entry.getKey()== order_no){
                    To_Remove=-1;
                    System.out.println("Current status:"+entry.getValue().order_status);
                    System.out.println("Please select the new order status\n1.Completed\n2.Out for delivery\n3.Preparing");
                    int status=sc.nextInt();
                    if(status==1){
                        System.out.println("Order has been completed");
                        To_Remove=entry.getKey();
                        completed_orders.put(entry.getKey(),entry.getValue());
                    }
                    else if (status==2) {
                        System.out.println("Order is on its way");
                        entry.getValue().order_status="out_for_delivery";
                    }
                    else if (status==3) {
                        System.out.println("Order is being prepared");
                        entry.getValue().order_status="preparing";
                    }
                }
            }

            if(To_Remove==null) System.out.println("Not a valid Order number,Please try again");
            else if (To_Remove!=-1) pending_orders.remove(To_Remove);
        }

        else if(command==3){
            System.out.println("Process refunds: ");
            System.out.println("Please enter the order number for which you need to Process refunds: ");
            int order_no=sc.nextInt();
            System.out.println("Refund Initiated");
        }

        else if(command==4){
            System.out.println("Handle special request: ");
            System.out.println("Please enter the order number for which you need to see special Request: ");
            int order_no=sc.nextInt();
            if(pending_orders.get(order_no)!=null){
                Order o1=pending_orders.get(order_no);
                if(o1.descriptions!=null){
                    System.out.println("Special request is: "+o1.descriptions);
                    System.out.println("Your request has been acknowledged, and processing of your order has begun.");
                }
                else{
                    System.out.println("No special request ");
                }
            }
            else{
                System.out.println("Not a valid Order number,Please try again");
            }
        }

        else if(command==5){
            System.out.println("Order priority");
            System.out.println("Processing Orders according to there priority");
            for(Map.Entry<Integer,Order> entry : pending_orders.entrySet()){
                if(entry.getValue().priority) System.out.println("Order no:"+entry.getKey()+"Food item: "+entry.getValue().food_item);
            }
            for(Map.Entry<Integer,Order> entry : pending_orders.entrySet()){
                if(!entry.getValue().priority) System.out.println("Order no:"+entry.getKey()+"Food item: "+entry.getValue().food_item);
            }

        }

        else if(command==9) {
            System.out.println("Returning to Main menu\n");
        }
    }

    public void sales_report(TreeMap<Integer,Order> completed_orders,ArrayList<MenuFoodItem> menu){
        int totalsales=0;
        System.out.println("Total no of orders completed: "+ completed_orders.size());
        for(Map.Entry<Integer,Order> entry : completed_orders.entrySet()){
            System.out.println("Order no "+entry.getValue().order_no+" Food item "+entry.getValue().food_item+" Quantity "+entry.getValue().quantity+" Customer Id "+entry.getValue().customer_order.customer_id);

            String item_name=entry.getValue().food_item;
            int q=entry.getValue().quantity;
            for(MenuFoodItem fd:menu){
                if(Objects.equals(fd.name, item_name)){
                    totalsales+=q*fd.price;
                    break;
                }
            }
        }

        System.out.println("Total sales for the day is "+totalsales);
    }

}
