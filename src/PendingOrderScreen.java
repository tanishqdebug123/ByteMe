import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PendingOrderScreen extends JPanel {
    public PendingOrderScreen(JPanel cardPanel) {
        // Set up the layout of the panel
        setLayout(new BorderLayout());
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        JTextArea Area = new JTextArea();
        Area.setEditable(false);

        //Reading the file and add content to data
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/pendingorders"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line1=line.split(":");
                data.add(line1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        StringBuilder menuscreen=new StringBuilder();
        menuscreen.append("Order no-Food Item-Quantity-Status\n");
        for (String[] d1:data){
            menuscreen.append(d1[0]+"-"+d1[1]+"-"+d1[2]+"-"+d1[3]+"\n");
        }
        Area.setText(menuscreen.toString());
        JScrollPane scrollPane=new JScrollPane(Area);
        add(scrollPane, BorderLayout.CENTER);

        JButton menubutton = new JButton("Menu");
        menubutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "MenuScreen");
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(menubutton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
