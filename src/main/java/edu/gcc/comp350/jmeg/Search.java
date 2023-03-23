package edu.gcc.comp350.jmeg;
import java.util.ArrayList;
import java.util.Scanner;



public class Search {
    private ArrayList<Filter> filters;
    private ArrayList<Course> completeCourseList=Main.getCourses();
    private User currentUser;
    private String inputData;
    private Schedule currentSchedule;
    public Search(Schedule schedule, User user) {
        currentSchedule = schedule;
        this.currentUser=user;
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
        while (!exit.equals("EXIT")) {
            System.out.println("Search by,");
            System.out.println("Name    Day     Time    Code");
            //Determine how they are searching
            String identifier = scnr.nextLine().toUpperCase();

            System.out.println("Enter query:");
            String search = scnr.nextLine().toUpperCase();

            //Gets list of results from search
            ArrayList<Course> results = search(identifier, search);

            if (results == null) {
                System.out.println("Incorrect Search or Identifier");
                continue;
            } else if (results.isEmpty()) {
                System.out.println("Search produced zero results, try a different query or identifier");
            } else {
                System.out.println(results);//Print the search results
                System.out.println("Would you like to add any classes to your schedule? Y/N");
                String ans=scnr.nextLine();
                if(ans.equals("Y")||ans.equals("y")){
                    System.out.println("Enter the name of the class you wish to add");
                    String classToAdd=scnr.nextLine();
                    for(Course i:completeCourseList){
                        if(i.getCrs_title().equals(classToAdd)){
                            currentSchedule.getCourses().add(i);

                        }
                    }
                    System.out.println("Successfully added course.  Current course list:\n");
                    for(Course c:currentSchedule.getCourses()){
                        System.out.print(c.getCrs_title() + " " );
                    }
                }
            }
            System.out.println("If finished searching, type exit, else press enter: ");
            exit = scnr.nextLine().toUpperCase();
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

        //Search search = new Search(new Schedule("TEST", 0), );

        System.out.println("Enter Code of Class");
        String input = scnr.nextLine();

        //search.searchCourseName(input);
      //  search.searchCourseCode(input);
    }

    /**
     * Takes in and identifier and searches based on input
     * @param identifier defines what method to search by
     * @param input string that is used to search
     * @return List of courses if identifier exits else null
     */
    public ArrayList<Course> search(String identifier, String input) {
        if (identifier.equals("NAME")) {
            return searchCourseName(input);
        } else if (identifier.equals("DAY")) {
            return searchCourseDay(input);
        } else if (identifier.equals("TIME")) {
            return searchCourseTime(input);
        } else if (identifier.equals("CODE")) {
            return searchCourseCode(input);
        }

        return null;
    }

    /**
     * This method takes in user input and searches the course list based on the course name
     * that is inputted
     * @param input - user input (course name) as a string
     * @return - course list including courses with the course name specified by the user
     */
    public ArrayList<Course> searchCourseName(String input) {
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
    public ArrayList<Course> searchCourseCode(String input) {
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
    public ArrayList<Course> searchCourseDay(String input) {
        ArrayList<Course> c = new ArrayList<>();

        for (Course course : Main.getCourses()) {
            if(input.equals("M") || input.equals("MON") || input.equals("MONDAY")){
                if (course.getMonday_cde().contains(input)) {
                    c.add(course);
                }
            }

            if(input.equals("T") || input.equals("TUES") || input.equals("TUESDAY")){
                if (course.getTuesday_cde().contains(input)) {
                    c.add(course);
                }
            }

            if(input.equals("W") || input.equals("WED") || input.equals("WEDNESDAY")){
                if (course.getWednesday_cde().contains(input)) {
                    c.add(course);
                }
            }

            if(input.equals("R") || input.equals("THURS") || input.equals("THURSDAY")){
                if (course.getThursday_cde().contains(input)) {
                    c.add(course);
                }
            }

            if(input.equals("F") || input.equals("FRI") || input.equals("FRIDAY")){
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
    public ArrayList<Course> searchCourseTime(String input) {
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

}
