package edu.gcc.comp350.jmeg;

import java.util.ArrayList;

public class Schedule {
    private User user;
    private String title;
    private int credits;
    private ArrayList<Course> courses;
    private Calendar calendar;

    public Schedule(){
//Might not need
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

   public void searchCourses(){

   }

   private void removeCourse(Course course){

   }

   private void getRecommendedSchedule(User user){

   }
   private void switchSection(Course course){

   }
   private void showCalendar(ArrayList<Course> courses){

   }
   private void getDetails(Course course){

   }
}
