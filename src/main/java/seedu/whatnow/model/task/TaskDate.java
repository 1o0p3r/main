package seedu.whatnow.model.task;

import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.text.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class checks for the validity of the user input date by checking with
 * currentDate, and checking if the date range is valid Throws its respective
 * message if the input is invalid
 */
public class TaskDate {
    // @@author A0139128A
    
    private static final Logger logger = LogsCenter.getLogger(TaskDate.class);
    
    public static final String MESSAGE_NAME_CONSTRAINTS = "Task Date should be represented as one of the followings:\n"
            + "dd/mm/yyyy\n" + "day month year\n" + "today\n" + "tomorrow\n";
    public static final String EXPIRED_TASK_DATE = "Task Date cannot be in the past!";
    public static final String INVALID_TASK_DATE_RANGE_FORMAT = "The task date range is invalid!";
    public static final String INVALID_TASK_DATE_NO_DATE = "Please input a date.";
    public static final String INVALID_TASK_DATE = "The task date is invalid.";

    public static final String DATE_ALPHA_WITH_YEAR_VALIDATION_REGEX = "([0-9]{2}+[\\w\\.])+([0-9]{4})";
    public static final String DATE_ALPHA_WITHOUT_YEAR_VALIDATION_REGEX = "([0-9]{2}+[\\w\\.])";

    public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX = "([0-9]{2}+)/([0-9]{2}+)/([0-9]{4})"; 
    public static final String DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX = "([0-9]{2})/([0-9]{2})";
    public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_REGEX = "([0-9]{1}+)/([0-9]{2}+)/([0-9]{4})";
    public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_MONTH_REGEX = "([0-9]{2}+)/([1-9]{1}+)/([0-9]{4})";
    public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_AND_MONTH_REGEX = "([0-9]{1}+)/([0-9]{1}+)/([0-9]{4})";

    public static final String DATE_NUM_SLASH_WITH_YEAR_FORMAT = "dd/MM/yyyy";

    private static final Pattern DAYS_MONDAY = Pattern.compile("((?:monday|mon))", Pattern.CASE_INSENSITIVE);
    private static final Pattern DAYS_TUESDAY = Pattern.compile("((?:tuesday|tue|tues))", Pattern.CASE_INSENSITIVE);
    private static final Pattern DAYS_WEDNESDAY = Pattern.compile("((?:wednesday|wed))", Pattern.CASE_INSENSITIVE);
    private static final Pattern DAYS_THURSDAY = Pattern.compile("((?:thursday|thur|thu))", Pattern.CASE_INSENSITIVE);
    private static final Pattern DAYS_FRIDAY = Pattern.compile("((?:friday|fri))", Pattern.CASE_INSENSITIVE);
    private static final Pattern DAYS_SATURDAY = Pattern.compile("((?:saturday|sat))", Pattern.CASE_INSENSITIVE);
    private static final Pattern DAYS_SUNDAY = Pattern.compile("((?:sunday|sun))", Pattern.CASE_INSENSITIVE);
    private static final Pattern TODAY = Pattern.compile("((?:today|tdy))", Pattern.CASE_INSENSITIVE);
    private static final Pattern TOMORROW = Pattern.compile("((?:tomorrow|tmr))", Pattern.CASE_INSENSITIVE);

    private static final Pattern DAYS_IN_FULL = Pattern
            .compile("^(monday|tuesday|wednesday|thursday|friday|saturday|sunday)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern DAYS_IN_SHORT = Pattern.compile("^(mon|tue|tues|wed|thu|thur|fri|sat|sun)$",
            Pattern.CASE_INSENSITIVE);

    private static String fullDate;
    private static String startDate;
    private static String endDate;

    //author@@ A0141021H
    private static final String JANUARY_FULL = "january";
    private static final String FEBRUARY_FULL = "february";
    private static final String MARCH_FULL = "march";
    private static final String APRIL_FULL = "april";
    private static final String MAY_FULL = "may";
    private static final String JUNE_FULL = "june";
    private static final String JULY_FULL = "july";
    private static final String AUGUST_FULL = "august";
    private static final String SEPTEMBER_FULL = "september";
    private static final String OCTOBER_FULL = "october";
    private static final String NOVEMBER_FULL = "november";
    private static final String DECEMBER_FULL = "december";

    private static final String JANUARY_SHORT = "jan";
    private static final String FEBRUARY_SHORT = "feb";
    private static final String MARCH_SHORT = "mar";
    private static final String APRIL_SHORT = "apr";
    private static final String JUNE_SHORT = "jun";
    private static final String JULY_SHORT = "jul";
    private static final String AUGUST_SHORT = "aug";
    private static final String SEPTEMBER_SHORT = "sep";
    private static final String OCTOBER_SHORT = "oct";
    private static final String NOVEMBER_SHORT = "nov";
    private static final String DECEMBER_SHORT = "dec";

    //@@author A0139772U
    private static final int DATE_COMPONENT_DAY = 0;
    private static final int DATE_COMPONENT_MONTH = 1;
    private static final int DATE_COMPONENT_YEAR = 2;
    private static final int INCREASE_DATE_BY_ONE_DAY = 1;
    private static final int INCREASE_DATE_BY_SEVEN_DAYS = 7;

    // @@author A0139128A
    /**
     * Validates given date
     *
     * @throw IllegalValueException if given date is invalid
     */
    public TaskDate(String taskDate, String startDate, String endDate) throws IllegalValueException, ParseException {
        if (taskDate == null && startDate != null && endDate != null) {
            if (TODAY.matcher(startDate).find() || TOMORROW.matcher(startDate).find()) {
                startDate = performStartDate(startDate);
            }
            if (TODAY.matcher(endDate).find() || TOMORROW.matcher(endDate).find()) {
                endDate = performEndDate(endDate);
            }
            if (!isValidDateRange(startDate, endDate)) {
                throw new IllegalValueException(INVALID_TASK_DATE_RANGE_FORMAT);
            }
        } else {
            if (!isValidDate(taskDate)) {
                throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
            }
            if (TODAY.matcher(taskDate).find()) {
                fullDate = assignTodayDate();
            } else if (TOMORROW.matcher(taskDate).find()) {
                fullDate = assignTmrDate();
            }
        }
    }

    // @@author A0139128A
    /** Assigns the appropriate today's or tomorrow date to startDate */
    public static String performStartDate(String startDate) {
        if (TODAY.matcher(startDate).find()) {
            return assignTodayDate();
        } else {
            return assignTmrDate();
        }
    }

    // @@author A0139128A
    /** Assigns the appropriate today's or tomorrow date to endDate */
    public static String performEndDate(String endDate) {
        if (TODAY.matcher(endDate).find()) {
            return assignTodayDate();
        } else {
            return assignTmrDate();
        }
    }

    // @@author A0139128A
    /** Assigns today's date to the the appropriate date required and returns it */
    public static String assignTodayDate() {
        String todayDate;
        DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        Calendar cal = Calendar.getInstance();

        todayDate = dateFormat.format(cal.getTime());
        return todayDate;
    }

    // @@author A0139128A
    /** Assigns tmr's date to the appropriate date required and returns it */
    public static String assignTmrDate() {
        String tmrDate;
        DateFormat dateFormat2 = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        Calendar cal2 = Calendar.getInstance();

        cal2.add(Calendar.DATE, INCREASE_DATE_BY_ONE_DAY);

        tmrDate = dateFormat2.format(cal2.getTime());
        return tmrDate;
    }

    // @@author A0139128A
    /**
     * @param test
     *            is a given user date input
     * @return the validity of the user date input by passing it to methods of
     *         different regex
     * @throws ParseException
     * @throws IllegalValueException
     */
    private static boolean isValidDate(String reqDate) throws ParseException, IllegalValueException {

        if (TODAY.matcher(reqDate).find() || TOMORROW.matcher(reqDate).find()) {
            return true;
        } else {
            return isValidNumDate(reqDate);
        }
    }

    /**
     * This function finds the respective regex that matches the user input and
     * sends to isValidDateRangeValidator to check if the two dates are really
     * valid
     * 
     * @param startDate
     *            is the user input startingDate
     * @param endDate
     *            is the user input endingDate
     * @return true is valid date range, else false
     * @throws ParseException
     */
    // @@author A0139128A
    private static boolean isValidDateRange(String startDate, String endDate) throws ParseException {
        return isValidDateRangeValidator(startDate, endDate);
    }

    // @@author A0139128A
    private static boolean isValidDateRangeValidator(String beforeDate, String afterDate) {
        if (beforeDate == null && afterDate == null) {
            return true;
        }
        boolean convertedFromDay = false;
        if (isDay(beforeDate) && isDay(afterDate)) {
            beforeDate = formatDayToDate(beforeDate);
            afterDate = formatDayToDate(afterDate);
            convertedFromDay = true;
        } else if (isDay(beforeDate)) {
            beforeDate = formatDayToDate(beforeDate);
            convertedFromDay = true;
        } else if (isDay(afterDate)) {
            afterDate = formatDayToDate(afterDate);
            convertedFromDay = true;
        }
        boolean validDateRange = false;
        boolean sameDate = false;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        Date beginDate = null;
        Date finishDate = null;
        try {
            beginDate = sdf.parse(beforeDate);
            finishDate = sdf.parse(afterDate);
            if (beginDate.before(finishDate)) {
                validDateRange = true;
            } else {
                if (convertedFromDay) {
                    afterDate = isBeforeEarlierThanAfter(finishDate);
                    finishDate = sdf.parse(afterDate);
                    if (beginDate.before(finishDate)) {
                        validDateRange = true;
                    }
                }
            }
            if (beginDate.equals(finishDate)) {
                sameDate = true;
            }
        } catch (ParseException e) {
            return false;
        }
        /**
         * Following is done because the default date gotten from currentDate is
         * always 0000(time) i.e. always earlier than the user inputDates
         */
        Calendar before = new GregorianCalendar();
        before = setGregorian(before, beginDate);
        beginDate = before.getTime();

        Calendar after = new GregorianCalendar();
        after = setGregorian(after, finishDate);
        finishDate = after.getTime();

        /**
         * Following checks if the user input date is invalid i.e before today's
         * date
         */
        Calendar current = new GregorianCalendar();
        current = setGregorianCurrent(current);
        Date currDate = current.getTime();

        if (currDate.compareTo(beginDate) > 0 || currDate.compareTo(finishDate) > 0) {
            return false;
        } else if (!validDateRange && !sameDate) {
            return false;
        } else {
            startDate = beforeDate;
            endDate = afterDate;
            return true;
        }
    }

    // @@author A0139128A
    /**
     * returns a week ahead of the input date when a day is entered
     */
    public static String isBeforeEarlierThanAfter(Date finishDate) {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(finishDate);
        cal.add(Calendar.DATE, INCREASE_DATE_BY_SEVEN_DAYS);

        return sdf.format(cal.getTime());
    }

    /**
     * @param test
     *            is the date input by the user
     * @param format
     *            is the type of format that the date input will be tested
     *            against with
     * @return the validity of the user input
     * @throws ParseException
     * @throws IllegalValueException
     */
    private static boolean isValidNumDate(String incDate) throws ParseException, IllegalValueException {
        Date inputDate = null;
        if (isDay(incDate)) {
            incDate = formatDayToDate(incDate);
        }
        try {
            DateFormat df = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
            df.setLenient(false);

            inputDate = df.parse(incDate);
        } catch (ParseException ex) {
            logger.warning("TaskDate.java, isValidNumDate:\n" + ex.getMessage());
            return false;
        }

        Calendar input = new GregorianCalendar();
        input = setGregorian(input, inputDate);
        inputDate = input.getTime();

        /**
         * Following checks if the user input date is invalid i.e before today's
         * date
         */
        Calendar current = new GregorianCalendar();
        current = setGregorianCurrent(current);
        Date currDate = current.getTime();

        if (currDate.compareTo(inputDate) > 0) {
            throw new IllegalValueException(EXPIRED_TASK_DATE);
        }
    
        fullDate = formatDateToStandardDate(incDate);
        return true;
    }

    // @@author A0139128A
    public static boolean isDay(String inputDay) {
        return (DAYS_MONDAY.matcher(inputDay).find() || DAYS_TUESDAY.matcher(inputDay).find()
                || DAYS_WEDNESDAY.matcher(inputDay).find() || DAYS_THURSDAY.matcher(inputDay).find()
                || DAYS_FRIDAY.matcher(inputDay).find() || DAYS_SATURDAY.matcher(inputDay).find()
                || DAYS_SUNDAY.matcher(inputDay).find());
    }

    /**
     * This method checks the validity of the month entered by the user.
     */
    public static boolean isValidMonth(String mth) {
        String month = mth.toLowerCase();
        if (month.equals(JANUARY_FULL) || month.equals(JANUARY_SHORT)) {
            return true;
        } else if (month.equals(FEBRUARY_FULL) || month.equals(FEBRUARY_SHORT)) {
            return true;
        } else if (month.equals(MARCH_FULL) || month.equals(MARCH_SHORT)) {
            return true;
        } else if (month.equals(APRIL_FULL) || month.equals(APRIL_SHORT)) {
            return true;
        } else if (month.equals(MAY_FULL)) {
            return true;
        } else if (month.equals(JUNE_FULL) || month.equals(JUNE_SHORT)) {
            return true;
        } else if (month.equals(JULY_FULL) || month.equals(JULY_SHORT)) {
            return true;
        } else if (month.equals(AUGUST_FULL) || month.equals(AUGUST_SHORT)) {
            return true;
        } else if (month.equals(SEPTEMBER_FULL) || month.equals(SEPTEMBER_SHORT)) {
            return true;
        } else if (month.equals(OCTOBER_FULL) || month.equals(OCTOBER_SHORT)) {
            return true;
        } else if (month.equals(NOVEMBER_FULL) || month.equals(NOVEMBER_SHORT)) {
            return true;
        } else if (month.equals(DECEMBER_FULL) || month.equals(DECEMBER_SHORT)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method sets the date to be of the latest time as a date always comes
     * attached with a default time and there is a need to overwrite this timing
     * to the latest so that it can be compared with the current date
     */
    // @@author A0139128A
    private static Calendar setGregorian(Calendar cal, Date reqDate) {
        cal.setTime(reqDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal;
    }

    /** Gets the current Date and set it to earliest */
    // @@author A0139128A
    private static Calendar setGregorianCurrent(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        return cal;
    }

    //@@author A0139772U
    public static String formatDateToStandardDate(String date) throws IllegalValueException, ParseException {
        Calendar today = Calendar.getInstance();
        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        String year = yearFormat.format(today.getTime());
        String[] dateComponent = new String[3];
        if (date.length() == 0) {
            throw new IllegalValueException(INVALID_TASK_DATE_NO_DATE);
        } else if (date.length() < 4) {
            throw new IllegalValueException(INVALID_TASK_DATE);
        } else if (date.contains("/")) {
            dateComponent = date.split("/");
        } else if (date.contains("-")) {
            dateComponent = date.split("-");
        } else if (date.contains(" ")) {
            dateComponent = date.split(" ");
        } else if (date.contains(".")) {
            dateComponent = date.split("\\.");
        } else {
            dateComponent[DATE_COMPONENT_DAY] = date.substring(0, 2);
            dateComponent[DATE_COMPONENT_MONTH] = date.substring(2, 4);
            dateComponent[DATE_COMPONENT_YEAR] = date.substring(4);
        }

        if (dateComponent[DATE_COMPONENT_DAY].length() < 2) {
            dateComponent[DATE_COMPONENT_DAY] = 0 + dateComponent[DATE_COMPONENT_DAY];
        }
        if (dateComponent[DATE_COMPONENT_MONTH].length() < 2) {
            dateComponent[DATE_COMPONENT_MONTH] = 0 + dateComponent[DATE_COMPONENT_MONTH];
        }
        if (dateComponent[DATE_COMPONENT_YEAR].length() < 4) {
            dateComponent[DATE_COMPONENT_YEAR].replace(dateComponent[DATE_COMPONENT_YEAR], year);
        }
        String formattedDate = dateComponent[DATE_COMPONENT_DAY] + "/" + dateComponent[DATE_COMPONENT_MONTH] + "/"
                + dateComponent[DATE_COMPONENT_YEAR];
        return formattedDate;
    }

    //@@author A0139772U
    public static String formatDayToDate(String date) {
        assert (DAYS_IN_FULL.matcher(date).find() || DAYS_IN_SHORT.matcher(date).find());
        DateFormat df = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        if (TODAY.matcher(date).find()) {
            return df.format(cal.getTime());
        } else if (TOMORROW.matcher(date).find()) {
            cal.add(Calendar.DATE, INCREASE_DATE_BY_ONE_DAY);
        } else if (DAYS_MONDAY.matcher(date).find()) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else if (DAYS_TUESDAY.matcher(date).find()) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        } else if (DAYS_WEDNESDAY.matcher(date).find()) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        } else if (DAYS_THURSDAY.matcher(date).find()) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        } else if (DAYS_FRIDAY.matcher(date).find()) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        } else if (DAYS_SATURDAY.matcher(date).find()) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        } else {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        if (cal.getTime().before(today.getTime())) {
            cal.add(Calendar.DATE, INCREASE_DATE_BY_SEVEN_DAYS);
        }
        return df.format(cal.getTime());
    }

    // @@author A0139128A
    @Override
    public String toString() {
        if (fullDate == null) {
            return startDate + " " + endDate;
        } else {
            return fullDate;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                        && this.fullDate.equals(((TaskDate) other).fullDate)); // state
        // check
    }

    /** Returns the fullDate */
    public String getDate() {
        return fullDate;
    }

    /** Returns the startDate */
    public String getStartDate() {
        return this.startDate;
    }

    /** Returns the endDate */
    public String getEndDate() {
        return this.endDate;
    }

    //@@author A0141021H
    public static boolean getIsValidDate(String date) throws ParseException, IllegalValueException{
        return isValidDate(date);
    }

    public static boolean getIsValidDateRange(String start, String end) throws ParseException {
        return isValidDateRange(start, end);
    }
}