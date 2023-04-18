package edu.gcc.comp350.jmeg;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FilterYear implements Filterable {
    private final int year;

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
