package ArrangementSystem;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JFrame {

    public WelcomePage(){
        this.setSize(500, 300);
        JPanel welcomePanel = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton log_in = new JButton("Log in");
        log_in.addActionListener(e -> {
            WelcomePage.this.dispose();
            new LogInPage();});
        JButton newPatient = new JButton(("New Patient"));
        newPatient.addActionListener(e -> {
            WelcomePage.this.dispose();
            new CreatPatient();
        });
        welcomePanel.add(log_in);
        welcomePanel.add(newPatient);
        this.add(welcomePanel,BorderLayout.CENTER);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        WelcomePage test = new WelcomePage();
        test.setVisible(true);
    }
}
