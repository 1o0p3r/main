package seedu.whatnow.model.task;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import java.util.Date;

import org.hamcrest.generator.qdox.parser.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TaskDate {
	public static final String MESSAGE_NAME_CONSTRAINTS = "Task Date should be represented as one of the followings:"
			+ "dd/mm/yy\n" + "day month year\n" + "today\n" + "tomorrow\n";
	public static final String DATE_ALPHA_VALIDATION_REGEX = ".+";	//To be updated
	public static final String DATE_NUM_VALIDATION_REGEX = "\\d{2}/\\d{2}/\\d{4}"; //To be updated
	public static final String DATE_NUM_FORMAT = "dd/MM/yyyy";
	public final String fullDate;

	/*
	 * Validates given date
	 *
	 * @throw IllegalValueException if given date is invalid
	 */
	public TaskDate(String date) throws IllegalValueException, java.text.ParseException {
		assert date != null;
		date = date.trim();
		if(!isValidDate(date)) {
			throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
		}
		if(date.equals("today")) {
			DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_FORMAT);
			Calendar cal = Calendar.getInstance();
			date = dateFormat.format(cal.getTime());
		}
		else if(date.equals("tomorrow")) {
			DateFormat dateFormat2 = new SimpleDateFormat(DATE_NUM_FORMAT);
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 1);
			date = dateFormat2.format(cal2.getTime());
		}
		fullDate = date;
	}

	public boolean isValidDate(String test) throws java.text.ParseException {
		if(test.equals("today") || test.equals("tomorrow")) {
			return true;
		}
		if(test.matches(DATE_NUM_VALIDATION_REGEX)) {
			Date tempDate = null;
			try {
				DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_FORMAT);
				tempDate = dateFormat.parse(test);
				if(!test.equals(dateFormat.format(DATE_NUM_FORMAT))) {
					tempDate = null;
				}
			} catch(ParseException ex) {
				ex.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_FORMAT);
			Calendar todayCal = Calendar.getInstance();
			Date todayDate = todayCal.getTime();
			if(todayDate.compareTo(tempDate) > 0) {
				return false;
			}
			else 
			return tempDate != null;
		}
		else if(test.matches(DATE_NUM_VALIDATION_REGEX)){
			return false;
		}
		else
			return false;
	}
	@Override
	public String toString() {
		return fullDate;
	}
	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Name // instanceof handles nulls
						&& this.fullDate.equals(((Name) other).fullName)); // state check
	}

	@Override
	public int hashCode() {
		return fullDate.hashCode();
	}

}