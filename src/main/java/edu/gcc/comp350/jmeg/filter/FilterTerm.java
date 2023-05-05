package edu.gcc.comp350.jmeg.filter;

import edu.gcc.comp350.jmeg.Course;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class for filtering Courses by term
 * Implements: Filterable
 * @author Garrett Martin
 */
public class FilterTerm implements Filterable {

    private final int term;

    /**
     * Constructor for FilterTerm
     * @param term term code to filter by
     */
    public FilterTerm (int term) {
        this.term = term;
    }

    /**
     * Implementation of Filterable filters by termCode for each course
     * @param courses ArrayList of Courses to filter by
     * @return Filtered List of Courses
     */
    @Override
    public ArrayList<Course> filter(ArrayList<Course> courses) {
        return courses.stream()
                .filter(c -> c.getTrm_code() == term)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Equals method for FilterTerm
     * @param obj object to compare
     * @return True if obj is same instance as FilterTerm
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof FilterTerm;
    }
}
