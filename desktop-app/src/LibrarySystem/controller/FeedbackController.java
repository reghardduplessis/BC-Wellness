package LibrarySystem.controller;

import LibrarySystem.model.*;

public class FeedbackController {
    private DataModel model;

    public FeedbackController(DataModel model) {
        this.model = model; // Inject DataModel instance
    }

    public void addFeedback(Feedback feedback) {
        model.addFeedback(feedback);
    }

    public void updateFeedback(int id, Feedback feedback) {
        System.out.println("FeedbackController: Updating ID " + id + ", Student: " + feedback.getStudent());
        model.updateFeedback(id, feedback);
    }

    public void deleteFeedback(int id) {
        model.removeFeedback(id);
    }
}