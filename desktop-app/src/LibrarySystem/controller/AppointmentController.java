package LibrarySystem.controller;

import LibrarySystem.model.*;

public class AppointmentController {
    private DataModel model;

    public AppointmentController(DataModel model) {
        this.model = model; // Inject DataModel instance
    }

    public void addAppointment(Appointment appointment) {
        model.addAppointment(appointment);
    }

    public void updateAppointment(int id, Appointment appointment) {
        System.out.println("Updating appointment with ID: " + id + ", Student: " + appointment.getStudent());
        model.updateAppointment(id, appointment);
    }

    public void deleteAppointment(int id) {
        model.removeAppointment(id);
    }
}