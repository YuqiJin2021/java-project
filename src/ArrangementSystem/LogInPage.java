package ArrangementSystem;

import javax.swing.*;
import java.awt.*;

public class LogInPage extends JFrame {
    public LogInPage() {
        this.setSize(400,300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel total = new JPanel(new GridLayout(3,1));
        JPanel idPanel = new JPanel();
        JLabel id = new JLabel("Id");
        JTextField text = new JTextField(20);
        idPanel.add(id);
        idPanel.add(text);
        total.add(idPanel);

        JPanel patientName = new JPanel();
        JLabel name = new JLabel("Prefer Name");
        JTextField n = new JTextField(20);
        patientName.add(name);
        patientName.add(n);
        total.add(patientName);


        JPanel buttons = new JPanel();
        JButton confirm = new JButton("confirm");
        JButton goBack = new JButton("Go back");
        confirm.addActionListener(e -> {
            this.dispose();
            new MainPageForBooking(n.getText(),Integer.parseInt(text.getText()));
        });
        goBack.addActionListener((e -> {
            this.dispose();
            new WelcomePage();
        }));
        buttons.add(confirm);
        buttons.add(goBack);
        total.add(buttons);

        this.add(total);
        this.setVisible(true);

    }
}
