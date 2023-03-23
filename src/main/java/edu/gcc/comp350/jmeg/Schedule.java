package edu.gcc.comp350.jmeg;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Scanner;

public class Schedule {
    private User user;
    private String title;
    private int credits;
    private ArrayList<Course> courses;
    private ArrayList<Course> allCourses;
    private Calendar calendar;

    public Schedule(User user, String title, int credits, ArrayList<Course> courses, Calendar calendar, ArrayList<Course> allCourses){
        //Might not need
        this.user = user;
        this.title = title;
        this.credits = credits;
        this.courses = courses;
        this.calendar = calendar;
        this.allCourses=allCourses;
    }

    public Schedule(User user, String titlex, ArrayList<Course> allCourses) {
        this.user = user;
        this.title = title;
        this.allCourses=allCourses;
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
        Search search = new Search(this, user, courses);
        search.searchInteraction();
        scheduleInteract();
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
    public void addCourse(String courseName, ArrayList<Course> userCurrentCourses, ArrayList<Course> allCourses){
        for(int i=0; i<userCurrentCourses.size(); i++) {
            if (userCurrentCourses.size() > 0) {
                if (userCurrentCourses.get(i).getCrs_title() == courseName) {
                    System.out.println("You already have this course added");
                }
            }
        }
        for (int j = 0; j < allCourses.size(); j++) {
            if (allCourses.get(j).getCrs_title().equals(courseName)) {
                userCurrentCourses.add(allCourses.get(j));
                System.out.println("Successfully added class" + allCourses.get(j).getCrs_title());
            }

        }
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
       if(courses==null||courses.isEmpty()){//TODO: MAKE IT SO THAT IF COURSES ARENT FILLED IN YET IT DOESNT RUN
           System.out.println("Schedule currently empty.  Add classes!");
           courses=new ArrayList<Course>();
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
           System.out.println("Would you like to add a class to your schedule?  Y/N");
           String ans=scnr.nextLine();
           if(ans.equals("Y")||ans.equals("y")){
               System.out.println("Enter the name of the course you wish to add");
               String courseTitleToAdd=scnr.nextLine();
               addCourse(courseTitleToAdd, courses, allCourses);
               System.out.println("Current courses added:");
               for(Course c : courses){
                   System.out.print(c.getCrs_title()+" ");
               }
           }
       } else if (action.equals("Quit")){
           return;
       }

       System.out.println("Incorrect input");
   }

}
