package LibrarySystem.model;

public class Appointment {
    private int id;
    private String student;
    private String counselor;
    private String date;
    private String time;
    private String status;

    public Appointment(int id, String student, String counselor, String date, String time, String status) {
        this.id = id;
        this.student = student;
        this.counselor = counselor;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public Appointment(String student, String counselor, String date, String time, String status) {
        this(0, student, counselor, date, time, status);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getCounselor() {
        return counselor;
    }

    public void setCounselor(String counselor) {
        this.counselor = counselor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
