package seedu.whatnow.model.task;

import seedu.whatnow.commons.exceptions.IllegalValueException;

public class Date {
	public static final String MESSAGE_NAME_CONSTRAINTS = "Task Date should be represented as one of the followings:"
			+ "dd/mm/yy\n" + "day month year\n" + "today\n" + "tomorrow\n";
    public static final String DATE_NUM_VALIDATION_REDEX = ".+";	//To be updated
    public static final String DATE_AlPHA_VALIDATION_REDEX = "+."; //To be updated
    public final String fullDate;
    
    /*
     * Validates given date
     *
     * @throw IllegalValueException if given date is invalid
     */
    public Date(String date) throws IllegalValueException {
    	assert date != null;
    	date = date.trim();
    	if(!isValidDate(date)) {
    		throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
    	}
    	this.fullDate = date;
    }
    
    public boolean isValidDate(String test) {
    	 if(test.equals("today") || test.equals("tomorrow")) {
    		 return true;
    	 }
    	 else if(test.matches(DATE_AlPHA_VALIDATION_REDEX)) {
    		 return true;
    	 }
    	 else if(test.matches(DATE_NUM_VALIDATION_REDEX)) {
    		 return true;
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