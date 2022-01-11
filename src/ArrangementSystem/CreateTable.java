package ArrangementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class CreateTable {
    private Connection connection;
public CreateTable(){};
public void setUp()
    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project","root","19980807.m");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE doctor (\n" +
                    "    doctor_id DECIMAL(22) NOT NULL COMMENT 'THE UNIQUE ID FOR EACH DOCTOR',\n" +
                    "    name      VARCHAR(20) NOT NULL COMMENT 'THE NAME OF THE DOCTOR'\n" +
                    ");\n");
            statement.execute("CREATE TABLE patient (\n" +
                    "    patient_id    DECIMAL(22) NOT NULL COMMENT 'THE UNIQUE ID FOR EACH PATIENT',\n" +
                    "    patient_lname VARCHAR(20) NOT NULL COMMENT 'THE LAST NAME OF THE PATIENT',\n" +
                    "    gender        CHAR(1) COMMENT 'THE GENDER OF THE PATIENT:\n" +
                    "M --> MALE\n" +
                    "F -->FEMALE',\n" +
                    "    phone_number  BIGINT NOT NULL COMMENT 'THE PHONE NUMBER FOR THE PATIENT'\n" +
                    ");");
            statement.execute("CREATE TABLE slot_doctor (\n" +
                    "    room_no    SMALLINT NOT NULL COMMENT 'THE ROOM NUMBER FOR THE DOCTOR WORKING IN A SPECIFIC TIME SLOT',\n" +
                    "    slot_no    TINYINT NOT NULL,\n" +
                    "    doctor_id  DECIMAL(22) NOT NULL,\n" +
                    "    patient_id DECIMAL(22)\n" +
                    ");");
            statement.execute("CREATE TABLE week_slot (\n" +
                    "    slot_no     TINYINT NOT NULL COMMENT '1 From Monday 8:00 \n" +
                    "last to Sunday 17:00',\n" +
                    "    day_of_week TINYINT NOT NULL COMMENT '1 --> Monday\n" +
                    "2 --> Tuesday\n" +
                    "3 --> Wensday\n" +
                    "4 --> Thursday\n" +
                    "5 --> Friday\n" +
                    "6 --> Saturday\n" +
                    "0 --> Sunday',\n" +
                    "    start_time  SMALLINT NOT NULL,\n" +
                    "    end_time    SMALLINT NOT NULL\n" +
                    ");");

            statement.execute("ALTER TABLE doctor ADD CONSTRAINT doctor_pk PRIMARY KEY ( doctor_id );");
            statement.execute("ALTER TABLE patient ADD CONSTRAINT patient_pk PRIMARY KEY ( patient_id );");
            statement.execute("ALTER TABLE slot_doctor ADD CONSTRAINT slot_doctor_pk PRIMARY KEY ( slot_no,doctor_id );");
            statement.execute("ALTER TABLE week_slot ADD CONSTRAINT week_slot_pk PRIMARY KEY ( slot_no );");
            statement.execute("CREATE INDEX idx_name ON DOCTOR(NAME);");
            statement.execute("ALTER TABLE slot_doctor\n" +
                    "    ADD CONSTRAINT slot_doctor_doctor_fk FOREIGN KEY ( doctor_id )\n" +
                    "        REFERENCES doctor ( doctor_id );");
            statement.execute("ALTER TABLE slot_doctor\n" +
                    "    ADD CONSTRAINT slot_doctor_patient_fk FOREIGN KEY ( patient_id )\n" +
                    "        REFERENCES patient ( patient_id );");
            statement.execute("ALTER TABLE slot_doctor\n" +
                    "    ADD CONSTRAINT slot_doctor_week_slot_fk FOREIGN KEY ( slot_no )\n" +
                    "        REFERENCES week_slot ( slot_no );");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertDoctor(Doctor d){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project","root","19980807.m");
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into doctor(doctor_id, name) values ("+d.getDoctor_id()+",'"+d.getName()+"');");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void insertDoctorTime(Doctor doctor,int[] time){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project","root","19980807.m");
            Statement statement = connection.createStatement();
            for(int i=0; i< time.length;i++){
                statement.executeUpdate("insert into slot_doctor(room_no,slot_no,doctor_id,patient_id) values(102,"+time[i]+","+doctor.getDoctor_id()+","+"null);");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void setTimeWeek(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project","root","19980807.m");
            Statement statement = connection.createStatement();
            int no =1;
            int[] time = new int[]{800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700};
            for(int i =0; i<8; i++){
                for (int j=0;j<7;j++) {
                    int startSlot = no/7;
                    if(no%7 ==0){
                        startSlot -=1;
                    }
                    int startTime;
                    int endTime;
                    if(startSlot<4){
                        startTime = time[startSlot];
                        endTime = time[startSlot+1];
                    }
                    else{
                        startTime = time[startSlot+1];
                        endTime = time[startSlot+2];
                    }

                    statement.executeUpdate("insert into week_slot(slot_no,day_of_week,start_time,end_time) values ("+no+","+no%7+","+startTime+","+endTime+")");
                    no+=1;
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void main(String[] args) {
        CreateTable c = new CreateTable();
        c.setUp();
        c.setTimeWeek();
        ArrayList<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Grace"));
        doctors.add(new Doctor("Amy"));
        doctors.add(new Doctor("John"));
        doctors.add(new Doctor("Jackie"));
        doctors.add(new Doctor("Aurora"));
        doctors.add(new Doctor("Harris"));
        doctors.add(new Doctor("Grace"));
        doctors.add(new Doctor("Smith"));
        doctors.add(new Doctor("Ross"));
        doctors.add(new Doctor("Joey"));
        doctors.add(new Doctor("Smith"));
        doctors.add(new Doctor("Potter"));
        doctors.add(new Doctor("Harry"));
        doctors.add(new Doctor("Potter"));
        doctors.add(new Doctor("Smith"));

        for(int i=0;i<doctors.size();i++){
            c.insertDoctor(doctors.get(i));
            Random r = new Random();
            Random time = new Random();
            int[] timeSlots = new int[time.nextInt(3)+1];
            for(int j=0; j< timeSlots.length;j++){
                timeSlots[j] = r.nextInt(55)+1;
            }
            c.insertDoctorTime(doctors.get(i),timeSlots);
        }



}
}
