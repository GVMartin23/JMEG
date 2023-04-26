package edu.gcc.comp350.jmeg;

public class SimpleCourse {
    private int startTime;
    private int endTime;
    private boolean onMonday;
    private boolean onTuesday;
    private boolean onWednesday;
    private boolean onThursday;
    private boolean onFriday;
    private String courseName;
    private String courseCode;

    public SimpleCourse(Course c) {
        TimeSlot slot = c.getTimeSlot();
        startTime = slot.beginTimeCode;
        endTime = slot.endTimeCode;
        onMonday = slot.isOnMonday();
        onTuesday = slot.isOnTuesday();
        onWednesday = slot.isOnWednesday();
        onThursday = slot.isOnThursday();
        onFriday = slot.isOnFriday();
        courseName = c.getCrs_title();
        courseCode = c.getCrs_code();
    }

    @Override
    public String toString() {
        return String.valueOf(startTime) + ',' +
                endTime + ',' +
                onMonday + ',' +
                onTuesday + ',' +
                onWednesday + ',' +
                onThursday + ',' +
                onFriday + ',' +
                courseName + ',' +
                courseCode + ':';
    }
}
