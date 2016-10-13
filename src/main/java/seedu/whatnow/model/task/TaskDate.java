package seedu.whatnow.model.task;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import java.util.Date;
import java.util.Locale;

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

	public static final String DATE_ALPHA_WITH_YEAR_VALIDATION_REGEX = "([0-9]{2}+[\\w\\.]+([0-9]{4}";	//To be updated
	public static final String DATE_ALPHA_WITHOUT_YEAR_VALIDATION_REGEX = "([0-9]{2}+[\\w\\.]";

	public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX = "([0-9]{2})/([0-9]{2})/([0-9]{4})"; //"\\d{2}/\\d{2}/\\d{4}"; //To be updated
	public static final String DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX = "([0-9]{2})/([0-9]{2})";//"\\d{2}/\\d{2}";
	
	public static final String DATE_NUM_SLASH_WITH_YEAR_FORMAT = "dd/MM/yyyy";
	public static final String DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT = "dd/MM";
	
	public static final String DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT = "dd MMMM yyyy ";
	public static final String DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT = "dd MMMM";	

	
	public String fullDate;	//Is the full date that will be used and stored

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
		//Formats the date to be today's date
		if(date.equals("today")) {
			DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
			Calendar cal = Calendar.getInstance();
			date = dateFormat.format(cal.getTime());
			fullDate = date;
		}
		//Formats the date to be tomorrow's date
		else if(date.equals("tomorrow")) {
			DateFormat dateFormat2 = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 1);
			date = dateFormat2.format(cal2.getTime());
			fullDate= date;
		}

	}

	public boolean isValidDate(String test) throws java.text.ParseException {
		if(test.equals("today") || test.equals("tomorrow")) {
			return true;
		}
		if(test.matches(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX)) {
			return isValidNumDate(test, DATE_NUM_SLASH_WITH_YEAR_FORMAT);
		}
		else if(test.matches(DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX)){
			return isValidNumDate(test, DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT);
		}
		else if(test.matches(DATE_ALPHA_WITH_YEAR_VALIDATION_REGEX)){
			return isValidAlphaDate(test, DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT);
		}
		else if(test.matches(DATE_ALPHA_WITHOUT_YEAR_VALIDATION_REGEX)){
			return isValidAlphaDate(test, DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT);
		}
		else
			return false;
	}
	public boolean isValidAlphaDate(String test, String format) throws java.text.ParseException {
		Date tempDate = null;

		//Following will check if the user input date is valid in terms of numerical value i.e. 32nd november
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			tempDate = dateFormat.parse(test);
			if(!test.equals(dateFormat.format(format))) {
				tempDate = null;
			}
		} catch(ParseException ex) {
			ex.printStackTrace();
			return false;
		}
		//Following checks if the user input date is invalid i.e before today's date
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar todayCal = Calendar.getInstance();
		Date todayDate = todayCal.getTime();
		if(todayDate.compareTo(tempDate) > 0) {
			return false;
		}
		//Following ensures that the date format keyed in the user will be converted to DATE_NUM_SLASH_WITH_YEAR_FORMAT
		if(format.equals(DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT)) {
			String tempToGetMonth;
			String[] splitted = test.split("\\s+");
			tempToGetMonth = splitted[0];
			Date dateToGetMonth = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(tempToGetMonth);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateToGetMonth);
			int month = cal.get(Calendar.MONTH);
			String requiredMonth = String.valueOf(month);
			String requiredDate = splitted[0];
			requiredDate.concat(requiredMonth);
			requiredDate.concat(splitted[2]);
			fullDate = requiredDate;
			return true;
		}
		else if(format.equals(DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT)) {
			String tempToGetMonth;
			String[] splitted = test.split("\\s+");
			tempToGetMonth = splitted[0];
			Date dateToGetMonth = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(tempToGetMonth);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateToGetMonth);
			int month = cal.get(Calendar.MONTH);
			String requiredMonth = String.valueOf(month);
			int yearInt = cal.get(Calendar.YEAR);
			String year = String.valueOf(yearInt);
			String requiredDate = splitted[0];
			requiredDate.concat(year);
			return true;
		}
		else
			return false;
	}
	/**
	 * 
	 * @param test is the date input by the user
	 * @param format is the type of format that the date input will be tested against with
	 * @return the validity of the user input
	 * @throws java.text.ParseException
	 */
	public boolean isValidNumDate(String test, String format) throws java.text.ParseException {
		Date tempDate = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			tempDate = dateFormat.parse(test);
			if(!test.equals(dateFormat.format(format))) {
				tempDate = null;
			}
		} catch(ParseException ex) {
			ex.printStackTrace();
			return false;
		}

		//Following checks if the user input date is invalid i.e before today's date
		Calendar todayCal = Calendar.getInstance();
		Date todayDate = todayCal.getTime();
		if(todayDate.compareTo(tempDate) > 0) {
			return false;
		}
		//The following will ensure the date format to be DATE_NUM_SLASH_WITH_YEAR_FORMAT
		if(format.equals(DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT)) {
			Calendar now = Calendar.getInstance();
			int yearInt = now.get(Calendar.YEAR);
			String year = String.valueOf(yearInt);
			test.concat(year);
			fullDate = test;
			return true;
		}
		else {
			fullDate = test;
			return true;
		}

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
	public String getDate() {
		return fullDate;
	}
}