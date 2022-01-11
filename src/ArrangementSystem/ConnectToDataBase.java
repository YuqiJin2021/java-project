package ArrangementSystem;
import java.sql.*;
import java.util.ArrayList;

public class ConnectToDataBase {
    public ConnectToDataBase(){};



    public void connectionToInsert(Patient patient) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "19980807.m");
            Statement statement = connection.createStatement();
            statement.execute(lockTable("patient"));
            ResultSet result =statement.executeQuery("select count(*) from patient;");
            int count =0;
            while (result.next()){
                count = result.getInt(1);
            }
            System.out.println(count);
            patient.setPatient_id(count+1);
            String sqlLine = String.format("insert into patient(patient_id,patient_lname,gender,phone_number) values (" +
                    "%s,'%s','%s',%s);",patient.getPatient_id(),patient.getName(),patient.getGender(),patient.getPhoneNumber());
            System.out.println(sqlLine);
            statement.executeUpdate(sqlLine);
            statement.execute(unlockTable());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public ArrayList<Integer> findIdByDoctorName(String name){
        ArrayList<Integer> ids = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "19980807.m");
            Statement statement = connection.createStatement();
            String sqlLine = String.format("Select doctor_id from doctor where doctor.name = '%s'",name);
            ResultSet result = statement.executeQuery(sqlLine);
            while(result.next()){
                ids.add(result.getInt("doctor_id"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ids;
    }

    public String updateData(Integer id,Integer slotNumber,Integer patient_Id){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project","root","19980807.m");
            Statement statement = connection.createStatement();
            int num = statement.executeUpdate("update slot_doctor set patient_id = "+patient_Id +" where slot_no = "+ slotNumber+" and doctor_id = "+id +" and patient_id is null;");
            if(num >0){
                return "Successful!";
            }
            return "Fail to book, Please refresh the finding information.";

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
    public String lockTable(String tableName){
        String lock = "LOCK TABLES "+tableName+" WRITE;";
        return lock;
    }
    public String unlockTable() {
        String unlock = "UNLOCK TABLE";
        return unlock;
    }




}
