package LibrarySystem.model;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class DataModel {
    private List<Appointment> appointments = new ArrayList<>();
    private List<Counselor> counselors = new ArrayList<>();
    private List<Feedback> feedbacks = new ArrayList<>();

    public DefaultTableModel getAppointmentTableModel() {
        String[] columns = {"ID", "Student", "Counselor", "Date", "Time", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0); // Explicitly set columns
        for (Appointment app : appointments) {
            model.addRow(new Object[]{app.getId(), app.getStudent(), app.getCounselor(), app.getDate(), app.getTime(), app.getStatus()});
        }
        return model;
    }

    public DefaultTableModel getCounselorTableModel() {
        String[] columns = {"ID", "Name", "Specialization", "Availability"};
        DefaultTableModel model = new DefaultTableModel(columns, 0); // Explicitly set columns
        for (Counselor counselor : counselors) {
            model.addRow(new Object[]{counselor.getId(), counselor.getName(), counselor.getSpecialization(), counselor.getAvailability()});
        }
        return model;
    }

    public DefaultTableModel getFeedbackTableModel() {
        String[] columns = {"ID", "Student", "Rating", "Comments"};
        DefaultTableModel model = new DefaultTableModel(columns, 0); // Explicitly set columns
        for (Feedback feedback : feedbacks) {
            model.addRow(new Object[]{feedback.getId(), feedback.getStudent(), feedback.getRating(), feedback.getComments()});
        }
        return model;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void updateAppointment(int id, Appointment updatedAppointment) {
        appointments.set(id - 1, updatedAppointment); // Simple update by ID (adjust as needed)
    }

    public void removeAppointment(int id) {
        appointments.removeIf(app -> app.getId() == id);
    }

    public void addCounselor(Counselor counselor) {
        counselors.add(counselor);
    }

    public void updateCounselor(int id, Counselor updatedCounselor) {
        counselors.set(id - 1, updatedCounselor); // Simple update by ID
    }

    public void removeCounselor(int id) {
        counselors.removeIf(coun -> coun.getId() == id);
    }

    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }

    public void updateFeedback(int id, Feedback updatedFeedback) {
        feedbacks.set(id - 1, updatedFeedback); // Simple update by ID
    }

    public void removeFeedback(int id) {
        feedbacks.removeIf(fb -> fb.getId() == id);
    }

    public void initializeSampleData() {
        appointments.add(new Appointment(1, "John Doe", "Dr. Smith", "2025-07-20", "10:00", "Scheduled"));
        counselors.add(new Counselor(1, "Dr. Smith", "Career Counseling", "Mon-Fri, 9-5"));
        feedbacks.add(new Feedback(1, "Jane Doe", 4, "Great session!"));
    }

    public List<Counselor> getCounselors() {
        return new ArrayList<>(counselors); // Return a copy to prevent external modification
    }
}