package clzzz.helper.ui.help;

import clzzz.helper.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

public class CommandCard extends UiPart<Region> {
    private static final String FXML = "help/CommandCard.fxml";

    @FXML
    private Button commandButton;

    @FXML
    private TextArea description;

    public CommandCard(String command, String description) {
        super(FXML);

        commandButton.setText(command);
        this.description.setText(description);
    }
}
