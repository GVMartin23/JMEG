package edu.gcc.comp350.jmeg;

import java.util.ArrayList;

/**
 * Interface for classes that can filter courses
 */
public interface Filterable {
    ArrayList<Course> filter(ArrayList<Course> courses);
}
