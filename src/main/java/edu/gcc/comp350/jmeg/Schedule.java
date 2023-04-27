package edu.gcc.comp350.jmeg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Schedule {
    private User user;
    private String title;
    private int credits;
    private ArrayList<Course> courses;
    private String semester;
    private int year;

    public Schedule(User user, String title) {
        this.user = user;
        this.title = title;
        credits = 0;
    }

    /**
     * Constructor for Schedule
     * Used in loading a schedule
     * @param title String title
     * @param credits int credits
     */
    public Schedule(String title, int credits, String semester, int year) {
        this.title = title;
        this.credits = credits;
        this.semester = semester;
        this.year = year;
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

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public ArrayList<Course> getCourses() {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getSemester() {
        if (semester == null) {
            semester = "";
        }
        return semester;
    }

    public int getYear() {
        return year;
    }

    /**
     * Creates new Search class then enters the searchInteraction method in search
     * Used to go from scheduleInteract to searchInteraction
     */
   public void searchCourses() throws Exception {
        Search search = new Search(this);
        search.searchInteraction();
   }

    /**
     * Removes course from schedule courses list
     * @param course course to remove
     * @return true if successful, false otherwise
     */
   public boolean removeCourse(Course course) {
       boolean didRemove = false;
       while (courses.contains(course)) {
           didRemove = courses.remove(course);
       }
       return didRemove;
   }

   public void switchSection(Course old_course, Course new_course){
        removeCourse(old_course);
        courses.add(new_course);
   }

    /**
     * Creates a new calendar and prints out string created by calendar
     */
   private void showCalendar() {
       System.out.println("Credits: "+credits);
       Calendar c=new Calendar(this);
       System.out.println(c.showCalendar());
   }

    /**
     * UI method for working in schedule
     * Used to navigate different functions that can be taken in schedule
     * Current functions are Searching or Quiting program
     */
   public void scheduleInteract() throws Exception {
       boolean firstTime = true;
       Scanner scnr = IO.getInstance().getScanner();

       System.out.println("Viewing schedule "+ title);
       if(courses==null||courses.isEmpty()){
           System.out.println("Schedule currently empty.  Add classes!");
           courses=new ArrayList<>();
       }else {
           System.out.println("Credits: "+credits);
           System.out.println("Entire class list:");
           //Lists out all classes in schedule
           System.out.println(Course.succinctCourse(courses));
       }

       while (true) {
           if (firstTime) {
               firstTime = false;
           } else {
               System.out.println("Viewing schedule "+ title);
               System.out.println("Credits:" +credits);
               System.out.println("Entire class list:");
               //Lists out all classes in schedule
               System.out.println(Course.succinctCourse(courses));
           }

           System.out.println("\nWhat would you like to do?\nSearch     View Calendar       Remove Course       Quit");
           String action = scnr.nextLine().toUpperCase().strip();

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

   private void removeCourseInteract() throws Exception {
       Scanner scnr = IO.getInstance().getScanner();
       System.out.print(Course.succinctCourse(courses));
       System.out.println("Which Course would you like to remove?\nEnter code:");
       String inputCode = scnr.nextLine().strip().toUpperCase();
       List<Course> removable = courses.stream().filter(c -> c.getCrs_code().equals(inputCode)).collect(Collectors.toList());
       if (removable.size() == 1) {
           if(credits - removable.get(0).getCredit_hrs()>=12) {
               int cred=removable.get(0).getCredit_hrs();

               if (removeCourse(removable.get(0))) {
                   setCredits(getCredits() - cred);
               } else {
                   throw new Exception("Could not remove course");
               }
           }else{
               throw new Exception("Cannot remove course as it would take you under the 12 credit limit");
           }
       } else {
           throw new Exception("Invalid course code");
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
                && this.semester.equals(schedule.semester)
                && this.year == schedule.year;
    }
}
