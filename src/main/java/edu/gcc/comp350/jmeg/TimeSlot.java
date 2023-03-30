package edu.gcc.comp350.jmeg;

public class TimeSlot {
    private boolean onMonday;
    private boolean onTuesday;
    private boolean onWednesday;
    private boolean onThursday;
    private boolean onFriday;
    private boolean inPerson;

    int[] beginTimeCodes;
    int[] endTimeCodes;

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

    public int[] getBeginTimeCodes() {
        return beginTimeCodes;
    }

    public void setBeginTimeCodes(int[] beginTimeCodes) {
        this.beginTimeCodes = beginTimeCodes;
    }

    public int[] getEndTimeCodes() {
        return endTimeCodes;
    }

    public void setEndTimeCodes(int[] endTimeCodes) {
        this.endTimeCodes = endTimeCodes;
    }

    public TimeSlot (Course c) {
        onMonday = c.getMonday_cde().equals("M");
        onTuesday = c.getTuesday_cde().equals("T");
        onWednesday = c.getWednesday_cde().equals("W");
        onThursday = c.getThursday_cde().equals("R");
        onFriday = c.getFriday_cde().equals("F");
        inPerson = onMonday || onTuesday || onWednesday || onThursday || onFriday;
        beginTimeCodes = new int[]{0, 0, 0, 0, 0};
        endTimeCodes = new int[]{0, 0, 0, 0, 0};
        getTimeCodes(c);
    }

    private void getTimeCodes(Course c) {
        if (onMonday) {
            beginTimeCodes[0] = getCode(c.getBegin_tim());
            endTimeCodes[0] = getCode(c.getEnd_tim());
        }

        if (onTuesday) {
            beginTimeCodes[1] = getCode(c.getBegin_tim());
            endTimeCodes[1] = getCode(c.getEnd_tim());
        }

        if (onWednesday) {
            beginTimeCodes[2] = getCode(c.getBegin_tim());
            endTimeCodes[2] = getCode(c.getEnd_tim());
        }

        if (onThursday) {
            beginTimeCodes[3] = getCode(c.getBegin_tim());
            endTimeCodes[3] = getCode(c.getEnd_tim());
        }

        if (onFriday) {
            beginTimeCodes[4] = getCode(c.getBegin_tim());
            endTimeCodes[4] = getCode(c.getEnd_tim());
        }
    }

    /**
     * Converts string formatted as HH:MM:SS to number
     * multiplies HH by 60 then add minutes
     * @param timeString string to convert
     * @return corresponding time
     */
    private int getCode(String timeString) {
        int parsedInt = Integer.parseInt(timeString.substring(0,timeString.length()-2));
        if (timeString.contains("PM")) {
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

}
