package LibrarySystem.controller;

import LibrarySystem.model.*;

public class CounselorController {
    private DataModel model;

    public CounselorController(DataModel model) {
        this.model = model; // Inject DataModel instance
    }

    public void addCounselor(Counselor counselor) {
        model.addCounselor(counselor);
    }

    public void updateCounselor(int id, Counselor counselor) {
        model.updateCounselor(id, counselor);
    }

    public void deleteCounselor(int id) {
        model.removeCounselor(id);
    }
}