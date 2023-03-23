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
        assertNull(search.search("",""));
    }

    @Test
    void searchCourseName() {
        ArrayList<Course> courses = search.search("NAME", "COST ACCOUNTING");

        assertEquals(courses.get(0).getCrs_title(), "COST ACCOUNTING");
    }

    @Test
    void searchCourseCode() {
        HashSet<String> courseCodes = new HashSet<>();

        for (Course c : search.search("CODE", "INBS")) {
            courseCodes.add(c.getCrs_comp1());
        }



        assertEquals(1, courseCodes.size());
    }

    @Test
    void searchCourseDayMonday() {
        boolean onMonday = true;
        for (Course c : search.search("DAY", "M")) {
            if (!c.getMonday_cde().equals("M")) {
                onMonday = false;
                break;
            }
        }

        assertTrue(onMonday);

        onMonday = true;
        for (Course c : search.search("DAY", "MON")) {
            if (!c.getMonday_cde().equals("M")) {
                onMonday = false;
                break;
            }
        }

        assertTrue(onMonday);

        onMonday = true;
        for (Course c : search.search("DAY", "MONDAY")) {
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

        for (Course c : search.search("TIME", "8")) {
            courseTimes.add(c.getBegin_tim());
        }

        System.out.println(courseTimes);

        assertEquals(1, courseTimes.size());
    }
}