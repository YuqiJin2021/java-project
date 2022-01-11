package ArrangementSystem;

import javax.swing.*;
import java.awt.*;

public class IsBookingSuccessful extends JFrame {
    public IsBookingSuccessful(String information){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,300);
        JLabel message = new JLabel(information);
        JButton goBack = new JButton("continue reserve");
        this.add(goBack,BorderLayout.SOUTH);
        goBack.addActionListener(e ->{IsBookingSuccessful.this.dispose();});
        this.add(message, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
