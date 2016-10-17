package seedu.whatnow.model.task;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

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

	
	private String fullDate;	//Is the full date that will be used and stored

	/*
	 * Validates given date
	 *
	 * @throw IllegalValueException if given date is invalid
	 */
	public TaskDate(String taskDate) throws IllegalValueException, java.text.ParseException {
		assert taskDate != null;
		taskDate = taskDate.trim();
		
		if(!isValidDate(taskDate)) {
			throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
		}
		//Formats the date to be today's date
		if(taskDate.equals("today")) {
			DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
			Calendar cal = Calendar.getInstance();
			taskDate = dateFormat.format(cal.getTime());
			fullDate = taskDate;
		}
		//Formats the date to be tomorrow's date
		else if(taskDate.equals("tomorrow")) {
			DateFormat dateFormat2 = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 1);
			taskDate = dateFormat2.format(cal2.getTime());
			fullDate= taskDate;
		}

	}
	/**
	 * 
	 * @param test is a given user date input
	 * @return the validity of the user date input by passing it to methods of different regex
	 * @throws java.text.ParseException
	 */
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
	/**
	 * 
	 * @param test is the user date input
	 * @param format is the type of format the user has chosen to input
	 * @return the validity of format of the user date input and validity i.e. existence of the date input
	 * @throws java.text.ParseException
	 */
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
			Date dateToGetMonth = new SimpleDateFormat("MMMMM", Locale.ENGLISH).parse(tempToGetMonth);
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
			Date dateToGetMonth = new SimpleDateFormat("MMMMM", Locale.ENGLISH).parse(tempToGetMonth);
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
			/*
			System.out.println("The tesString is: " + test);
			System.out.println("The format is : " + format);
			DateFormat dateFormat = new SimpleDateFormat(format);
			tempDate = dateFormat.parse(test);
			if(!tempDate.equals(dateFormat.format(format))) {
				tempDate = null;
			}*/
			System.out.println();
			System.out.println("Entered isValidNumDate");
			System.out.println("test is : "+ test);
			System.out.println("format is : "+ format);
			DateFormat df = new SimpleDateFormat(format);
			df.setLenient(false);
			
			tempDate = df.parse(test);
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