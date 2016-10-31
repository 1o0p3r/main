//@@author A0139772U
package seedu.whatnow.model.freetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class FreePeriod {
    private ArrayList<Period> freePeriod;
    
    private static final String TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT = "h:mma";
    
    public FreePeriod() {
        freePeriod = new ArrayList<Period>();
        freePeriod.add(new Period("12:00am", "11:59pm"));
    }
    
    public ArrayList<Period> getList() {
        return freePeriod;
    }
    
    public void block(String start, String end) {
        DateFormat df = new SimpleDateFormat(TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT);
        df.setLenient(false);
        try {
            Date reqStartTime = df.parse(start);
            Date reqEndTime = df.parse(end);
            Date freeSlotStartTime;
            Date freeSlotEndTime;
            for (int i = 0; i < freePeriod.size(); i++) {
                Period curr = freePeriod.get(i);
                freeSlotStartTime = df.parse(curr.getStart());
                freeSlotEndTime = df.parse(curr.getEnd());
                if (isWithinThisPeriod(reqStartTime, reqEndTime, freeSlotStartTime, freeSlotEndTime)) {
                    freePeriod.remove(i);
                    freePeriod.add(new Period(curr.getStart(), start));
                    freePeriod.add(new Period(end, curr.getEnd()));
                } else if (isPartlyBeforeThisPeriod(reqStartTime, reqEndTime, freeSlotStartTime, freeSlotEndTime)) {
                    curr.setStart(df.format(reqEndTime));
                } else if (isPartlyAfterThisPeriod(reqStartTime, reqEndTime, freeSlotStartTime, freeSlotEndTime)) {
                    curr.setEnd(df.format(reqStartTime));
                } else {
                    //To do: check for more cases
                }
            }
            freePeriod.sort(new Period());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean isWithinThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime, Date freeSlotEndTime) {
        return reqStartTime.compareTo(freeSlotStartTime) >= 0  && reqEndTime.compareTo(freeSlotEndTime) <= 0;
    }
    
    private boolean isPartlyBeforeThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime, Date freeSlotEndTime) {
        return reqStartTime.compareTo(freeSlotStartTime) < 0 
                && reqEndTime.compareTo(freeSlotStartTime) > 0 
                && reqEndTime.compareTo(freeSlotEndTime) < 0;
    }
    
    private boolean isPartlyAfterThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime, Date freeSlotEndTime) {
        return reqStartTime.compareTo(freeSlotStartTime) > 0 
                && reqStartTime.compareTo(freeSlotEndTime) < 0 
                && reqEndTime.compareTo(freeSlotEndTime) > 0;
    }
    
    private boolean isBiggerThanThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime, Date freeSlotEndTime) {
        return (reqStartTime.compareTo(freeSlotStartTime) < 0 && reqEndTime.compareTo(freeSlotEndTime) >= 0) 
                || (reqStartTime.compareTo(freeSlotStartTime) <= 0 && reqEndTime.compareTo(freeSlotEndTime) >= 0);
    }
    
    @Override
    public String toString() {
        return freePeriod.toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(freePeriod);
    }
}
