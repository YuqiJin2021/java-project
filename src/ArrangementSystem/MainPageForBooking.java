package ArrangementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainPageForBooking extends JFrame {
    private String patientName;
    private int Id;
    private JLabel topMiddle;
    private JPanel topBottom;
    private JTable table;
    private JScrollPane scrollPane;
    private TableModel tableModel;

    public MainPageForBooking(String name,int Id) {
        this.patientName = name;
        this.Id = Id;
        this.setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel welcome = new JLabel("Hello! " + patientName);
        welcome.setHorizontalAlignment(SwingConstants.CENTER);

        JButton exit = new JButton("Go back to Log in");
        exit.addActionListener(e -> {
            MainPageForBooking.this.dispose();
            new WelcomePage();
        });

        JPanel leftSide = new JPanel(new GridLayout(2, 1));
        leftSide.setSize(100, 400);
        leftSide.add(welcome);
        JPanel leftMiddle = new JPanel();
        leftSide.add(leftMiddle);
        JPanel leftBottom = new JPanel();
        leftBottom.add(exit);
        leftSide.add(leftBottom);


        JPanel rightSideTop = new JPanel(new GridLayout(3, 1));
        JPanel buttons = new JPanel();
        JButton doctor = new JButton("find by doctor");
        JButton time = new JButton("find by time");
        buttons.add(doctor);
        buttons.add(time);


        this.topMiddle = new JLabel("Please select the finding type!");

        final int FIELD_WIDTH = 20;
        JPanel enterDoctor = new JPanel();
        JTextField doctorName = new JTextField(FIELD_WIDTH);
        JButton confirm = new JButton("confirm");


        JButton timeConfirm = new JButton("confirm");


        enterDoctor.add(doctorName);
        enterDoctor.add(confirm);


        JTextField year = new JTextField(4);
        JLabel slash1 = new JLabel("/");
        JLabel slash2 = new JLabel("/");

        JTextField months = new JTextField(2);
        JTextField day = new JTextField(2);
        JPanel enterTime = new JPanel(new GridLayout(1, 6));
        enterTime.add(year);
        enterTime.add(slash1);
        enterTime.add(months);
        enterTime.add(slash2);
        enterTime.add(day);
        enterTime.add(timeConfirm);
        this.add(rightSideTop,BorderLayout.NORTH);
        this.setVisible(true);

        this.topBottom = enterDoctor;
        doctor.addActionListener(e ->{
            rightSideTop.remove(this.topBottom);
            this.topBottom = enterDoctor;
            rightSideTop.add(enterDoctor);
            rightSideTop.repaint();
            this.topMiddle.setText("Please enter the doctor name:");});

        time.addActionListener(e ->{
            this.topMiddle.setText("Please enter the time(yyyy/mm/dd) within the week:");
            rightSideTop.remove(this.topBottom);
            this.topBottom = enterTime;
            rightSideTop.add(enterTime);
            rightSideTop.repaint();
        });

        rightSideTop.add(buttons);
        rightSideTop.add(this.topMiddle);
        rightSideTop.add(this.topBottom);
        JPanel rightTotal = new JPanel();
        rightTotal.add(rightSideTop,BorderLayout.CENTER);


        JScrollPane scroll = new JScrollPane();
        this.table = createTableByName(doctorName.getText());
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroll.setViewportView(this.table);
        scroll.setSize(600, 100);
        this.scrollPane = scroll;

        this.table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() ==2){
                    int r= table.getSelectedRow();
                    Integer id = Integer.parseInt(tableModel.getValueAt(r,0).toString());
                    System.out.println("The id is "+id);
                    String name = tableModel.getValueAt(r,1).toString();
                    System.out.println("The name is "+name);

                    String time = tableModel.getValueAt(r,2).toString();
                    System.out.println("The time is "+time);

                    Integer room = Integer.parseInt(tableModel.getValueAt(r,3).toString());
                    System.out.println("The room is "+room);

                    new ConfirmReservationPage(id,name,time,room,Id);
                }
            }
        });

        rightTotal.add(this.scrollPane,BorderLayout.CENTER);


        timeConfirm.addActionListener(e ->{

            int week = getWeekOfDate(Integer.parseInt(year.getText()),Integer.parseInt(months.getText()),Integer.parseInt(day.getText()));

            JTable table = createTableByTime(week);
            this.scrollPane.getViewport().remove(this.table);
            this.table = table;
            this.scrollPane.getViewport().add(this.table);
            this.table.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() ==2){
                        int r= table.getSelectedRow();
                        Integer id = Integer.parseInt(tableModel.getValueAt(r,0).toString());
                        System.out.println("The id is "+id);
                        String name = tableModel.getValueAt(r,1).toString();
                        System.out.println("The name is "+name);

                        String time = tableModel.getValueAt(r,2).toString();
                        System.out.println("The time is "+time);

                        Integer room = Integer.parseInt(tableModel.getValueAt(r,3).toString());
                        System.out.println("The room is "+room);

                        new ConfirmReservationPage(id,name,time,room,Id);
                    }
                }
            });
            this.repaint();
        });

        confirm.addActionListener(e -> {
            JTable table = createTableByName(doctorName.getText());
            this.scrollPane.getViewport().remove(this.table);
            this.table = table;
            this.scrollPane.getViewport().add(this.table);
            this.table.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {

                    if(e.getClickCount() ==2){
                        int r= table.getSelectedRow();
                        Integer id = Integer.parseInt(tableModel.getValueAt(r,0).toString());
                        System.out.println("The id is "+id);
                        String name = tableModel.getValueAt(r,1).toString();
                        System.out.println("The name is "+name);

                        String time = tableModel.getValueAt(r,2).toString();
                        System.out.println("The time is "+time);

                        Integer room = Integer.parseInt(tableModel.getValueAt(r,3).toString());
                        System.out.println("The room is "+room);

                        new ConfirmReservationPage(id,name,time,room,Id);
                    }
                }
            });
            this.repaint();
        });


        this.add(rightTotal,BorderLayout.CENTER);
        this.add(leftSide,BorderLayout.WEST);
        this.setVisible(true);

    };
    public JTable createTableByTime(int week){
        String[] columnName = {"Id","Doctor Name","Time Slot","Room"};
        ArrayList<String> n = new ArrayList<>();
        ArrayList<Integer> r = new ArrayList<>();
        ArrayList<String> t = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();

        FindByTimePart part = new FindByTimePart(week);
        part.run();

        n.addAll(part.name);
        System.out.println(n);

        r.addAll(part.room);
        System.out.println(r);

        t.addAll(part.time);
        System.out.println(t);
        ids.addAll(part.idDoctor);
        System.out.println(ids);


        Object[][] obj = new Object[n.size()][columnName.length];
        for(int i=0; i< n.size();i++){
            for(int j=0;j<columnName.length;j++){
                switch (j){
                    case 0:
                        obj[i][j] = ids.get(i);
                        break;
                    case 1:
                        obj[i][j] = n.get(i);
                        break;
                    case 2:
                        obj[i][j] = t.get(i);
                        break;
                    case 3:
                        obj[i][j] = r.get(i);
                        break;
                }
            }
        }

        TableModel tableModel = new DefaultTableModel(obj, columnName) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tableModel = tableModel;
        TableColumn column;
        JTable table = new JTable(tableModel);
        int colunms = table.getColumnCount();
        for(int i = 0; i < colunms; i++)
        {
            column = table.getColumnModel().getColumn(i);
            if(i == 2){
                column.setPreferredWidth(200);}
            else
                column.setPreferredWidth(100);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        return table;
};

    public JTable createTableByName(String name){
        String[] columnName = {"Id","Doctor Name","Time Slot","Room"};

        ConnectToDataBase c = new ConnectToDataBase();
        ArrayList<Integer> ids = c.findIdByDoctorName(name);
        ArrayList<Integer> writeIntoTableIds = new ArrayList<>();
        ArrayList<String> n = new ArrayList<>();
        ArrayList<Integer> r = new ArrayList<>();
        ArrayList<String> t = new ArrayList<>();

        for(int i =0; i < ids.size();i++){
            Doctor doctor = new Doctor(name,ids.get(i));
            FindByDoctorPart part = new FindByDoctorPart(doctor);
            part.run();

            writeIntoTableIds.addAll(part.getIds());
            n.addAll(part.getDoctorName());
            System.out.println(n);

            r.addAll(part.getRoom());
            System.out.println(r);

            t.addAll(part.getTime());
            System.out.println(t);

        }

        Object[][] obj = new Object[n.size()][columnName.length];
        for(int i=0; i< n.size();i++){
            for(int j=0;j<columnName.length;j++){
                switch (j){
                    case 0:
                        obj[i][j] = writeIntoTableIds.get(i);
                        break;
                    case 1:
                        obj[i][j] = n.get(i);
                        break;
                    case 2:
                        obj[i][j] = t.get(i);
                        break;
                    case 3:
                        obj[i][j] = r.get(i);
                        break;
                }
            }
        }

        TableModel tableModel = new DefaultTableModel(obj, columnName) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tableModel = tableModel;
        TableColumn column;
        JTable table = new JTable(tableModel);
        int colunms = table.getColumnCount();
        for(int i = 0; i < colunms; i++)
        {
            column = table.getColumnModel().getColumn(i);
            if(i == 2){
                column.setPreferredWidth(200);}
            else
                column.setPreferredWidth(100);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        return table;

    }
    public static int getWeekOfDate(int year, int months, int day) {
        String strDate = year+"-"+months+"-"+day;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int[] weekDays = {7,1,2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static void main(String[] args) {
        MainPageForBooking test = new MainPageForBooking("grace",1234);
    }

}
