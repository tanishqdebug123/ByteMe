import java.util.ArrayList;

public class MenuFoodItem {
    String name;
    int price;
    String category;
    int availability;
    ArrayList<String> reviews;


    public MenuFoodItem(String name, int price, String category, int availability){
        this.name=name;
        this.price=price;
        this.category=category;
        this.availability=availability;
        this.reviews=new ArrayList<>();
    }

    public MenuFoodItem(String name, int price, String category, int availability,ArrayList<String> reviews){
        this.name=name;
        this.price=price;
        this.category=category;
        this.availability=availability;
        this.reviews=reviews;
    }

}
