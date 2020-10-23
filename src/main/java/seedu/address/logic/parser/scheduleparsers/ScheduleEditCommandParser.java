package seedu.address.logic.parser.scheduleparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.parserutils.ParserUtil.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedulecommands.ScheduleEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.parserutils.ParserUtil;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Duration;
import seedu.address.model.task.Title;

import java.util.Optional;

public class ScheduleEditCommandParser implements Parser<ScheduleEditCommand> {
    private static int EXPECTED_PART_OF_INPUT = 3;

    private String getIndexFromInput(String input) throws ParseException {
        String[] splittedPartInput = input.split(" ", EXPECTED_PART_OF_INPUT);
        if (splittedPartInput.length < EXPECTED_PART_OF_INPUT) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleEditCommand.MESSAGE_USAGE));
        }
        return splittedPartInput[1];
    }

    private String getEditInfo(String input) throws ParseException {
        String[] splittedPartInput = input.split(" ", EXPECTED_PART_OF_INPUT);
        String emptySpace = " ";
        if (splittedPartInput.length < EXPECTED_PART_OF_INPUT) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleEditCommand.MESSAGE_USAGE));
        }
        // add back the empty space to conform to the format of ArgumentTokenizer#tokenize()
        return emptySpace + splittedPartInput[2];
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleEditCommand
     * and returns a ScheduleEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleEditCommand parse(String args) throws ParseException {
        try {
            String indexString = getIndexFromInput(args);
            String editInfoString = getEditInfo(args);
            Index index = ParserUtil.parseIndex(indexString);
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(
                            editInfoString, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_TIME, PREFIX_DURATION);

            if ((!arePrefixesPresent(argMultimap, PREFIX_TITLE)
                    && !arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)
                    && !arePrefixesPresent(argMultimap, PREFIX_TIME)
                    && !arePrefixesPresent(argMultimap, PREFIX_DURATION))
                    ) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleEditCommand.MESSAGE_USAGE));
            }

            Title title = argMultimap.getValue(PREFIX_TITLE).map(x -> {
                try {
                    return ParserUtil.parseTitle(x);
                } catch (ParseException e) {
                    return null;
                }
            }).orElse(null);
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).orElse(null));
            DateTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).orElse(null));
            Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).orElse(null));

            return new ScheduleEditCommand(index, title, description, time, duration);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleEditCommand.MESSAGE_USAGE), pe);
        }
    }
}