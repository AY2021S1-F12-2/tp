package seedu.address.logic.commands.quizcommands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class RefreshCommand extends Command<Model> {

    public static final String COMMAND_WORD = "refresh";
    public static final String MESSAGE_QUIZ_REFRESHED = "Refreshed! Here is your last quiz state:\n\n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_QUIZ_REFRESHED
                + QuizCommand.getCurrentCommandResult());
    }
}
