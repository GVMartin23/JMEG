package edu.gcc.comp350.jmeg;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;


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
        ArrayList<Course> results = null;

        String exit = "";
        while (!exit.equals("N")) {

            System.out.println("Search by,");
            System.out.println("Name    Day     Time    Code");
            //Determine how they are searching
            String identifier = scnr.nextLine().toUpperCase();

            System.out.println("Enter query:");
            String search = scnr.nextLine().toUpperCase();

            //Gets list of results from search
            if (results == null) {
                results = search(identifier, search, Main.getCourses());
            } else {
                results = search(identifier, search, results);
            }


            if (results == null) {
                System.out.println("Incorrect Search or Identifier");
            } else if (results.isEmpty()) {
                System.out.println("Search produced zero results, try a different query or identifier");
            } else {
                System.out.println(results);//Print the search results
                System.out.println("Would you like to add any classes to your schedule? Y/N");
                String ans=scnr.nextLine();
                if(ans.equals("Y")||ans.equals("y")){
                    System.out.println("Enter the name of the class you wish to add");
                    String classToAdd=scnr.nextLine();
                    for(Course i : Main.getCourses()){
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
            System.out.println(results);
            System.out.println("Filter By?");
            System.out.println("Year    Term    None");
            String filterBy = scnr.nextLine().toUpperCase();
            if (!filterBy.equals("NONE")) {
                results = filterInteract(filterBy, results);
                System.out.println(results);
            }

        }


        System.out.println("Continue Searching? (Y/N): ");

            System.out.println("If finished searching, type exit, else press enter: ");
            exit = scnr.nextLine().toUpperCase();
        }
    }

    private ArrayList<Course> filterInteract(String filterBy, ArrayList<Course> courseList) {
        Scanner scnr = new Scanner(System.in);
        Filter filter = null;
        String filterVal;
        if (filterBy.equals("YEAR")) {
            System.out.println("Enter year (2018, 2019, 2020): ");
            filterVal = scnr.next();
            if(filterVal.equals("2018") || filterVal.equals("2019") || filterVal.equals("2020")) {
                filter = new Filter(filterBy, filterVal);
            } else {
                System.out.println("Error, invalid input.");
            }
        } else if (filterBy.equals("TERM")) {
            System.out.println("Enter term (Spring, Fall): ");
            filterVal = scnr.next().toUpperCase();
            if (filterVal.equals("FALL") || filterVal.equals("SPRING")) {
                filter = new Filter(filterBy, filterVal);
            }
        }

        if (filter != null) {
            return filterCourses(filter, courseList);
        }
        System.out.println("Invalid filter");
        return courseList;
    }

    /**
     * Takes in and identifier and searches based on input
     * @param identifier defines what method to search by
     * @param input string that is used to search
     * @return List of courses if identifier exits else null
     */
    public ArrayList<Course> search(String identifier, String input, ArrayList<Course> searchList) {
        if (identifier.equals("NAME")) {
            return searchCourseName(input, searchList);
        } else if (identifier.equals("DAY")) {
            return searchCourseDay(input, searchList);
        } else if (identifier.equals("TIME")) {
            return searchCourseTime(input, searchList);
        } else if (identifier.equals("CODE")) {
            return searchCourseCode(input, searchList);
        }

        return null;
    }

    /**
     * This method takes in user input and searches the course list based on the course name
     * that is inputted
     * @param input - user input (course name) as a string
     * @return - course list including courses with the course name specified by the user
     */
    public ArrayList<Course> searchCourseName(String input, ArrayList<Course> searchList) {
        ArrayList<Course> c = new ArrayList<>();

        for (Course course : searchList) {
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
    public ArrayList<Course> searchCourseCode(String input, ArrayList<Course> searchList) {
        ArrayList<Course> c = new ArrayList<>();

        for (Course course : searchList) {
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
    public ArrayList<Course> searchCourseDay(String input, ArrayList<Course> searchList) {
        ArrayList<Course> c = new ArrayList<>();

        for (Course course : searchList) {
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
    private ArrayList<Course> searchCourseTime(String input, ArrayList<Course> searchList) {

        ArrayList<Course> c = new ArrayList<>();

        for (Course course : searchList) {
            if (course.getBegin_tim().substring(11).contains(input)) {
                c.add(course);
            }
        }
        return c;
    }

    public ArrayList<Course> filterCourses(Filter filter, ArrayList<Course> courses) {
        ArrayList<Filter> filterList = getFilters();
        if (filterList.contains(filter)) {
            System.out.println("Already filtered by " + filter.getFilterType().toString().toLowerCase());
            return courses;
        } else {
            filterList.add(filter);
        }

        if (filter.getFilterType() == Filter.FilterTypes.YEAR) {
            return  (ArrayList<Course>) courses.stream().filter(c -> c.getYr_code() == Integer.parseInt(filter.getFilterName())).collect(Collectors.toList());
        } else if (filter.getFilterType() == Filter.FilterTypes.TERM) {
            int termInt = filter.getFilterName().equals("FALL") ? 10 : 30;
            return (ArrayList<Course>) courses.stream().filter(c -> c.getTrm_code() == termInt).collect(Collectors.toList());
        }
        return null;
    }

    private void viewDetails(Course c){
        return;
    }
    private void returnToSchedule(){
        return;
    }

    public ArrayList<Filter> getFilters() {
        if (filters == null) {
            filters = new ArrayList<>();
        }
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
