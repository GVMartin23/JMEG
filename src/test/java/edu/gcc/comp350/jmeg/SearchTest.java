package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    static Search search;

    @BeforeAll
    static void setup() {
        Schedule schedule = new Schedule("TESTING", 0);
        Main.testCSV();
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
        ArrayList<Course> courses = search.search("NAME", "COST ACCOUNTING", Main.getCourses());

        assertEquals(courses.get(0).getCrs_title(), "COST ACCOUNTING");
    }

    @Test
    void searchCourseCode() {
        HashSet<String> courseCodes = new HashSet<>();

        for (Course c : search.search("CODE", "INBS", Main.getCourses())) {
            courseCodes.add(c.getCrs_comp1());
        }



        assertEquals(1, courseCodes.size());
    }

    @Test
    void searchCourseDayMonday() {
        boolean onMonday = true;
        for (Course c : search.search("DAY", "M", Main.getCourses())) {
            if (!c.getMonday_cde().equals("M")) {
                onMonday = false;
                break;
            }
        }

        assertTrue(onMonday);

        onMonday = true;
        for (Course c : search.search("DAY", "MON", Main.getCourses())) {
            if (!c.getMonday_cde().equals("M")) {
                onMonday = false;
                break;
            }
        }

        assertTrue(onMonday);

        onMonday = true;
        for (Course c : search.search("DAY", "MONDAY", Main.getCourses())) {
            if (!c.getMonday_cde().equals("M")) {
                onMonday = false;
                break;
            }
        }

        assertTrue(onMonday);
    }

    @Test
    void searchCourseTime() {
        ArrayList<String> courseTimes = new ArrayList<>();

        for (Course c : search.search("TIME", "8", Main.getCourses())) {
            courseTimes.add(c.getBegin_tim());
        }

        System.out.println(courseTimes);

        assertEquals(1, courseTimes.size());
    }

    @Test
    void filterCoursesYear() {
        Filter filter = new Filter("YEAR", "2018");

        search.filterCourses(filter, Main.getCourses());

        assertEquals(1506, Main.getCourses().size());

    }

    @Test
    void filterCoursesTerm() {
        Filter filter = new Filter("TERM", "FALL");

        ArrayList<Course> courses = search.filterCourses(filter, Main.getCourses());

        assertEquals(765, courses.size());

    }
}