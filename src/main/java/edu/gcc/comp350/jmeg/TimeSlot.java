package edu.gcc.comp350.jmeg;

public class TimeSlot {
    private boolean onMonday;
    private boolean onTuesday;
    private boolean onWednesday;
    private boolean onThursday;
    private boolean onFriday;

    int beginTimeCode;
    int endTimeCode;

    public boolean isOnMonday() {
        return onMonday;
    }

    public boolean isOnTuesday() {
        return onTuesday;
    }

    public boolean isOnWednesday() {
        return onWednesday;
    }

    public boolean isOnThursday() {
        return onThursday;
    }

    public boolean isOnFriday() {
        return onFriday;
    }

    public boolean isInPerson() {
        return onMonday || onTuesday || onWednesday || onThursday || onFriday;
    }

    public int getBeginTimeCode() {
        return beginTimeCode;
    }

    public int getEndTimeCode() {
        return endTimeCode;
    }

    public TimeSlot (Course c) {
        onMonday = c.getMonday_cde().equals("M");
        onTuesday = c.getTuesday_cde().equals("T");
        onWednesday = c.getWednesday_cde().equals("W");
        onThursday = c.getThursday_cde().equals("R");
        onFriday = c.getFriday_cde().equals("F");
        beginTimeCode = getCode(c.getBegin_tim());
        endTimeCode = getCode(c.getEnd_tim());
    }

    /**
     * Constructor used for testing only
     */
    public TimeSlot() {

    }

    /**
     * Converts string formatted as HH:MM:SS to number
     * multiplies HH by 60 then add minutes
     * @param timeString string to convert
     * @return corresponding time
     */
    public int getCode(String timeString) {
        String[] parsed = timeString.split(":");

        int parsedInt = Integer.parseInt(parsed[0]);
        if (timeString.contains("PM") && !timeString.contains("12")) {
            //All PM times except Noon
            parsedInt = convertToMilitary(parsedInt);
        }

        if (timeString.contains("12") && !timeString.contains("PM")) {
            //Catch 12AM classes
            parsedInt = convertToMilitary(parsedInt);
        }

        //Convert hours to minutes and add minutes
        parsedInt *= 60;
        parsedInt += Integer.parseInt(parsed[1].substring(0,2));

        return parsedInt;
    }

    /**
     * Converts hours to military time
     * @param num num to convert
     * @return num + 12
     */
    private int convertToMilitary(int num) {
        return num + 12;
    }

    /**
     * Checks timeslots for day overlaps
     * Used in search to check if there are day overlaps
     * @param t1 slot 1
     * @param t2 slot 2
     * @return true if on same day, false otherwise
     */
    public static boolean dayOverlap(TimeSlot t1, TimeSlot t2) {
        return t1.isOnMonday() == t2.isOnMonday()
                || t1.isOnTuesday() == t2.isOnTuesday()
                || t1.isOnWednesday() == t2.isOnWednesday()
                || t1.isOnThursday() == t2. isOnThursday()
                || t1.isOnFriday() == t2.isOnFriday();
    }
}
