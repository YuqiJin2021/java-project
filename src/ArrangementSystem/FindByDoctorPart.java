package ArrangementSystem;

import java.sql.*;
import java.util.ArrayList;

public class FindByDoctorPart {
    private Doctor myLock;
    private ArrayList<String> doctorName;
    private ArrayList<String> time;
    private ArrayList<Integer> room;
    private ArrayList<Integer> ids;

    public FindByDoctorPart(Doctor doctor){
        this.myLock = doctor;
    }

    public static String findTimeBySlot(int slot){
        String[] week = new String[7];
        week[0] = "Monday";
        week[1] = "Tuesday";
        week[2] = "Wednesday";
        week[3] = "Thursday";
        week[4] = "Friday";
        week[5] = "Saturday";
        week[6] = "Sunday";
        String[] dayTime = new String[8];
        dayTime[0] = "8:00-9:00";
        dayTime[1] = "9:00-10:00";
        dayTime[2] = "10:00-11:00";
        dayTime[3] = "11:00-12:00";
        dayTime[4] = "13:00-14:00";
        dayTime[5] = "14:00-15:00";
        dayTime[6] = "15:00-16:00";
        dayTime[7] = "16:00-17:00";

        int w = slot % 7 -1;
        if(w < 0){
            w =0;
        }
        int d = slot / 7;
        if(slot%7 ==0){
            d -=1;
        }
        String time = week[w]+" / "+ dayTime[d];
        return time;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public ArrayList<Integer> getRoom() {
        return room;
    }

    public ArrayList<String> getDoctorName() {
        return doctorName;
    }

    public ArrayList<String> getTime() {
        return time;
    }


    public void run() {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "19980807.m");
                Statement statement = connection.createStatement();
                ConnectToDataBase c = new ConnectToDataBase();
                statement.execute(" lock tables slot_doctor write");
                ResultSet result =statement.executeQuery("select room_no,slot_no,doctor_id from slot_doctor where slot_doctor.doctor_id = "+myLock.getDoctor_id()+
                        " and slot_doctor.patient_id is null;");
                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> time = new ArrayList<>();
                ArrayList<Integer> room = new ArrayList<>();
                while (result.next()){
                    ids.add(this.myLock.getDoctor_id());
                    name.add(this.myLock.getName());
                    time.add(this.findTimeBySlot(result.getInt("slot_no")));
                    room.add(result.getInt("room_no"));
                }
                this.ids = ids;
                this.doctorName = name;
                this.room = room;
                this.time = time;
                statement.execute(c.unlockTable());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

    }
}
