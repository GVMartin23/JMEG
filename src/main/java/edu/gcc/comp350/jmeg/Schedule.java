package edu.gcc.comp350.jmeg;

import java.util.ArrayList;
import java.util.Scanner;

public class Schedule {
    private User user;
    private String title;
    private int credits;
    private ArrayList<Course> courses;
    private Calendar calendar;

    public Schedule(User user, String title, int credits, ArrayList<Course> courses, Calendar calendar, ArrayList<Course> allCourses){
        //Might not need
        this.user = user;
        this.title = title;
        this.credits = credits;
        this.courses = courses;
        this.calendar = calendar;
    }

    public Schedule(User user, String title) {
        this.user = user;
        this.title = title;
    }

    /**
     * Constructor for Schedule
     * Used in loading a schedule
     * @param title String title
     * @param credits int credits
     */
    public Schedule(String title, int credits) {
        this.title = title;
        this.credits = credits;
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

   public void searchCourses() {
        Search search = new Search(this);
        search.searchInteraction();
        scheduleInteract();
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
    /**
     * UI method for working in schedule
     * Everything in here can change
     * Some of stuff in userSheduleSelect in Main moved here
     * Main use is giving way to choose between searching courses
     * and Quitting program
     */
   public void scheduleInteract() {
       Scanner scnr = new Scanner(System.in);

       System.out.println("Viewing schedule "+ title);
       if(courses==null||courses.isEmpty()){
           System.out.println("Schedule currently empty.  Add classes!");
           courses=new ArrayList<>();
       }else {
           System.out.println("Entire class list:");//Lists out all classes in schedule
           for (Course c : courses) {
               System.out.print(" " + c.getCrs_title());
           }
       }
       System.out.println("\nWhat would you like to do?\nSearch     Quit");

       String action = scnr.nextLine();
       if (action.equals("Search")) {
           searchCourses();
       } else if (action.equals("Quit")){
           return;
       }

       System.out.println("Incorrect input");
   }

   @Override
   public String toString(){
       StringBuilder schedules = new StringBuilder(String.format("%s's current schedule:\n--------------------------------\n", user));
       for (Course course: courses) {
           schedules.append(course.toString());
           if(courses.indexOf(course) != courses.size() - 1)
               schedules.append("--------------------------------\n");
       }
       return schedules.toString();
   }

}
