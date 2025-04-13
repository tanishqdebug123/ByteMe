import java.io.*;
import java.util.*;


public class Main {
    public static ArrayList<Customer> customer_ls=new ArrayList<>();
    public static Admin ad1;
    public static int orderno=4;
    public static ArrayList<MenuFoodItem> menu=new ArrayList<>();
    public static TreeMap<Integer,Order> pending_orders=new TreeMap<Integer, Order>();
    public static TreeMap<Integer,Order> completed_orders =new TreeMap<Integer, Order>();
    public static TreeMap<Integer,Order> canceled_orders =new TreeMap<Integer, Order>();

    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) throws IOException {

        ad1=new Admin("a1","a1");

        Customer cs1=new Customer("cs1@gmail.com","cs1");
        Customer cs2=new Customer("cs2@gmail.com","cs2");
        Customer cs3 =new Customer("cs3@gmail.com","cs3");

        customer_ls.add(cs1);
        customer_ls.add(cs2);
        customer_ls.add(cs3);

        File file=new File("src/customerdata");

        if(file.length()!=0) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/customerdata"))) {
                customer_ls = (ArrayList<Customer>) in.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(customer_ls);


        ArrayList<String> feedback=new ArrayList<>();
        feedback.add("Good taste");
        feedback.add("Bad food");
        MenuFoodItem m1=new MenuFoodItem("F1",120,"Veg",100);
        MenuFoodItem m2=new MenuFoodItem("F2",140,"Veg",10,feedback);
        MenuFoodItem m3=new MenuFoodItem("F3",100,"Non-veg",100,feedback);
        menu.add(m1);
        menu.add(m2);
        menu.add(m3);

        File file2=new File("src/menu");
        if(file2.length()==0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/menu"))) {
                for (MenuFoodItem item : menu) {
                    writer.write(item.name + ":" + item.price + ":" + item.category+":"+item.availability);
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        Order o1=new Order(1,"F1",2,cs1,"No onions","Out for delivery",false);
        Order o2=new Order(3,"F2",3,cs2,"No onions","Processing",true);
        Order o3=new Order(2,"F1",2,cs1,"No onions","Completed",false);
        Order o4=new Order(4,"F1",2,cs1,"No onions","Completed",false);
        pending_orders.put(1,o1);
//        pending_orders.put(3,o2);
        completed_orders.put(2,o3);
        completed_orders.put(4,o4);

        //Writing completed orders
        File file3=new File("src/orderhistory");
        if(file3.length()!=0) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/orderhistory"))) {
                completed_orders = (TreeMap<Integer,Order>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(completed_orders);


        // Writing Pending orders
        File file4=new File("src/pendingorders");
        if(file4.length()==0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/pendingorders"))){
                for (Map.Entry<Integer,Order> entry : pending_orders.entrySet()){
                    writer.write(entry.getKey() + ":" + entry.getValue().food_item+":"+entry.getValue().quantity+":"+entry.getValue().order_status);
                    writer.newLine();
                }
            }
            catch (IOException e) {
                throw new RuntimeException();
            }
        }


        while (true) {
            System.out.println("Welcome to Byte Me!");
            System.out.print("Enter your input (1:Enter/2:Exit) ");
            int input = sc.nextInt();
            if (input == 1) {
                System.out.print("Enter your role\n1:Customer\n2:Admin\n");
                int role = sc.nextInt();
                if (role == 1) {
                    enterascustomer();

                }
                else if (role == 2) {
                    enterasadmin();
                }
                else {
                    System.out.println("Invalid Input\nEnter 1 to try again\nEnter 2 to quit");
                    int id=sc.nextInt();
                    if(id==2){
                        break;
                    }
                }
            }
            else if (input == 2) {
                System.out.println("Successfully logged out.");
                break;
            }
        }

        //Writing back to file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/customerdata"))) {
            out.writeObject(customer_ls);
        }

        // writing updates to pending order
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/pendingorders"))){
            for (Map.Entry<Integer,Order> entry : pending_orders.entrySet()){
                writer.write(entry.getKey() + ":" + entry.getValue().food_item+":"+entry.getValue().quantity+":"+entry.getValue().order_status);
                writer.newLine();
            }
        }
        catch (IOException e) {
            throw new RuntimeException();
        }


    }


    private static void enterasadmin() {
        while(true) {
            System.out.println("Enter your Admin id: ");
            String inp_admin_id = sc.next();
            System.out.println("Enter your password: ");
            String inp_admin_pass = sc.next();

            boolean admin_found=admin_login(inp_admin_id,inp_admin_pass);
            if(admin_found){
                show_admin_menu(ad1);
                break;
            }
            else if (!admin_found) {
                System.out.println("Invalid Credentials\n");
                System.out.println("Enter one of the following\n1.Try again\n2.Exit");
                int inp= sc.nextInt();
                if(inp==2) break;
            }
        }
    }

    public static boolean admin_login(String admin_id,String admin_pass){
        if(Objects.equals(ad1.admin_id, admin_id) && Objects.equals(ad1.admin_pass, admin_pass)){
            System.out.println("Login successful\n");
            return true;
        }
        else{
            return false;
        }
    }

    private static void enterascustomer(){
        System.out.println("1.Login or 2.Signup");
        int in=sc.nextInt();
        if(in==1){
            while(true) {
                System.out.println("Enter your Customer id: ");
                String cust_id = sc.next();
                System.out.println("Enter your password: ");
                String cust_pass = sc.next();

                boolean cs_found=false;
                for(Customer cs_iterate:customer_ls){
                    if(Objects.equals(cs_iterate.customer_id, cust_id) && Objects.equals(cs_iterate.customer_pass, cust_pass)){
                        System.out.println("Login successful\n");
                        cs_found=true;
                        show_customer_menu(cs_iterate);
                        break;
                    }
                }

                if(cs_found){
                    break;
                }
                else if(!cs_found){
                    System.out.println("Invalid Credentials\n");
                    System.out.println("Enter one of the following\n1.Try again\n2.Exit");
                    int inp= sc.nextInt();
                    if(inp==2) break;
                }
            }
        }
        else if (in==2){
            System.out.println("Enter a new Customer id: ");
            String cust_id = sc.next();
            System.out.println("Create a new password: ");
            String cust_pass = sc.next();

            Customer cs_new=new Customer(cust_id,cust_pass);
            System.out.println("You have successfully signed in");


//            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/customerdata",true))) {
//                writer.write(cust_id+":"+cust_pass);
//            }catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            customer_ls.add(cs_new);
            show_customer_menu(cs_new);
        }
    }


    public static boolean cust_login(String cust_id,String cust_pass){
        boolean cs_found=false;
        for(Customer cs_iterate:customer_ls){
            if(Objects.equals(cs_iterate.customer_id, cust_id) && Objects.equals(cs_iterate.customer_pass, cust_pass)){
                System.out.println("Login successful\n");
                cs_found=true;
                break;
            }
        }
        return cs_found;
    }

    private static void show_customer_menu(Customer cs){
        System.out.println("Showing customer menu");
        while(true){
            System.out.println("Enter one of the following commands\n1.Browse Menu\n2.Cart operations\n3.Order Tracking\n4.Item Reviews\n5.Change customer type\n9.Logout\n");
            int command=sc.nextInt();
            if(command==1){
                System.out.println("BrowseMenu\n");
                cs.browsemenu(menu);
            }
            else if (command==2) {
                System.out.println("Cart operations\n");
                cs.cart_operation(cs.customer_cart,menu,pending_orders,orderno);
            }
            else if (command==3) {
                System.out.println("Order Tracking\n");
                cs.order_tracking(pending_orders,completed_orders,orderno);
            }
            else if (command==4) {
                System.out.println("Item Reviews\n");
                cs.Item_Reviews(menu);
            }
            else if (command==5) {
                System.out.println("Change customer type\n");
                cs.change_customer_type();
            }
            else if(command==9) {
                System.out.println("Successfully logged out.\n");
                break;
            }
        }
    }


    private static void show_admin_menu(Admin ad1) {
        System.out.println("Showing Admin menu");
        while(true){
            System.out.println("Enter one of the following commands\n1.Menu Management\n2.Order Management\n3.Generate daily sales report\n9.Logout\n");
            int command=sc.nextInt();
            if(command==1){
                System.out.println("Menu management\n");
                ad1.menu_management(menu);
            }
            else if(command==2){
                System.out.println("Order management\n");
                ad1.order_management(pending_orders,completed_orders,canceled_orders);
            }
            else if(command==3){
                System.out.println("Sales Report\n");
                ad1.sales_report(completed_orders,menu);
            }
            else if(command==9) {
                System.out.println("Successfully logged out.\n");
                break;
            }
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/orderhistory"))) {
            out.writeObject(completed_orders);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/orderhistory"))) {
            TreeMap<Integer,Order> newlist= (TreeMap<Integer,Order>) in.readObject();
            System.out.println(newlist);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }





    }
}

