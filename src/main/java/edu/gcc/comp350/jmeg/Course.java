package edu.gcc.comp350.jmeg;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Course {

    private TimeSlot timeSlot;

    private int yr_code;
    private int trm_code;
    private String crs_code;
    private String crs_comp1;
    private String crs_comp2;
    private String crs_comp3;
    private String crs_title;
    private int credit_hrs;
    private int crs_capacity;
    private int crs_enrollment;
    private String monday_cde;
    private String tuesday_cde;
    private String wednesday_cde;
    private String thursday_cde;
    private String friday_cde;
    private String begin_tim;
    private String end_tim;
    private String last_name;
    private String first_name;
    private String preferred_name;
    private String comment_txt;

    public Course(int yr_code, int trm_code, String crs_code, String crs_comp1, String crs_comp2,
                  String crs_comp3, String crs_title, int credit_hrs, int crs_capacity, int crs_enrollment,
                  String monday_cde, String tuesday_cde, String wednesday_cde, String thursday_cde,
                  String friday_cde, String begin_tim, String end_tim, String last_name, String first_name,
                  String preferred_name, String comment_txt) {
        this.yr_code = yr_code;
        this.trm_code = trm_code;
        this.crs_code = crs_code.replace("  ", " ");
        this.crs_comp1 = crs_comp1;
        this.crs_comp2 = crs_comp2;
        this.crs_comp3 = crs_comp3;
        this.crs_title = crs_title;
        this.credit_hrs = credit_hrs;
        this.crs_capacity = crs_capacity;
        this.crs_enrollment = crs_enrollment;
        this.monday_cde = monday_cde;
        this.tuesday_cde = tuesday_cde;
        this.wednesday_cde = wednesday_cde;
        this.thursday_cde = thursday_cde;
        this.friday_cde = friday_cde;
        this.begin_tim = convertStringToTime(begin_tim);
        this.end_tim = convertStringToTime(end_tim);
        this.last_name = last_name;
        this.first_name = first_name;
        this.preferred_name = preferred_name;
        this.comment_txt = comment_txt;
        timeSlot = new TimeSlot(this);
    }

    /**
     * Dummy Constructor for testing
     */
    public Course() {

    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public int getYr_code() {
        return yr_code;
    }

    public int getTrm_code() {
        return trm_code;
    }

    public String getCrs_code() {
        return crs_code;
    }

    public void setCrs_code(String crs_code) {
        this.crs_code = crs_code;
    }

    public String getCrs_comp1() {
        return crs_comp1;
    }

    public String getCrs_comp2() {
        return crs_comp2;
    }

    public String getCrs_comp3() {
        return crs_comp3;
    }

    public String getCrs_title() {
        return crs_title;
    }

    public int getCredit_hrs() {
        return credit_hrs;
    }

    public int getCrs_capacity() {
        return crs_capacity;
    }

    public int getCrs_enrollment() {
        return crs_enrollment;
    }

    public String getMonday_cde() {
        return monday_cde;
    }

    public String getTuesday_cde() {
        return tuesday_cde;
    }

    public String getWednesday_cde() {
        return wednesday_cde;
    }

    public String getThursday_cde() {
        return thursday_cde;
    }

    public String getFriday_cde() {
        return friday_cde;
    }

    public String getBegin_tim() {
        return begin_tim;
    }

    public String getEnd_tim() {
        return end_tim;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getPreferred_name() {
        return preferred_name;
    }

    public String getComment_txt() {
        return comment_txt;
    }

    public static String formatTimeOfDay(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
        return time.format(formatter);
    }

    public static String convertStringToTime(String timeString) {
        if (timeString.length() == 7) {
            timeString = "0" + timeString;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(timeString, formatter);
        return formatTimeOfDay(localTime);
    }

    @Override
    public String toString(){
        String crs = crs_title + "\n";
        crs += "--------------------------------\n";
        crs += "Course and Section Code: " + crs_comp1 + " " + crs_comp2 + " " + crs_comp3 + "\t\t";

        String meets = String.format("Meets: %s/%s/%s/%s/%s\t\tFrom: %s to %s\n",
                monday_cde, tuesday_cde, wednesday_cde, thursday_cde, friday_cde, begin_tim, end_tim);
        crs += meets;

        crs += "Professor: " + last_name + ", " + first_name;
        crs += String.format("\t\tCapacity: %d/%d\n", crs_enrollment, crs_capacity);
        crs += String.format("\t\tCredit Hours: %d\n", credit_hrs);
        return crs;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Course)) {
            return false;
        }
        Course course = (Course) obj;

        if (this == obj) {
            return true;
        }

        return this.crs_code.equals(course.crs_code)
                && this.trm_code == course.trm_code
                && this.yr_code == course.yr_code;
    }


    /**
     * Used in place of toString when all we want is short on line course description
     * @param courses ArrayList of Courses to convert to String
     * @return String containing year, title, code for each course in list
     */
    public static String succinctCourse(ArrayList<Course> courses) {
        StringBuilder sb = new StringBuilder();
        for (Course c : courses) {
            sb.append(c.getYr_code());
            sb.append("| Title: ");
            sb.append(c.getCrs_title());
            sb.append("| Code: ");
            sb.append(c.getCrs_code());
            sb.append("\n");
        }

        return sb.toString();
    }
}
