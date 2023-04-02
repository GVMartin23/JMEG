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
        if (timeString.contains("PM")) {
            return convertToMilitary(parsedInt);
        }

        return parsedInt + Integer.parseInt(parsed[1].substring(0, 2));
    }

    /**
     * Converts hours to military time
     * @param num num to convert
     * @return num + 12
     */
    private int convertToMilitary(int num) {
        return num + 12;
    }

}
