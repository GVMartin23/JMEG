package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @BeforeAll
    static void init() {
        if (Main.getCourses().isEmpty()) {
            IO.getInstance().importCSVData();
        }
    }

    @Test
    void succinctCourse() {
        Course c = Main.getCourses().get(0);
        String succinctReal = c.getYr_code() +
                "| Title: " + c.getCrs_title() +
                "| Code: " + c.getCrs_code() + "\n";
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c);
        String produced = Course.succinctCourse(courses);
        assertEquals(succinctReal, produced);
    }
}