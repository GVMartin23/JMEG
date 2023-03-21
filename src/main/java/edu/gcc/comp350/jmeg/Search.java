package edu.gcc.comp350.jmeg;
import java.util.ArrayList;
import java.util.Scanner;


public class Search {
    private ArrayList<Filter> filters;
    private String inputData;
    private Schedule currentSchedule;
    public Search(Schedule schedule) {
        currentSchedule = schedule;
        filters = new ArrayList<>();
        inputData = "";
    }

    /**
     * Basic method for interaction with program
     * Everything in here can change
     * Return at end used to pass by returntoSchedule
     * If this method is only called in scheduleInteract,
     * then a return will send it back to that method
     */
    public void searchInteraction() {
        Scanner scnr = new Scanner(System.in);

        String exit = "Enter";
        while (!exit.equals("exit")) {
            System.out.println("Enter Name of Class");
            String search = scnr.nextLine();
            ArrayList<Course> results = searchCourseName(search);
            System.out.println(results);

            System.out.println("If finished searching, type exit, else press enter: ");
            exit = scnr.nextLine();
        }
    }


    /**
     * USED FOR TESTING ONLY
     * DELETE ONCE DONE
     * @param args
     */
    public static void main(String[] args) {
        Main.testCSV();
        Scanner scnr = new Scanner(System.in);

        Search search = new Search(new Schedule("TEST", 0));

        System.out.println("Enter Code of Class");
        String input = scnr.nextLine();

        //search.searchCourseName(input);
        search.searchCourseCode(input);
    }

    public ArrayList<Course> search() {
        return null;
    }

    /**
     * This method takes in user input and searches the course list based on the course name
     * that is inputted
     * @param input - user input (course name) as a string
     * @return - course list including courses with the course name specified by the user
     */
    private ArrayList<Course> searchCourseName(String input) {
        ArrayList<Course> c = new ArrayList<>();

        for (Course course : Main.getCourses()) {
            if (course.getCrs_title().contains(input)) {
                c.add(course);
            }
        }
        return c;
    }

    /**
     * This method takes in user input and searches the course list based on the course code
     * that is inputted
     * @param input- user input (course code) as a string
     * @return - course list including courses with the course name specified by the user
     */
    private ArrayList<Course> searchCourseCode(String input) {
        ArrayList<Course> c = new ArrayList<>();

        for (Course course : Main.getCourses()) {
            if (course.getCrs_code().contains(input)) {
                c.add(course);
            }
        }
        return c;
    }

    /**
     * This method takes in user input and searches the course list based on the course day
     * that is inputted
     * @param input- user input (course day) as a string
     * @return - course list including courses with the course name specified by the user
     */
    private ArrayList<Course> searchCourseDay(String input) {
        ArrayList<Course> c = new ArrayList<>();

        for (Course course : Main.getCourses()) {
            if(input == "M" || input == "MON" || input == "MONDAY"){
                if (course.getMonday_cde().contains(input)) {
                    c.add(course);
                }
            }

            if(input == "T" || input == "TUES" || input == "TUESDAY"){
                if (course.getTuesday_cde().contains(input)) {
                    c.add(course);
                }
            }

            if(input == "W" || input == "WED" || input == "WEDNESDAY"){
                if (course.getWednesday_cde().contains(input)) {
                    c.add(course);
                }
            }

            if(input == "R" || input == "THURS" || input == "THURSDAY"){
                if (course.getThursday_cde().contains(input)) {
                    c.add(course);
                }
            }

            if(input == "F" || input == "FRI" || input == "FRIDAY"){
                if (course.getFriday_cde().contains(input)) {
                    c.add(course);
                }
            }

        }
        return c;
    }

    /**
     * This method takes in user input and searches the course list based on the course begin time
     * that is inputted
     * @param input- user input (course begin time) as a string
     * @return - course list including courses with the course name specified by the user
     */
    private ArrayList<Course> searchCourseTime(String input) {
        ArrayList<Course> c = new ArrayList<>();

        for (Course course : Main.getCourses()) {
            if (course.getBegin_tim().contains(input)) {
                c.add(course);
            }
        }
        return c;
    }
    private void clearFilters(){
        return;
    }
    private Course addFilter(Filter f){
        return null;
    }
    private Course removeFilter(Filter f){
        return null;
    }
    private void viewDetails(Course c){
        return;
    }
    private void returnToSchedule(){
        return;
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<Filter> filters) {
        this.filters = filters;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }

    public void setCurrentSchedule(Schedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }

    private void addCourse(Course c){
        return;
    }

}
