package ArrangementSystem;

import javax.swing.*;
import java.awt.*;

public class ConfirmReservationPage extends JFrame {
    public ConfirmReservationPage(Integer id, String name, String time,Integer room, Integer patient_Id){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,300);
        JPanel panel = new JPanel(new GridLayout(3, 1));
        JLabel hint = new JLabel("confirm the following reservation:");
        hint.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel detail = new JLabel("Doctor: "+ name+" Time: "+time+" in Room: "+ room);
        detail.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(hint);
        panel.add(detail);

        JPanel buttons = new JPanel();
        JButton confirm = new JButton("Confirm");
        JButton exit = new JButton("Exit");
        Integer slotNumber = parseTime(time);

        buttons.add(confirm);
        buttons.add(exit);

        panel.add(buttons);
         this.add(panel,BorderLayout.CENTER);


        exit.addActionListener(e -> this.dispose());
        confirm.addActionListener(e ->{
            ConnectToDataBase c = new ConnectToDataBase();
            String updateSituation = c.updateData(id,slotNumber,patient_Id);
            this.dispose();
            new IsBookingSuccessful(updateSituation);
        });

        this.setVisible(true);


    }
    public Integer parseTime(String timeSlot){
            String [] t =  timeSlot.split("/");
            int multi = 1;
            switch (t[0].trim()) {
                case "Monday":
                    multi = 1;

                    break;
                case "Tuesday":
                    multi = 2;
                    break;
                case "Wednesday":
                    multi = 3;

                    break;
                case "Thursday":
                    multi = 4;

                    break;
                case "Friday":
                    multi = 5;

                    break;
                case "Saturday":
                    multi = 6;

                    break;
                case "Sunday":
                    multi = 7;

                    break;
            }
            String time = t[1].trim().substring(0,1);

        if(time.equals("8")){

            return multi;
                    }
                    else if(time.equals("9")){
                        return 7+multi;
                    }
                    else if(time.equals("10")){

            return 14+multi;
                    }
                    else if(time.equals("11")){

            return 21+multi;
                    }
                    else if(time.equals("13")){

            return 28+multi;
                    }
                    else if(time.equals("15")){

            return 35+multi;
                    }
                    else if(time.equals("16")){

            return 42+multi;
                    }
                    else {

            return 49+multi;
            }
    }

    public static void main(String[] args) {
        new ConfirmReservationPage(0,"A","Tuesday / 8:00-9:00",101,27);
    }
}
