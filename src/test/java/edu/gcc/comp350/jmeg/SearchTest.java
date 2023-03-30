package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    static Search search;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeAll
    static void setup() {
        if (Main.getCourses().isEmpty()) {
            IO.getInstance().importCSVData();
        }

        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    void prelim() {
        Schedule schedule = new Schedule("TESTING", 0);
        search = new Search(schedule);
    }

    @Test
    void searchInteraction() {
    }

    @Test
    void search() {
        assertNull(search.search("","", new ArrayList<>()));
    }

    @Test
    void searchCourseName() {
        ArrayList<Course> courses = search.search("NAME", "COMP", Main.getCourses());

        assertEquals("COMP PROGRAMMING I", courses.get(0).getCrs_title());
        assertEquals("COMPARATIVE POLITICS", courses.get(courses.size()-1).getCrs_title());
    }

    @Test
    void searchCourseCode() {
        ArrayList<Course> courses = search.search("CODE", "201", Main.getCourses());

        assertEquals(courses.size(), courses.stream().filter(c -> c.getCrs_code().contains("201")).count());
        assertEquals("PRINCIPLES OF ACCOUNTING I", courses.get(0).getCrs_title());
        assertEquals("INTERMEDIATE SPANISH I", courses.get(courses.size()-1).getCrs_title());
    }

    @Test
    void searchCourseDayMonday() {
        boolean onMonday = true;
        ArrayList<Course> courses = search.search("DAY", "M", Main.getCourses());
        for (Course c : courses) {
            if (!c.getMonday_cde().equals("M")) {
                onMonday = false;
                break;
            }
        }

        assertTrue(onMonday);
        assertEquals("PRINCIPLES OF ACCOUNTING I", courses.get(0).getCrs_title());
        assertEquals("SCREENWRITING", courses.get(courses.size()-1).getCrs_title());

        courses = search.search("DAY", "MON", Main.getCourses());
        for (Course c : courses) {
            if (!c.getMonday_cde().equals("M")) {
                onMonday = false;
                break;
            }
        }

        assertTrue(onMonday);

        courses = search.search("DAY", "MONDAY", Main.getCourses());
        for (Course c : courses) {
            if (!c.getMonday_cde().equals("M")) {
                onMonday = false;
                break;
            }
        }

        assertTrue(onMonday);
    }

    @Test
    void searchCourseTime() {
        ArrayList<Course> courses = search.search("TIME", "12PM", Main.getCourses());

        boolean isTime = true;

        for (Course c : courses) {
            isTime = c.getBegin_tim().contains("12PM");
        }

        assertTrue(isTime);

        assertEquals("PRINCIPLES OF ACCOUNTING I", courses.get(0).getCrs_title());
        assertEquals("SCIENCE  FAITH  AND TECHNOLOGY", courses.get(courses.size()-1).getCrs_title());
    }

    @Test
    void searchBadIdentifier() {
        ArrayList<Course> courses = search.search("TEST", "", Main.getCourses());

        assertNull(courses);
    }



    @Test
    void filterCoursesYear() {
        Filter filter = new Filter("YEAR", "2018");

        ArrayList<Course> courses = search.filterCourses(filter, Main.getCourses());

        assertEquals(courses.size(), courses.stream().filter(c -> c.getYr_code() == 2018).count());

    }

    @Test
    void filterCoursesTerm() {
        Filter filter = new Filter("TERM", "FALL");

        ArrayList<Course> courses = search.filterCourses(filter, Main.getCourses());

        assertEquals(courses.size(), courses.stream().filter(c -> c.getTrm_code() == 10).count());
    }

    @Test
    void filterCoursesTwice() {
        Filter filter = new Filter("YEAR", "2018");
        Filter filter2 = new Filter("YEAR", "2019");

        ArrayList<Course> courses = search.filterCourses(filter, Main.getCourses());
        courses = search.filterCourses(filter2, courses);

        assertEquals(courses.size(), courses.stream().filter(c -> c.getYr_code() == 2018).count());
        assertEquals("Already filtered by year", outContent.toString().trim());
    }
}