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
        model.updateAppointment(id, appointment);
    }

    public void deleteAppointment(int id) {
        model.removeAppointment(id);
    }
}