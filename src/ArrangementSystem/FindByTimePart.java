package ArrangementSystem;

import java.sql.*;
import java.util.ArrayList;

public class FindByTimePart {
    private int week;
    public ArrayList<String> name ;
    public ArrayList<String> time ;
    public ArrayList<Integer> room;
    public ArrayList<Integer> idDoctor;

    public FindByTimePart(int i){
        this.week = i;
    }

    public int[] getSlotByWeek(){
        int[] re = new int[8];
        switch (this.week){
            case 1:
                re= new int[]{1, 8, 15, 22, 29, 36, 43, 50};
                break;
            case 2:
                re= new int[]{2, 9, 16, 23, 30, 37, 44, 51};
                break;
            case 3:
                re= new int[]{3, 10, 17, 24, 31, 38, 45, 52};
                break;
            case 4:
                re= new int[]{4, 11, 18, 25, 32, 39, 46, 53};
                break;
            case 5:
                re= new int[]{5, 12, 19, 26, 33, 40, 47, 54};
                break;
            case 6:
                re= new int[]{6, 13, 20, 27, 34,41, 48, 55};
                break;
            case 7:
                re= new int[]{7, 14, 21, 28, 35, 42, 49, 56};
                break;
        }
        return re;
    }
    public String getName(int id){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "19980807.m");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from doctor where doctor_id = "+id);
            while(resultSet.next()){
                return resultSet.getString("name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "Error";
    }

    public void run(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "19980807.m");
            Statement statement = connection.createStatement();
            ConnectToDataBase c = new ConnectToDataBase();
            statement.execute(" lock tables slot_doctor write");
            int[] g = getSlotByWeek();
            String range = "(";
            for(int i=0; i< g.length;i++){
                if(i == 7){
                    range = range + g[i];
                }
                else
                range = range + g[i]+",";
            }
            range +=")";
            System.out.println(range);
            ResultSet result =statement.executeQuery("select room_no,slot_no,doctor_id from slot_doctor where slot_no in "+range+ " and patient_id is null");
            ArrayList<Integer> ids = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> time = new ArrayList<>();
            ArrayList<Integer> room = new ArrayList<>();
            while (result.next()){
                ids.add(result.getInt("doctor_id"));
                name.add(this.getName(result.getInt("doctor_id")));
                time.add(FindByDoctorPart.findTimeBySlot(result.getInt("slot_no")));
                room.add(result.getInt("room_no"));
            }
            this.name = name;
            this.room = room;
            this.time = time;
            this.idDoctor = ids;
            statement.execute(c.unlockTable());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
