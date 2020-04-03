package clzzz.helper.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class FeedbackDisplay extends UiPart<Region> {

    private static final String FXML = "FeedbackDisplay.fxml";

    @FXML
    private TextArea feedbackDisplay;

    public FeedbackDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        feedbackDisplay.setText(feedbackToUser);
    }

}
