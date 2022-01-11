package ArrangementSystem;

import java.util.Objects;

public class Doctor {
    private String name;
    private static int nextId = 0;
    private int doctor_id;

    public Doctor(String name){
        this.name = name;
        this.doctor_id = nextId;
        nextId++;
    }

    public Doctor(String name, int id){
        this.doctor_id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return doctor_id == doctor.doctor_id && Objects.equals(name, doctor.name);
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public String getName() {
        return name;
    }
}
