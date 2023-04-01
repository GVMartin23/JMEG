package edu.gcc.comp350.jmeg;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Search {
    private final IO io;
    private ArrayList<Filter> filters;
    private final Schedule currentSchedule;
    public Search(Schedule schedule) {
        currentSchedule = schedule;
        filters = new ArrayList<>();
        io = IO.getInstance();
    }

    /**
     * Basic method for interaction with program
     * Everything in here can change
     * Return at end used to pass by returntoSchedule
     * If this method is only called in scheduleInteract,
     * then a return will send it back to that method
     */
    public void searchInteraction() {
        Scanner scnr = io.getScanner();
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
                results = resultsInteract(results);
            }

            System.out.println("Continue Searching? (Y/N): ");
            exit = scnr.nextLine().toUpperCase();
        }
    }

    private void addCourseInteract(ArrayList<Course> results) {
        Scanner scnr = io.getScanner();

        System.out.println("Would you like to add any classes to your schedule? Y/N");
        String ans=scnr.nextLine().toUpperCase();
        if(ans.equals("Y")){
            Boolean success=false;
                while(!success) {
                    System.out.println("Enter the course code of the class you wish to add, or Q to quit");
                    String courseCodeToAdd = scnr.nextLine().toUpperCase().strip();
                    if(courseCodeToAdd.equals("Q")){
                        return;
                    }
                    for (Course i : results) {
                        String resultCode=i.getCrs_code().replace(" ", "").strip();
                        String userCode=courseCodeToAdd.replace(" ", "").strip();
                        if (resultCode.equals(userCode)) {
                            if(this.currentSchedule.getCourses().size()>0) {
                                for (Course j : this.currentSchedule.getCourses()) {
                                    if (j.getBegin_tim().equals(i.getBegin_tim())) {
                                        System.out.println("Cannot add course as it overlaps with course " + j.getCrs_title() + ".\n Please remove " + j.getCrs_title() + " in order to add course " + i.getCrs_title());
                                        return;
                                    } else {
                                        currentSchedule.getCourses().add(i);
                                        success = true;
                                        break;
                                    }
                                }
                            }else{
                                success=true;
                                currentSchedule.getCourses().add(i);
                                break;
                            }
                        }
                    }
                    if(!success){
                        System.out.println("Failed to add class. Invalid course code, try again");
                    }
                }
            }

            System.out.println("Successfully added course.  Current course list:\n");
            for(Course c:currentSchedule.getCourses()){
                System.out.print(c.getCrs_title() + " " );
            }
        }


    private ArrayList<Course> resultsInteract(ArrayList<Course> courseList) {
        Scanner scnr = io.getScanner();
        while (true) {
            System.out.print(Course.succinctCourse(courseList));
            System.out.println("What would you like to do?");
            System.out.println("Add Course     Filter    View Details     Continue Searching");
            String input = scnr.nextLine().strip().toUpperCase();
            if (input.equals("ADD COURSE")) {
                addCourseInteract(courseList);
            } else if (input.equals("FILTER")) {
                courseList = filterInteract(courseList);
            } else if (input.equals("VIEW DETAILS")) {
                viewDetailsInteract(courseList);
            } else if ("CONTINUE SEARCHING".contains(input) || input.contains("CONTINUE SEARCHING")) {
                System.out.println("Returning to search");
                break;
            } else {
                System.out.println("Error, invalid input");
            }
        }

        return courseList;
    }



    private ArrayList<Course> filterInteract(ArrayList<Course> courseList) {
        Scanner scnr = io.getScanner();
        System.out.println("Filter By?");
        System.out.println("Year    Term    None");
        String filterBy = scnr.nextLine().toUpperCase();
        if (filterBy.equals("NONE")) {
            return courseList;
        }

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
     * This method finds the course the user wants more details on and uses the viewDetails method
     * to take that course and display more info on it
     * @param courseList - list of courses in csv
     */
    private void viewDetailsInteract(ArrayList<Course> courseList) {
        //TODO: allow viewDetails to interact with the search list
        //TODO: Get User input as to what course they wish to view details of, then call viewDetails with that course
        Scanner scan = io.getScanner();
        System.out.println("Which course would you like to view details on?");
        System.out.println("Choose one of the following:");
        for(Course c : courseList) {
            System.out.println(c.getCrs_code());
        }
        String courseCode = scan.nextLine().toUpperCase().strip();

        List<Course> courses = courseList.stream().filter(c -> c.getCrs_code().equals(courseCode)).collect(Collectors.toList());

        if (courses.size() == 0) {
            System.out.println("Error, invalid course code, please enter correct code.");
            return;
        }

        viewDetails(courses.get(0));
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
            if (course.getBegin_tim().contains(input)) {
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

    /**
     * This method takes in a course after the user searches for it and
     * displays more information on the specified course.
     * Displays the course title, code, begin and end time, day, professor,
     * capacity, credits
     * @param c - course searched for
     */
    private void viewDetails(Course c) {
        System.out.println(c);
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
}
