package edu.gcc.comp350.jmeg;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FilterTerm implements Filterable{

    private final int term;

    public FilterTerm (int term) {
        this.term = term;
    }

    @Override
    public ArrayList<Course> filter(ArrayList<Course> courses) {
        return courses.stream()
                .filter(c -> c.getTrm_code() == term)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FilterTerm;
    }
}
