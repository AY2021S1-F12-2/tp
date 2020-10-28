package seedu.studybananas.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.studybananas.commons.core.LogsCenter;
import seedu.studybananas.logic.Logic;
import seedu.studybananas.logic.commands.commandresults.CommandResult;
import seedu.studybananas.logic.commands.exceptions.CommandException;
import seedu.studybananas.logic.parser.exceptions.ParseException;

public class FlashcardUi extends UiPart<Region> {
    private static final String FXML = "FlashcardUi.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Logic logic;

    // Independent Ui parts residing in this Ui container
    private FlashcardSetListPanel flashcardSetListPanel;
    private ResultDisplay resultDisplay;
    private FlashcardsDisplay flashcardsDisplay;

    @javafx.fxml.FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane flashcardSetListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane flashcardsDisplayPlaceholder;

    /**
     * Constructor for ScheduleUi.
     */
    public FlashcardUi(Logic logic) {
        super(FXML);

        // Set dependencies
        this.logic = logic;

        flashcardSetListPanel = new FlashcardSetListPanel(logic.getFilteredFlashcardSetList());
        flashcardSetListPanelPlaceholder.getChildren().add(flashcardSetListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        flashcardsDisplay = new FlashcardsDisplay(logic.getFlashcardSetToView());
        flashcardsDisplayPlaceholder.getChildren().add(flashcardsDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    public FlashcardSetListPanel getFlashcardSetListPanel() {
        return flashcardSetListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.studybananas.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
