package meeju;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * Represents an event task.
 * This class extends the <code>Task</code> class having from and to as additional properties.
 */
public class Event extends Task {
    private static final String TASK_ICON = "[E]";
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructor for an event task.
     * Parses the given from and to, converts it into a LocalDateTime object.
     *
     * @param taskDescription The description of the task.
     * @param from The start date and time for the task in the format "DD/MM/YYYY HHMM".
     * @param to The end date and time for the task in the format "DD/MM/YYYY HHMM".
     * @throws MeejuException If the deadline format is incorrect or if parsing the date or time fails.
     */
    public Event(String taskDescription, String from, String to) throws MeejuException {
        super(taskDescription, false);
        String[] dateAndTimeFrom = from.split(" ");
        String[] dateAndTimeTo = to.split(" ");

        String exceptionMessage = "I'm having a bit of trouble understanding the task.\n"
                + "Could you please explain it using the correct format?\n"
                + "The Correct format is -> event <desc> /from DD/MM/YYYY HHMM /to DD/MM/YYYY HHMM";

        if (dateAndTimeFrom.length != 2 || dateAndTimeTo.length != 2) {
            throw new MeejuException(exceptionMessage);
        }

        String dateRawFrom = dateAndTimeFrom[0];
        String timeRawFrom = dateAndTimeFrom[1];
        String dateRawTo = dateAndTimeTo[0];
        String timeRawTo = dateAndTimeTo[1];
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalDate fromDate;
        LocalTime fromTime;
        LocalDate toDate;
        LocalTime toTime;
        try {
            fromDate = LocalDate.parse(dateRawFrom, dateFormatter);
            fromTime = LocalTime.parse(timeRawFrom, timeFormatter);
            toDate = LocalDate.parse(dateRawTo, dateFormatter);
            toTime = LocalTime.parse(timeRawTo, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new MeejuException("Meow! Please recheck date and time you have entered \n"
                    + "The Correct format is -> event <desc> /from DD/MM/YYYY HHMM /to DD/MM/YYYY HHMM");
        }
        this.from = LocalDateTime.of(fromDate, fromTime);
        this.to = LocalDateTime.of(toDate, toTime);
    }

    /**
     * Serializes the task details into a string format suitable for storage.
     * The delimiter used is "!-".
     *
     * @return A string representing the serialized details of the task.
     */
    public String serializeDetails() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        String formattedDateFrom = this.from.toLocalDate().format(dateFormatter);
        String formattedTimeFrom = this.from.toLocalTime().format(timeFormatter);
        String formattedDateTo = this.to.toLocalDate().format(dateFormatter);
        String formattedTimeTo = this.to.toLocalTime().format(timeFormatter);
        return "E !- " + this.getIsDone() + "!- "
                + this.getTaskDescription() + "!- "
                + formattedDateFrom + " " + formattedTimeFrom + "!- "
                + formattedDateTo + " " + formattedTimeTo + "\n";
    }


    /**
     * Returns the start date and time of the task in a readable format.
     * To be used with toString method
     *
     * @return A string representing the start date and time in the format "MMM d yyyy HHmm HRS".
     */
    public String getFrom() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        String formattedDate = this.from.toLocalDate().format(dateFormatter);
        LocalTime time = this.from.toLocalTime();
        return formattedDate + " " + time + "HRS";
    }

    /**
     * Returns the end date and time of the task in a readable format.
     * To be used with toString method
     *
     * @return A string representing the end date and time in the format "MMM d yyyy HHmm HRS".
     */
    public String getTo() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        String formattedDate = this.to.toLocalDate().format(dateFormatter);
        LocalTime time = this.to.toLocalTime();
        return formattedDate + " " + time + "HRS";
    }
    @Override
    public String toString() {
        return TASK_ICON + super.toString() + " (from: " + this.getFrom()
                    + " to: " + this.getTo() + ")";
    }
}
