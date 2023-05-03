package edu.gcc.comp350.jmeg;

import edu.gcc.comp350.jmeg.filter.FilterTerm;
import edu.gcc.comp350.jmeg.filter.FilterYear;
import edu.gcc.comp350.jmeg.filter.Filterable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Search {
    private ArrayList<Filterable> filters;
    private final Schedule currentSchedule;
    private ArrayList<Course> results;

    public Search(Schedule schedule) {
        currentSchedule = schedule;
        filters = new ArrayList<>();
        results = initFilters(schedule.getSemester(), schedule.getYear());
    }

    public ArrayList<Course> getResults() {
        if (results == null) {
            results = new ArrayList<>();
        }
        return results;
    }

    /**
     * Getter for filters
     * @return filters is null, new ArrayList, else filters
     */
    public ArrayList<Filterable> getFilters() {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        return filters;
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
            int code = semester.equals("FALL") ? 10 : 30;
            FilterTerm termFilter = new FilterTerm(code);
            filteredCourses = termFilter.filter(filteredCourses);
            getFilters().add(termFilter);
        }

        if (year == 2018 || year == 2019 || year == 2020) {
            FilterYear yearFilter = new FilterYear(year);
            filteredCourses = yearFilter.filter(filteredCourses);
            getFilters().add(yearFilter);
        }

        return filteredCourses;
    }

    /**
     * Adds all courses that are equivalent to courseToAdd that exist in resultList.
     * This deals with classes such as Math classes where the data splits them into two classes,
     * one meeting MWF and 10 and the other that meets on Tuesday at a different time since these are 4 credit classes.
     * @param schedule Schedule to add the Course(s) to
     * @param courseToAdd Course that is used to determine which courses to add
     * @param resultList List of search results containing the Course(s) to add
     */
    public void addToSchedule(Schedule schedule, Course courseToAdd, List<Course> resultList) {
        for (Course c : resultList) {
            if (c.equals(courseToAdd)) {
                schedule.getCourses().add(c);
                schedule.setCredits();
            }
        }
    }

    /**
     * Checks course against List of courses to see if there are any overlaps
     * @param course Course that is to be added
     * @param overlapList Current list of courses that may overlap with Course
     * @return true if overlap exists, false if there are no overlaps
     */
    public boolean checkForOverlap(Course course, List<Course> overlapList) {
        for (Course overlapCourse : overlapList) {
            if (TimeSlot.dayOverlap(course.getTimeSlot(), overlapCourse.getTimeSlot()) && coursesOverlap(course, overlapCourse)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns true if courses overlap, false otherwise
     * @param c1 Course for evaluation
     * @param c2 Course for evaluation
     * @return True if courses overlap, false otherwise
     */
    public Boolean coursesOverlap(Course c1, Course c2) {
        int c1Begin = c1.getTimeSlot().getBeginTimeCode();
        int c1End = c1.getTimeSlot().getEndTimeCode();
        int c2Begin = c2.getTimeSlot().getBeginTimeCode();
        int c2End = c2.getTimeSlot().getEndTimeCode();

        boolean overlap = false;

        if (c1Begin == c2Begin || c1End == c2End) {
            //if they equal, they overlap
            overlap = true;
        } else if (c1Begin > c2Begin && c1Begin < c2End) {
            //if start time is in between
            overlap = true;
        } else if (c1End > c2Begin && c1End < c2End) {
            //end time is in between
            overlap = true;
        } else if (c1Begin < c2Begin && c1End > c2End) {
            //Surrounds
            overlap = true;
        }
        return overlap;
    }

    /**
     * Takes in and identifier and searches based on input
     * @param identifier defines what method to search by
     * @param input      string that is used to search
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
        return searchList.stream()
                .filter(course -> course.getCrs_title().contains(input))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This method takes in user input and searches the course list based on the course code
     * that is inputted
     * @param input- user input (course code) as a string
     * @return - course list including courses with the course name specified by the user
     */
    public ArrayList<Course> searchCourseCode(String input, ArrayList<Course> searchList) {
        return searchList.stream()
                .filter(course -> course.getCrs_code().contains(input))
                .collect(Collectors.toCollection(ArrayList::new));
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
            TimeSlot timeSlot = course.getTimeSlot();
            if(input.equals("M") || input.equals("MON") || input.equals("MONDAY")){
                if (timeSlot.isOnMonday()) {
                    c.add(course);
                }
            }

            if(input.equals("T") || input.equals("TUES") || input.equals("TUESDAY")){
                if (timeSlot.isOnTuesday()) {
                    c.add(course);
                }
            }

            if(input.equals("W") || input.equals("WED") || input.equals("WEDNESDAY")){
                if (timeSlot.isOnWednesday()) {
                    c.add(course);
                }
            }

            if(input.equals("R") || input.equals("THURS") || input.equals("THURSDAY")){
                if (timeSlot.isOnThursday()) {
                    c.add(course);
                }
            }

            if(input.equals("F") || input.equals("FRI") || input.equals("FRIDAY")){
                if (timeSlot.isOnFriday()) {
                    c.add(course);
                }
            }
        }
        return c;
    }

    /**
     * This method takes in user input and searches the course list based on the course begin time and end time
     * that is input
     * @param input- user input (course begin time) as a string
     * @return - course list including courses with the course name specified by the user
     */
    private ArrayList<Course> searchCourseTime(String input, ArrayList<Course> searchList) {
        return searchList.stream()
                .filter(course -> course.getBegin_tim().contains(input) || course.getEnd_tim().contains(input))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Course> filterCourses(Filterable filter, ArrayList<Course> courses) {
        ArrayList<Filterable> filterList = getFilters();
        if (filterList.contains(filter)) {
            return courses;
        } else {
            filterList.add(filter);
        }

        return filter.filter(courses);
    }

    /**
     * Clears previously used filters
     */
    public void clearFilters() {
        filters = new ArrayList<>();
    }

    /**
     * Probably will need in future
     * Used so that we don't have to create a new schedule every time we want to search
     * Will be useful when our search is a page with a searchbar instead of current implementation
     */
    public void resetSearch() {
        clearFilters();
        results = initFilters(currentSchedule.getSemester(), currentSchedule.getYear());
    }
}
