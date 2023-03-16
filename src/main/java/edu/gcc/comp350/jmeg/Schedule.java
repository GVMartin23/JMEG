package edu.gcc.comp350.jmeg;

import java.util.ArrayList;

public class Schedule {
    private User user;
    private String title;
    private int credits;
    private ArrayList<Course> courses;
    private Calendar calendar;

    public Schedule(User user, String title, int credits, ArrayList<Course> courses, Calendar calendar){
        //Might not need
        this.user = user;
        this.title = title;
        this.credits = credits;
        this.courses = courses;
        this.calendar = calendar;
    }

    public Schedule(User user, String title) {
        this.user=user;
        this.title=title;
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
        //TODO: search csv data for courses
   }

   private void removeCourse(Course course){
        courses.remove(course);
   }

   private void getRecommendedSchedule(User user){
        //TODO: stretch goal
   }
   private void switchSection(Course old_course, Course new_course){
        removeCourse(old_course);
        courses.add(new_course);
   }
   private void showCalendar(ArrayList<Course> courses){
        //TODO: once calendar is implemented
   }
   private void getDetails(Course course){
        //maybe should be in Course class?
   }
}
