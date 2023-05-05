package edu.gcc.comp350.jmeg.filter;

import edu.gcc.comp350.jmeg.Course;

import java.util.ArrayList;

/**
 * Interface for classes that can filter courses
 * @author Garrett Martin
 */
public interface Filterable {
    ArrayList<Course> filter(ArrayList<Course> courses);
}
