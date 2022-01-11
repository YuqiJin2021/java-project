package ArrangementSystem;

public class Patient {
    private String name;
    private String gender;
    private int patient_id;
    private Long phoneNumber;

    public Patient(String name,String gender,Long phoneNumber){
        this.gender = gender;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }
}
