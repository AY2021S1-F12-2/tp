package seedu.studybananas.ui;

import static seedu.studybananas.ui.util.ScheduleUiUtil.toAmPmTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.studybananas.commons.core.index.Index;
import seedu.studybananas.logic.Logic;
import seedu.studybananas.logic.commands.Command;
import seedu.studybananas.logic.commands.commandresults.CommandResult;
import seedu.studybananas.logic.commands.commandresults.QuizCommandResult;
import seedu.studybananas.logic.commands.exceptions.CommandException;
import seedu.studybananas.logic.commands.quizcommands.StartCommand;
import seedu.studybananas.logic.parser.exceptions.ParseException;
import seedu.studybananas.model.flashcard.FlashcardSet;
import seedu.studybananas.model.task.Task;
import seedu.studybananas.ui.util.SingletonCommandResultState;
import seedu.studybananas.ui.util.SingletonUiState;
import seedu.studybananas.ui.util.UiStateType;

public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskListCard.fxml";
    private static final String[] BACKGROUND_COLOR = new String[]{
        "#ff6666", //red
        "#3366ff;", //blue
        "#cc66ff", //purple
    };
    public final Task task;

    @FXML
    private Label id;
    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label duration;

    private SingletonCommandResultState commandResultState;
    private SingletonUiState uiState;
    private Logic logic;
    private Command quiz;

    /**
     * Creates a {@code TaskCode} with the given {@code Task} and index to display.
     */
    public TaskCard(Task task, int displayedIndex, Logic logic) {
        super(FXML);
        this.task = task;
        this.logic = logic;
        cardPane.setStyle("-fx-background-color: " + BACKGROUND_COLOR[displayedIndex % 3]);
        id.setText(String.valueOf(displayedIndex));
        title.setText(task.getTitle().title);
        date.setText(task.getDateTime().map(time -> time.getUiFormatDate()).orElse(""));
        time.setText(task.getDateTime().map(time -> toAmPmTime(time.getStandardFormatTime())).orElse(""));
        duration.setText(task.getDuration().map(dur -> Integer.toString(dur.duration)).orElse(""));

        //set up description
        String descriptionStr = task.getDescription().map(des-> des.description).orElse("");
        handleDescription(descriptionStr);

        // observe the states
        commandResultState = SingletonCommandResultState.getInstance();
        uiState = SingletonUiState.getInstance();
    }

    @FXML
    private void handleMouseClicked() {
        try {
            CommandResult commandResult = logic.execute(this.quiz);
            commandResultState.updateCommandResult(commandResult);
            uiState.updateState(UiStateType.QUIZ);
        } catch (CommandException e) {
            return;
        }
    }

    private void handleDescription(String description) {
        try {
            Command command = logic.parse(description);
            if (command instanceof StartCommand) {
                StartCommand quizStartCommand = (StartCommand) command;
                FlashcardSet flashcardSet = logic.getFlashcardSetFromIndex(quizStartCommand.getQuizIndex());
                // can create another fxml!
                this.description.setStyle("-fx-background-color: #33cccc;"
                        + "-fx-background-radius: 5;"
                        + "-fx-font-weight: bold;"
                        + "-fx-font-family: Arial;");
                this.description.setText("Quiz: " + flashcardSet.getFlashcardSetName());
                this.quiz = command;
                return;
            }
            this.description.setText(description);
        } catch (ParseException | IndexOutOfBoundsException e) {
            this.description.setText(description);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
