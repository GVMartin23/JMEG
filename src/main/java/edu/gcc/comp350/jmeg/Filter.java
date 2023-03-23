package edu.gcc.comp350.jmeg;

import java.util.ArrayList;

public class Filter {
    enum FilterTypes {
        YEAR, TERM,
    }

    private FilterTypes filterType;
    private boolean active;
    private String filterName;
    private ArrayList<String> filterValues;

    public Filter(String filterType, String filterName){
        this.filterName = filterName;
        this.filterType = FilterTypes.valueOf(filterType.toUpperCase());
    }



    public FilterTypes getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterTypes filterType) {
        this.filterType = filterType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public ArrayList<String> getFilterValues() {
        return filterValues;
    }

    public void setFilterValues(ArrayList<String> filterValues) {
        this.filterValues = filterValues;
    }
}
