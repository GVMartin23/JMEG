package edu.gcc.comp350.jmeg.filter;

import edu.gcc.comp350.jmeg.Course;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class for filtering Courses by year
 * Implements: Filterable
 * @author Garrett Martin
 */
public class FilterYear implements Filterable {
    private final int year;

    /**
     * Constructor for FilterYear
     * @param year year to filter by
     */
    public FilterYear (int year) {
        this.year = year;
    }

    @Override
    public ArrayList<Course> filter(ArrayList<Course> courses) {
        return courses.stream()
                .filter(c -> c.getYr_code() == year)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FilterYear;
    }
}
