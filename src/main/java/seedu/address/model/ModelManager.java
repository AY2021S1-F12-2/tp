package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardSet;
import seedu.address.model.flashcard.Question;
import seedu.address.model.person.Person;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.systemlevelmodel.AddressBook;
import seedu.address.model.systemlevelmodel.FlashcardBank;
import seedu.address.model.systemlevelmodel.ReadOnlyAddressBook;
import seedu.address.model.systemlevelmodel.ReadOnlyFlashcardBank;
import seedu.address.model.systemlevelmodel.ReadOnlySchedule;
import seedu.address.model.systemlevelmodel.ReadOnlyUserPrefs;
import seedu.address.model.systemlevelmodel.Schedule;
import seedu.address.model.systemlevelmodel.UserPrefs;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBookModelManager addressBookModelManager;
    private final ScheduleModelManager scheduleModelManager;
    private final FlashcardModelManager flashcardModelManager;
    private final UserPrefs userPrefs;
    private final Map<Integer, Quiz> quizRecords = new HashMap<>();
    private Quiz quiz;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlySchedule schedule, ReadOnlyFlashcardBank flashcardBank) {
        super();
        requireAllNonNull(addressBook, schedule, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " , user prefs "
                + userPrefs + " , and schedule: " + schedule);

        addressBookModelManager = new AddressBookModelManager(addressBook);
        scheduleModelManager = new ScheduleModelManager(schedule);
        flashcardModelManager = new FlashcardModelManager(flashcardBank);
        this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new Schedule(), new FlashcardBank());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getScheduleFilePath() {
        return userPrefs.getScheduleFilePath();
    }

    @Override
    public void setScheduleFilePath(Path scheduleFilePath) {
        requireNonNull(scheduleFilePath);
        userPrefs.setScheduleFilePath(scheduleFilePath);
    }

    @Override
    public Path getFlashcardBankFilePath() {
        return userPrefs.getFlashcardBankFilePath();
    }

    @Override
    public void setFlashcardBankFilePath(Path flashcardBankFilePath) {
        requireNonNull(flashcardBankFilePath);
        userPrefs.setFlashcardBankFilePath(flashcardBankFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBookModelManager.setAddressBook(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return this.addressBookModelManager.getAddressBook();
    }

    @Override
    public boolean hasPerson(Person person) {
        return this.addressBookModelManager.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        this.addressBookModelManager.deletePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        this.addressBookModelManager.addPerson(person);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        this.addressBookModelManager.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return this.addressBookModelManager.getFilteredPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        this.addressBookModelManager.updateFilteredPersonList(predicate);
    }

    //=========== Schedule =================================================================================

    @Override
    public void setSchedule(ReadOnlySchedule schedule) {
        this.scheduleModelManager.setSchedule(schedule);
    }

    @Override
    public ReadOnlySchedule getSchedule() {
        return this.scheduleModelManager.getSchedule();
    }

    @Override
    public boolean hasTask(Task task) {
        return this.scheduleModelManager.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        this.scheduleModelManager.deleteTask(target);
    }

    @Override
    public void addTask(Task task) {
        this.scheduleModelManager.addTask(task);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        scheduleModelManager.setTask(target, editedTask);
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return scheduleModelManager.getFilteredTaskList();
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        this.scheduleModelManager.updateFilteredTaskList(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
                && addressBookModelManager.equals(other.addressBookModelManager)
                && scheduleModelManager.equals(other.scheduleModelManager)
                && flashcardModelManager.equals(other.flashcardModelManager);
    }

    //=========== Quiz =============================================================
    @Override
    public Question start(Quiz quiz) {
        this.quiz = quiz;
        return getQuestion();
    }

    public boolean hasStarted() {
        return this.quiz != null;
    }

    @Override
    public void tallyScore(boolean isCorrect) {
        this.quiz.setPointsScored(isCorrect);
    }

    @Override
    public Question getQuestion() {
        return this.quiz.getQuestion();
    }

    @Override
    public Answer getAnswer() {
        return this.quiz.getAnswer();
    }

    @Override
    public double stopQuiz() {
        double score = this.quiz.getPercentageScore();
        quizRecords.put(quiz.getFlashcardSetIndex(), quiz);
        this.quiz = null;
        return score;
    }

    @Override
    public String getQuizRecords(int index) {
        return this.quizRecords.get(index).toString();
    }

    @Override
    public void setFlashcardBank(ReadOnlyFlashcardBank flashcardBank) {
        flashcardModelManager.setFlashcardBank(flashcardBank);
    }

    @Override
    public ReadOnlyFlashcardBank getFlashcardBank() {
        return flashcardModelManager.getFlashcardBank();
    }

    @Override
    public FlashcardSet getFlashcardSet(Index index) {
        return flashcardModelManager.getFlashcardSet(index);
    }

    @Override
    public boolean hasFlashcardSet(FlashcardSet flashcardSet) {
        return flashcardModelManager.hasFlashcardSet(flashcardSet);
    }

    @Override
    public void deleteFlashcardSet(FlashcardSet target) {
        flashcardModelManager.deleteFlashcardSet(target);
    }

    @Override
    public Flashcard getFlashcard(FlashcardSet flashcardSet, Index flashcardIndex) {
        return flashcardModelManager.getFlashcard(flashcardSet, flashcardIndex);
    }

    @Override
    public void setFlashcard(FlashcardSet flashcardSet, Flashcard target, Flashcard editedFlashcard) {
        flashcardModelManager.setFlashcard(flashcardSet, target, editedFlashcard);
    }

    @Override
    public boolean hasFlashcard(FlashcardSet flashcardSet, Flashcard flashcard) {
        return flashcardModelManager.hasFlashcard(flashcardSet, flashcard);
    }

    @Override
    public void addFlashcard(FlashcardSet flashcardSet, Flashcard flashcard) {
        flashcardModelManager.addFlashcard(flashcardSet, flashcard);
    }

    @Override
    public void deleteFlashcard(FlashcardSet flashcardSet, Index flashcardIndex) {
        flashcardModelManager.deleteFlashcard(flashcardSet, flashcardIndex);
    }

    @Override
    public void addFlashcardSet(FlashcardSet flashcardSet) {
        flashcardModelManager.addFlashcardSet(flashcardSet);
    }

    @Override
    public void setFlashcardSet(FlashcardSet target, FlashcardSet editedFlashcardSet) {
        flashcardModelManager.setFlashcardSet(target, editedFlashcardSet);
    }

    @Override
    public ObservableList<FlashcardSet> getFilteredFlashcardSetList() {
        return flashcardModelManager.getFilteredFlashcardSetList();
    }

    @Override
    public void updateFilteredFlashcardSetList(Predicate<FlashcardSet> predicate) {
        flashcardModelManager.updateFilteredFlashcardSetList(predicate);
    }

    @Override
    public FlashcardSet getFlashcardSetToView() {
        return flashcardModelManager.getFlashcardSetToView();
    }

    @Override
    public void setFlashcardSetToView(FlashcardSet flashcardSet) {
        flashcardModelManager.setFlashcardSetToView(flashcardSet);
    }
}
