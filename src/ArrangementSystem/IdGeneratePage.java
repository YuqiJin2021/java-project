package ArrangementSystem;

import javax.swing.*;
import java.awt.*;

public class IdGeneratePage extends JFrame {
    public IdGeneratePage(String number) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 200);
        JPanel id = new JPanel(new GridLayout(2, 1, 20, 0));
        JLabel idShowing = new JLabel("Your unique Id number is:");
        idShowing.setHorizontalAlignment(SwingConstants.CENTER);

        id.add(idShowing);
        JLabel idNumber = new JLabel(number);
        idNumber.setHorizontalAlignment(SwingConstants.CENTER);

        id.add(idNumber);
        id.add(idNumber);
        this.add(id, BorderLayout.CENTER);

        JPanel action = new JPanel(new GridLayout(1, 2, 20, 10));
        JButton back = new JButton("go back to log in");
        back.addActionListener(e -> {
            IdGeneratePage.this.dispose();
            new WelcomePage();
        });
        action.add(back);
        JButton create = new JButton("create another patient");
        create.addActionListener(e -> {
            IdGeneratePage.this.dispose();
            new CreatPatient();
        });
        action.add(create);
        this.add(action, BorderLayout.SOUTH);
        this.setVisible(true);
    }



}