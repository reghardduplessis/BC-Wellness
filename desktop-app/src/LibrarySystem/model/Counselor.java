package LibrarySystem.model;

public class Counselor {
    private int id;
    private String name;
    private String specialization;
    private String availability;

    public Counselor( String name, String specialization, String availability) {
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
    }

    public Counselor(String name, String specialization, String availability) {
        this( name, specialization, availability);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
