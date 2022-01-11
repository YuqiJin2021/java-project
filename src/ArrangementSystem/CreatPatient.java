package ArrangementSystem;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CreatPatient extends JFrame {
    private String patientName;
    private String patientGender;
    private String patientPhoneNumber;
    private JTextField name;
    private JTextField phoneNumber;
    private Patient patient;



    public CreatPatient(){
        this.setSize(520,300);
        JPanel newPatient = new JPanel(new GridLayout(3, 2,20,10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel name = new JLabel("Name:");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        newPatient.add(name);
        final int FIELD_WIDTH = 20;
        JTextField textName = new JTextField(FIELD_WIDTH);
        this.name = textName;
        newPatient.add(textName);
        JLabel gender = new JLabel("Gender(optional):");
        gender.setHorizontalAlignment(SwingConstants.CENTER);
        newPatient.add(gender);
        JPanel textGender = this.createComboBox();
        newPatient.add(textGender);
        JLabel phoneNumber = new JLabel("Phone Number:");
        phoneNumber.setHorizontalAlignment(SwingConstants.CENTER);
        newPatient.add(phoneNumber);
        JTextField textPhone = new JTextField(FIELD_WIDTH);
        newPatient.add(textPhone);
        this.phoneNumber = textPhone;

        this.add(newPatient,BorderLayout.NORTH);

        JButton create = new JButton("Create");
        create.addActionListener(e ->{
            createNewPatient();
            CreatPatient.this.dispose();
            new IdGeneratePage(String.valueOf(this.patient.getPatient_id()));
        });
        JPanel panel = new JPanel();
        panel.add(create,BorderLayout.CENTER);
        this.add(panel,BorderLayout.SOUTH);
        this.setVisible(true);

    }

    private void createNewPatient(){
        this.patientName = this.name.getText();
        this.patientPhoneNumber = this.phoneNumber.getText();
        Long phone = Long.parseLong(this.patientPhoneNumber);
        this.patient = new Patient(this.patientName,this.patientGender,phone);
        ConnectToDataBase c = new ConnectToDataBase();
        c.connectionToInsert(this.patient);
    }
    private JPanel createComboBox(){
        JRadioButton maleButton = new JRadioButton("Male");
        maleButton.addActionListener(e -> this.patientGender = "M");
        JRadioButton femaleButton= new JRadioButton("Female");
        femaleButton.addActionListener(e -> this.patientGender = "F");
        JRadioButton notTellButton = new JRadioButton("Null");
        notTellButton.addActionListener(e -> this.patientGender = null);

        ButtonGroup group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);
        group.add(notTellButton);

        JPanel panel = new JPanel();
        panel.add(maleButton);
        panel.add(femaleButton);
        panel.add(notTellButton);

        return panel;
    }
}
