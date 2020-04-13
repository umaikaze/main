package clzzz.helper.ui;

import java.util.logging.Logger;

import clzzz.helper.commons.core.GuiSettings;
import clzzz.helper.commons.core.LogsCenter;
import clzzz.helper.commons.exceptions.IllegalValueException;
import clzzz.helper.logic.Logic;
import clzzz.helper.logic.commands.CommandResult;
import clzzz.helper.logic.commands.DisplayCommand;
import clzzz.helper.logic.commands.exceptions.CommandException;
import clzzz.helper.logic.parser.exceptions.ParseException;
import clzzz.helper.ui.calendar.CalendarPanel;
import clzzz.helper.ui.list.DisplayListPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private DisplayListPanel displayListPanel;
    private CalendarPanel calendarPanel;
    private FeedbackDisplay feedbackDisplay;
    private HelpWindow helpWindow;
    private OverallStats overallStats;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane feedbackDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        setWindowStyle();

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        displayListPanel = new DisplayListPanel(logic.getFilteredDisplayList());
        calendarPanel = new CalendarPanel(logic.getPetTracker().getSlotList());

        resultDisplayPlaceholder.getChildren().add(displayListPanel.getRoot());

        feedbackDisplay = new FeedbackDisplay();
        feedbackDisplayPlaceholder.getChildren().add(feedbackDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getPetTrackerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Changes the system being displayed.
     */
    private void handleChangeDisplay(DisplaySystemType type) throws CommandException {
        switch (type) {
        case PETS: // fallthrough
        case SCHEDULE:
            displayListPanel.updateWith(logic.getFilteredDisplayList());
            displayListPanel.collapseInformationView();
            resultDisplayPlaceholder.getChildren().set(0, displayListPanel.getRoot());
            break;
        case INVENTORY:
            displayListPanel.updateWith(logic.getFilteredDisplayList());
            displayListPanel.expandInformationView();
            resultDisplayPlaceholder.getChildren().set(0, displayListPanel.getRoot());
            break;
        case CALENDAR:
            calendarPanel.construct();
            resultDisplayPlaceholder.getChildren().set(0, calendarPanel.getRoot());
            break;
        case STATISTICS:
            resultDisplayPlaceholder.getChildren().clear();
            overallStats = new OverallStats(logic.getFilteredPetList(), logic.getFilteredSlotList(),
                    logic.getFilteredFoodCollectionList());
            resultDisplayPlaceholder.getChildren().add(overallStats.getRoot());
            break;
        case NO_CHANGE:
            handleChangeDisplay(logic.getDisplaySystemType());
            break;
        default:
            throw new CommandException(DisplayCommand.MESSAGE_INVALID_SYSTEM_TYPE);
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    private void setWindowStyle() {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
    }


    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public OverallStats getOverallStats() {
        return overallStats;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see clzzz.helper.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, IllegalValueException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            feedbackDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            handleChangeDisplay(commandResult.getDisplaySystemType());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            feedbackDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
