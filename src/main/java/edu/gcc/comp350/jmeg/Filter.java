package edu.gcc.comp350.jmeg;

import java.util.ArrayList;

public class Filter {
    private boolean active;
    private String filterName;
    private ArrayList<String> filterValues;

    public Filter(){

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
