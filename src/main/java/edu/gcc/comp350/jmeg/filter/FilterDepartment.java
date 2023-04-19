package edu.gcc.comp350.jmeg.filter;

import edu.gcc.comp350.jmeg.Course;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class for Filtering by different departments
 * Implements Filterable
 */
public class FilterDepartment implements Filterable{

    private String departmentCode;

    /**
     * Constructor for FilterDepartment
     * @param departmentCode String to filter by
     */
    public FilterDepartment(String departmentCode) {
        this.departmentCode = departmentCode.toUpperCase().strip();
    }

    /**
     * Implementation of Filterable in FilterDepartment
     * @param courses ArrayList of Courses to filter
     * @return ArrayList of Courses filtered by Course.getCrs_comp1()
     */
    @Override
    public ArrayList<Course> filter(ArrayList<Course> courses) {
        return courses.stream()
                .filter(c -> c.getCrs_comp1().equals(departmentCode))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Checks if compared object is a FilterDepartment class
     * @param obj Object to equate
     * @return if same instance, return true, else false
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof FilterDepartment;
    }
}
