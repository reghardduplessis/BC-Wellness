package LibrarySystem.controller;

import LibrarySystem.model.*;

public class CounselorController {
    private DataModel model;
    private DashboardController dashboardController;

    public CounselorController(DataModel model, DashboardController dashboardController) {
        this.model = model; // Inject DataModel instance
        this.dashboardController = this.dashboardController;
    }

    public void addCounselor(Counselor counselor) {
        System.out.println("CounselorController: Adding counselor - Name: " + counselor.getName());
        model.addCounselor(counselor);
        if (dashboardController != null) {
            dashboardController.refreshCounselorDropDown();
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