package edu.gcc.comp350.jmeg;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Search {
    private final IO io;
    private ArrayList<Filter> filters;
    private final Schedule currentSchedule;
    private ArrayList<Course> results;
    private boolean leave;
    private boolean leaveResults;

    public Search(Schedule schedule, String semester, int year) {
        currentSchedule = schedule;
        io = IO.getInstance();
        leave = false;
        leaveResults = false;
        results = initFilters(semester, year);
    }

    /**
     * Creates filters for semester and year based on constructor input
     * Takes full list of Courses and filters by semester and year
     * @param semester String semester to filter by
     * @param year int year to filter by
     * @return Filtered list of Courses
     */
    private ArrayList<Course> initFilters(String semester, int year) {
        ArrayList<Course> filteredCourses = new ArrayList<>(Main.getCourses());

        semester = semester.toUpperCase();
        if (semester.equals("FALL") || semester.equals("SPRING")) {
            Filter semesterFilter = new Filter("TERM", semester);
            filterCourses(semesterFilter, filteredCourses);
            getFilters().add(semesterFilter);
        }

        if (year == 2018 || year == 2019 || year == 2020) {
            Filter yearFilter = new Filter("YEAR", String.valueOf(year));
            filterCourses(yearFilter, filteredCourses);
            getFilters().add(yearFilter);
        }

        return filteredCourses;
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

        while (!leave) {
            System.out.println("Search by,");
            System.out.println("Name    Day     Time    Code");
            //Determine how they are searching
            String identifier = scnr.nextLine().toUpperCase();

            System.out.println("Enter query:");
            String search = scnr.nextLine().toUpperCase();

            results = search(identifier, search, results);

            if (results == null) {
                System.out.println("Incorrect Search or Identifier");
            } else if (results.isEmpty()) {
                System.out.println("Search produced zero results, try a different query or identifier");
                results = null;
            } else {
                results = resultsInteract(results);
            }

            if (leaveResults) {
                leaveResults = false;
                continue;
            }

            if (!leave) {
                System.out.println("Continue Searching? (Y/N): ");
                String exit = scnr.nextLine().toUpperCase().strip();
                if (exit.equals("N")) {
                    leave = true;
                }
            }
        }
        System.out.println("Leaving search");
    }

    private void addCourseInteract(ArrayList<Course> results) {
        Scanner scnr = io.getScanner();

        boolean success = false;
        while (!success) {
            System.out.println("Enter the course code of the class you wish to add, or Q to quit");
            String courseCodeToAdd = scnr.nextLine().toUpperCase().strip();
            if (courseCodeToAdd.equals("Q")) {
                leaveResults = true;
                return;
            }
            String userCode = courseCodeToAdd.replace(" ", "").strip();
            for (Course i : results) {
                String resultCode = i.getCrs_code().replace(" ", "").strip();
                if (resultCode.equals(userCode)) {
                    if (currentSchedule.getCourses().size() > 0) {//If there is stuff in the existing calendar
                        for (Course j : currentSchedule.getCourses()) {
                            if (TimeSlot.dayOverlap(i.getTimeSlot(), j.getTimeSlot()) && coursesOverlap(i, j)) {
                                System.out.println("Cannot add course as it overlaps with course " + j.getCrs_title() + ".\n Please remove " + j.getCrs_title() + " in order to add course " + i.getCrs_title());
                                return;
                            } else {
                                currentSchedule.getCourses().add(i);
                                leave = true;
                                leaveResults = true;
                                return;
                            }
                        }
                    } else {//Nothing in the schedule
                        System.out.println("Add course"+courseCodeToAdd+" ? Y/N");
                        String answer=scnr.nextLine().toUpperCase();
                        while(!answer.equals("Y")&& !answer.equals("N")){
                            System.out.println("Invalid input. Enter Y/N");
                            answer=scnr.nextLine();
                        }
                        if(answer.equals("Y")) {
                            for (Course c : results) {
                                if (c.equals(i)) {
                                    currentSchedule.getCourses().add(c);
                                }
                            }
                            leaveResults = true;
                            leave = true;
                            //Should send them back to searchInteract
                        }
                        return;
                    }
                }
            }
            if (!success) {
                System.out.println("Failed to add class. Invalid course code, try again");
            }
        }
    }

    /**
     * returns true if courses overlap, false otherwise
     * @param i
     * @param j
     * @return
     */
    public Boolean coursesOverlap(Course i, Course j){
        if(i.getTimeSlot().getBeginTimeCode()==j.getTimeSlot().getBeginTimeCode() || i.getTimeSlot().getEndTimeCode()==j.getTimeSlot().getEndTimeCode()){
            return true;
        }
        return ((i.getTimeSlot().getBeginTimeCode() >= j.getTimeSlot().getBeginTimeCode() &&
                i.getTimeSlot().getBeginTimeCode() <= j.getTimeSlot().getEndTimeCode())
                ||
                (i.getTimeSlot().getBeginTimeCode() <= j.getTimeSlot().getBeginTimeCode() &&
                        i.getTimeSlot().getEndTimeCode() >= j.getTimeSlot().getEndTimeCode())
                ||
                (i.getTimeSlot().getBeginTimeCode() >= j.getTimeSlot().getBeginTimeCode() &&
                        i.getTimeSlot().getEndTimeCode() <= j.getTimeSlot().getEndTimeCode())
                ||
                (i.getTimeSlot().getBeginTimeCode()<=j.getTimeSlot().getEndTimeCode() )&&
                        i.getTimeSlot().getEndTimeCode()>=j.getTimeSlot().getBeginTimeCode());
    }
    private ArrayList<Course> resultsInteract(ArrayList<Course> courseList) {
        Scanner scnr = io.getScanner();
        while (!leaveResults) {
            System.out.print(Course.succinctCourse(courseList));
            System.out.println("What would you like to do?");
            System.out.println("Add Course     Filter    View Details     Search Again      Exit");
            String input = scnr.nextLine().strip().toUpperCase();
            while (input.equals("")) {
                input = scnr.nextLine().strip().toUpperCase();
            }
            if (input.equals("ADD COURSE")) {
                addCourseInteract(courseList);
            } else if (input.equals("FILTER")) {
                courseList = filterInteract(courseList);
            } else if (input.equals("VIEW DETAILS")) {
                viewDetailsInteract(courseList);
            } else if (input.equals("SEARCH AGAIN")) {
                System.out.println("Returning to search");
                leaveResults = true;
            } else if (input.equals("EXIT")){
                leaveResults = true;
                leave = true;
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
        Scanner scan = io.getScanner();
        System.out.println("Which course would you like to view details on?");

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
}
