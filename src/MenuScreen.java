import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MenuScreen extends JPanel {
    public MenuScreen(JPanel cardPanel) {

        setLayout(new BorderLayout());
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        JTextArea Area = new JTextArea();
        Area.setEditable(false);



        //Reading the file and add content to data
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/menu"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line1=line.split(":");
                data.add(line1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        StringBuilder menuscreen=new StringBuilder();
        menuscreen.append("Food Item-Availability-Category-Price\n");
        for (String[] d1:data){
            menuscreen.append(d1[0]+"-"+d1[1]+"-"+d1[2]+"-"+d1[3]+"\n");
        }
        Area.setText(menuscreen.toString());
        JScrollPane scrollPane=new JScrollPane(Area);
        add(scrollPane, BorderLayout.CENTER);


        JButton pendingOrdersButton = new JButton("Pending Orders");
        pendingOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "PendingOrdersScreen");
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(pendingOrdersButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
