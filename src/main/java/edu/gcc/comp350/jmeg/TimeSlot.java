package edu.gcc.comp350.jmeg;

public class TimeSlot {
    private boolean onMonday;
    private boolean onTuesday;
    private boolean onWednesday;
    private boolean onThursday;
    private boolean onFriday;
    private boolean inPerson;

    int beginTimeCode;
    int endTimeCode;

    public boolean isOnMonday() {
        return onMonday;
    }

    public void setOnMonday(boolean onMonday) {
        this.onMonday = onMonday;
    }

    public boolean isOnTuesday() {
        return onTuesday;
    }

    public void setOnTuesday(boolean onTuesday) {
        this.onTuesday = onTuesday;
    }

    public boolean isOnWednesday() {
        return onWednesday;
    }

    public void setOnWednesday(boolean onWednesday) {
        this.onWednesday = onWednesday;
    }

    public boolean isOnThursday() {
        return onThursday;
    }

    public void setOnThursday(boolean onThursday) {
        this.onThursday = onThursday;
    }

    public boolean isOnFriday() {
        return onFriday;
    }

    public void setOnFriday(boolean onFriday) {
        this.onFriday = onFriday;
    }

    public boolean isInPerson() {
        return inPerson;
    }

    public void setInPerson(boolean inPerson) {
        this.inPerson = inPerson;
    }

    public int getBeginTimeCode() {
        return beginTimeCode;
    }

    public void setBeginTimeCode(int beginTimeCodes) {
        this.beginTimeCode = beginTimeCodes;
    }

    public int getEndTimeCode() {
        return endTimeCode;
    }

    public void setEndTimeCode(int endTimeCodes) {
        this.endTimeCode = endTimeCodes;
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
