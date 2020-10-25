package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_INVALID_FLASHCARDSET_DISPLAYED_INDEX =
            "The flashcard set index provided is invalid";
    public static final String MESSAGE_INVALID_FLASHCARD_INDEX =
            "The flashcard index provided is invalid";
    public static final String MESSAGE_QUIZ_HAS_STARTED = "A quiz is ongoing, "
            + "no non-quiz commands are allowed.\nKey `refresh' "
            + "to continue with quiz or 'cancel' to stop quiz.";
}
