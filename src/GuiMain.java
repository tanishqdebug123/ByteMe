import javax.swing.*;
import java.awt.*;

public class GuiMain {
    public static void main(String[] args) {

        JFrame frame = new JFrame("ByteMe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);


        JPanel mainpanel= new JPanel(new CardLayout());

        MenuScreen menuScreen = new MenuScreen(mainpanel);
        PendingOrderScreen pendingOrdersScreen = new PendingOrderScreen(mainpanel);

        mainpanel.add(menuScreen, "MenuScreen");
        mainpanel.add(pendingOrdersScreen, "PendingOrdersScreen");

        frame.add(mainpanel);
        frame.setVisible(true);

    }
}
