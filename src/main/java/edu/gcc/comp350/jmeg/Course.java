package edu.gcc.comp350.jmeg;

import java.time.LocalTime;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Course {

    int yr_code;
    int trm_code;
    String crs_code;
    String crs_comp1;
    String crs_comp2;
    String crs_comp3;
    String crs_title;
    int credit_hrs;
    String x_listed_parnt_crs;
    String acad_credit_varies;
    String acad_credit_label;
    int crs_capacity;
    int max_capacity;
    int crs_enrollment;
    String bldg_cde;
    String room_cde;
    String monday_cde;
    String tuesday_cde;
    String wednesday_cde;
    String thursday_cde;
    String friday_cde;
    String begin_tim;
    String end_tim;
    String last_name;
    String first_name;
    String preferred_name;
    String comment_txt;

public Course(String title){
    this.crs_title=title;
}
    public Course(int yr_code, int trm_code, String crs_code, String crs_comp1, String crs_comp2,
                  String crs_comp3, String crs_title, int credit_hrs, String x_listed_parnt_crs,
                  String acad_credit_varies, String acad_credit_label, int crs_capacity, int max_capacity,
                  int crs_enrollment, String bldg_cde, String room_cde, String monday_cde, String tuesday_cde,
                  String wednesday_cde, String thursday_cde, String friday_cde, String begin_tim, String end_tim,
                  String last_name, String first_name, String preferred_name, String comment_txt) {
        this.yr_code = yr_code;
        this.trm_code = trm_code;
        this.crs_code = crs_code;
        this.crs_comp1 = crs_comp1;
        this.crs_comp2 = crs_comp2;
        this.crs_comp3 = crs_comp3;
        this.crs_title = crs_title;
        this.credit_hrs = credit_hrs;
        this.x_listed_parnt_crs = x_listed_parnt_crs;
        this.acad_credit_varies = acad_credit_varies;
        this.acad_credit_label = acad_credit_label;
        this.crs_capacity = crs_capacity;
        this.max_capacity = max_capacity;
        this.crs_enrollment = crs_enrollment;
        this.bldg_cde = bldg_cde;
        this.room_cde = room_cde;
        this.monday_cde = monday_cde;
        this.tuesday_cde = tuesday_cde;
        this.wednesday_cde = wednesday_cde;
        this.thursday_cde = thursday_cde;
        this.friday_cde = friday_cde;
        this.begin_tim = begin_tim;
        this.end_tim = end_tim;
        this.last_name = last_name;
        this.first_name = first_name;
        this.preferred_name = preferred_name;
        this.comment_txt = comment_txt;
    }

    /**
     * Dummy Constructor for testing
     */
    public Course() {

    }

    public int getYr_code() {
        return yr_code;
    }

    public void setYr_code(int yr_code) {
        this.yr_code = yr_code;
    }

    public int getTrm_code() {
        return trm_code;
    }

    public void setTrm_code(int trm_code) {
        this.trm_code = trm_code;
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

    public void setCrs_comp1(String crs_comp1) {
        this.crs_comp1 = crs_comp1;
    }

    public String getCrs_comp2() {
        return crs_comp2;
    }

    public void setCrs_comp2(String crs_comp2) {
        this.crs_comp2 = crs_comp2;
    }

    public String getCrs_comp3() {
        return crs_comp3;
    }

    public void setCrs_comp3(String crs_comp3) {
        this.crs_comp3 = crs_comp3;
    }

    public String getCrs_title() {
        return crs_title;
    }

    public void setCrs_title(String crs_title) {
        this.crs_title = crs_title;
    }

    public int getCredit_hrs() {
        return credit_hrs;
    }

    public void setCredit_hrs(int credit_hrs) {
        this.credit_hrs = credit_hrs;
    }

    public String getX_listed_parnt_crs() {
        return x_listed_parnt_crs;
    }

    public void setX_listed_parnt_crs(String x_listed_parnt_crs) {
        this.x_listed_parnt_crs = x_listed_parnt_crs;
    }

    public String getAcad_credit_varies() {
        return acad_credit_varies;
    }

    public void setAcad_credit_varies(String acad_credit_varies) {
        this.acad_credit_varies = acad_credit_varies;
    }

    public String getAcad_credit_label() {
        return acad_credit_label;
    }

    public void setAcad_credit_label(String acad_credit_label) {
        this.acad_credit_label = acad_credit_label;
    }

    public int getCrs_capacity() {
        return crs_capacity;
    }

    public void setCrs_capacity(int crs_capacity) {
        this.crs_capacity = crs_capacity;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    public int getCrs_enrollment() {
        return crs_enrollment;
    }

    public void setCrs_enrollment(int crs_enrollment) {
        this.crs_enrollment = crs_enrollment;
    }

    public String getBldg_cde() {
        return bldg_cde;
    }

    public void setBldg_cde(String bldg_cde) {
        this.bldg_cde = bldg_cde;
    }

    public String getRoom_cde() {
        return room_cde;
    }

    public void setRoom_cde(String room_cde) {
        this.room_cde = room_cde;
    }

    public String getMonday_cde() {
        return monday_cde;
    }

    public void setMonday_cde(String monday_cde) {
        this.monday_cde = monday_cde;
    }

    public String getTuesday_cde() {
        return tuesday_cde;
    }

    public void setTuesday_cde(String tuesday_cde) {
        this.tuesday_cde = tuesday_cde;
    }

    public String getWednesday_cde() {
        return wednesday_cde;
    }

    public void setWednesday_cde(String wednesday_cde) {
        this.wednesday_cde = wednesday_cde;
    }

    public String getThursday_cde() {
        return thursday_cde;
    }

    public void setThursday_cde(String thursday_cde) {
        this.thursday_cde = thursday_cde;
    }

    public String getFriday_cde() {
        return friday_cde;
    }

    public void setFriday_cde(String friday_cde) {
        this.friday_cde = friday_cde;
    }

    public String getBegin_tim() {
        return begin_tim;
    }

    public void setBegin_tim(String begin_tim) {
        this.begin_tim = begin_tim;
    }

    public String getEnd_tim() {
        return end_tim;
    }

    public void setEnd_tim(String end_tim) {
        this.end_tim = end_tim;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPreferred_name() {
        return preferred_name;
    }

    public void setPreferred_name(String preferred_name) {
        this.preferred_name = preferred_name;
    }

    public String getComment_txt() {
        return comment_txt;
    }

    public void setComment_txt(String comment_txt) {
        this.comment_txt = comment_txt;
    }

    public static String formatTimeOfDay(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ha");
        String formattedTime = time.format(formatter);
        return formattedTime;
    }

    public static String convertStringToTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(timeString, formatter);
        String formattedTime = formatTimeOfDay(localTime);
        return formattedTime;
    }

    @Override
    public String toString(){
        String crs = "";
        crs += crs_title + "\n";
        crs += "--------------------------------\n";
        crs += "Course and Section Code: " + crs_comp1 + " " + crs_comp2 + " " + crs_comp3 + "\t\t";

        String st_time = convertStringToTime(begin_tim);
        String end_time = convertStringToTime(end_tim);

        String meets = String.format("Meets: %s/%s/%s/%s/%s\t\tFrom: %s to %s\n", monday_cde, tuesday_cde, wednesday_cde, thursday_cde, friday_cde, st_time, end_time);
        crs += meets;
        crs += "Professor: " + last_name + ", " + first_name;
        crs += String.format("\t\tCapacity: %d/%d\n", crs_enrollment, crs_capacity);
        crs += String.format("\t\tCredit Hours: %d\n", credit_hrs);
        return crs;
    }
}
