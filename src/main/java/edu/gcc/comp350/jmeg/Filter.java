package edu.gcc.comp350.jmeg;

public class Filter {
    enum FilterTypes {
        YEAR, TERM,
    }

    private final FilterTypes filterType;
    private boolean active;
    private final String filterName;

    public Filter(String filterType, String filterName){
        this.filterName = filterName;
        this.filterType = FilterTypes.valueOf(filterType.toUpperCase());
    }



    public FilterTypes getFilterType() {
        return filterType;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Filter)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Filter filter = (Filter) obj;

        return filter.filterType == this.filterType;
    }
}
