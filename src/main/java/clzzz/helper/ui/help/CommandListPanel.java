package clzzz.helper.ui.help;

import clzzz.helper.ui.UiPart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class CommandListPanel extends UiPart<Region> {
    private static final String FXML = "help/CommandListPanel.fxml";
    private static final ObservableList<CommandCard> EMPTY_DISPLAY_ITEM_LIST = FXCollections.observableArrayList();

    @FXML
    private ListView<CommandCard> list;

    public CommandListPanel() {
        super(FXML);

        list.setItems(EMPTY_DISPLAY_ITEM_LIST);
    }
}
