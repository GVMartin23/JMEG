package edu.gcc.comp350.jmeg;

public class TimeSlot {
    private enum WeekDay {
        MON, TUES, WED, THURS, FRI
    }

    private boolean onMonday;
    private boolean onTuesday;
    private boolean onWednesday;
    private boolean onThursday;
    private boolean onFriday;
    private boolean inPerson;

    int[] timeCodes;


    public TimeSlot (Course c) {
        onMonday = c.getMonday_cde().equals("M");
        onTuesday = c.getTuesday_cde().equals("T");
        onWednesday = c.getWednesday_cde().equals("W");
        onThursday = c.getThursday_cde().equals("R");
        onFriday = c.getFriday_cde().equals("F");
        inPerson = onMonday || onTuesday || onWednesday || onThursday || onFriday;
        timeCodes = getTimeCodes(c);
    }

    private int[] getTimeCodes(Course c) {
        int[] timeCodes = {0,0,0,0,0}; //Init to all zeroes, change if has time code for specific day
        if (onMonday) {
            timeCodes[0] = getCode(c.getBegin_tim(), c.getEnd_tim());
        }

        if (onTuesday) {
            timeCodes[1] = getCode(c.getBegin_tim(), c.getEnd_tim());
        }

        if (onWednesday) {
            timeCodes[2] = getCode(c.getBegin_tim(), c.getEnd_tim());
        }

        if (onThursday) {
            timeCodes[3] = getCode(c.getBegin_tim(), c.getEnd_tim());
        }

        if (onFriday) {
            timeCodes[4] = getCode(c.getBegin_tim(), c.getEnd_tim());
        }




        return timeCodes;

    }

    private int getCode(String beginTime, String endTime) {

        return 0;
    }
}
