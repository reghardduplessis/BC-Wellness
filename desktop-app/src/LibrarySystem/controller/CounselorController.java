package LibrarySystem.controller;

import LibrarySystem.model.*;

import java.sql.SQLException;

public class CounselorController {
    private DataModel model;
    private ControllerHub controllerHub;

    public CounselorController(DataModel model, ControllerHub controllerHub) {
        this.model = model; // Inject DataModel instance
        this.controllerHub = this.controllerHub;
    }

    public void addCounselor(Counselor counselor) {
        System.out.println("CounselorController: Adding counselor - Name: " + counselor.getName());
        try {
            model.addCounselor(counselor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (controllerHub != null) {
            controllerHub.refreshCounselorDropDown();
        }
    }

    public void updateCounselor(int id, Counselor counselor) {
        System.out.println("CounselorController: Updating ID " + id + ", Name: " + counselor.getName());
        model.updateCounselor(id, counselor);
    }

    public void deleteCounselor(int id) {
        System.out.println("CounselorController: Deleting ID " + id);
        model.removeCounselor(id);
    }
}