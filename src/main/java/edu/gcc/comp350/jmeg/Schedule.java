package edu.gcc.comp350.jmeg;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Schedule {
    private User user;
    private String title;
    private int credits;
    private ArrayList<Course> courses;

    public Schedule(User user, String title, int credits, ArrayList<Course> courses, Calendar calendar, ArrayList<Course> allCourses){
        //Might not need
        this.user = user;
        this.title = title;
        this.credits = credits;
        this.courses = courses;
    }

    public Schedule(User user, String title) {
        this.user = user;
        this.title = title;
        credits = 0;
    }
public Schedule(User user, String title, ArrayList<Course> courses){
        this.user=user;
        this.title=title;
        this.courses=courses;
        credits = 0;
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

    public boolean isEmpty() {
        return (this.title.equals(""));
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

    /**
     * Creates new Search class then enters the searchInteraction method in search
     * Used to go from scheduleInteract to searchInteraction
     */
   public void searchCourses() {
        Search search = new Search(this);
        search.searchInteraction();
   }

    /**
     * Removes course from schedule courses list
     * @param course course to remove
     * @return true if successful, false otherwise
     */
   public boolean removeCourse(Course course){
        return courses.remove(course);
   }

   private void getRecommendedSchedule(User user){
        //TODO: stretch goal
   }
   private void switchSection(Course old_course, Course new_course){
        removeCourse(old_course);
        courses.add(new_course);
   }

    /**
     * Creates a new calendar and prints out string created by calendar
     */
   private void showCalendar() {
       Calendar c=new Calendar(this);
       System.out.println(c.showCalendar());
   }

    /**
     * UI method for working in schedule
     * Used to navigate different functions that can be taken in schedule
     * Current functions are Searching or Quiting program
     */
   public void scheduleInteract() {
       boolean firstTime = true;
       Scanner scnr = IO.getInstance().getScanner();

       System.out.println("Viewing schedule "+ title);
       if(courses==null||courses.isEmpty()){
           System.out.println("Schedule currently empty.  Add classes!");
           courses=new ArrayList<>();
       }else {
           System.out.println("Entire class list:");
           //Lists out all classes in schedule
           System.out.println(Course.succinctCourse(courses));
       }

       while (true) {
           if (firstTime) {
               firstTime = false;
           } else {
               System.out.println("Viewing schedule "+ title);
               System.out.println("Entire class list:");
               //Lists out all classes in schedule
               System.out.println(Course.succinctCourse(courses));
           }

           System.out.println("\nWhat would you like to do?\nSearch     View Calendar       Remove Course       Quit");
           String action = scnr.nextLine().toUpperCase(Locale.ROOT).strip();

           if (action.equals("SEARCH")) {
               searchCourses();
           } else if (action.equals("QUIT")) {
               return;
           }else if(action.equals("VIEW CALENDAR")){
               showCalendar();
           } else if (action.equals("REMOVE COURSE")) {
               removeCourseInteract();
           } else {
               System.out.println("Incorrect input");
           }
       }
   }

   private void removeCourseInteract() {
       Scanner scnr = IO.getInstance().getScanner();
       while (true) {
           System.out.print(Course.succinctCourse(courses));
           System.out.println("Which Course would you like to remove?\nEnter code:");
           String inputCode = scnr.nextLine().strip().toUpperCase();
           List<Course> removable = courses.stream().filter(c -> c.getCrs_code().equals(inputCode)).collect(Collectors.toList());
           if (removable.size() == 1) {
               if (removeCourse(removable.get(0))) {
                   System.out.println("Removed: " + removable.get(0));
               } else {
                   System.out.println("Error, could not remove course.");
               }
               break;
           } else {
               System.out.println("Error, invalid course code.");
           }
       }
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

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Schedule)) {
           return false;
       }

       Schedule schedule = (Schedule) obj;

       if (this == schedule) {
           return true;
       }

        return this.title.equals(schedule.title)
                && this.user.equals(schedule.user);
    }
}
