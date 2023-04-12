package edu.gcc.comp350.jmeg;

public class TimeSlot {
    private final boolean onMonday;
    private final boolean onTuesday;
    private final boolean onWednesday;
    private final boolean onThursday;
    private final boolean onFriday;
    private final boolean inPerson;

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
        return inPerson;
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
        inPerson = onMonday || onTuesday || onWednesday || onThursday || onFriday;
        beginTimeCode = 0;
        endTimeCode = 0;
        getTimeCodes(c);
    }

    private void getTimeCodes(Course c) {
        beginTimeCode = getCode(c.getBegin_tim());
        endTimeCode = getCode(c.getEnd_tim());
    }

    /**
     * Converts string formatted as HH:MM:SS to number
     * multiplies HH by 60 then add minutes
     * @param timeString string to convert
     * @return corresponding time
     */
    private int getCode(String timeString) {
        String[] parsed = timeString.split(":");

        int parsedInt = Integer.parseInt(parsed[0]);
        if (timeString.contains("PM") && !timeString.contains("12")) {
            //All PM times except Noon
            return convertToMilitary(parsedInt);
        }

        if (timeString.contains("12") && !timeString.contains("PM")) {
            //Catch 12AM classes
            return convertToMilitary(parsedInt);
        }

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
