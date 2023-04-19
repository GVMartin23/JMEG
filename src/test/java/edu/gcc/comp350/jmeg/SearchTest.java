package edu.gcc.comp350.jmeg;

import edu.gcc.comp350.jmeg.filter.FilterTerm;
import edu.gcc.comp350.jmeg.filter.FilterYear;
import edu.gcc.comp350.jmeg.filter.Filterable;
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
    private Schedule schedule;

    @BeforeAll
    static void setup() {
        if (Main.getCourses().isEmpty()) {
            IO.getInstance().importCSVData();
        }
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    void prelim() {
        schedule = new Schedule("TESTING", 0, "FALL", 2018);
        search = new Search(schedule);
        search.clearFilters();
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
        ArrayList<Course> courses = search.search("TIME", "12:00PM", Main.getCourses());

        boolean isTime = true;

        for (Course c : courses) {
            isTime = c.getBegin_tim().contains("12:00PM");
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
        Filterable filter = new FilterYear(2018);

        ArrayList<Course> courses = search.filterCourses(filter, Main.getCourses());

        assertEquals(courses.size(), courses.stream().filter(c -> c.getYr_code() == 2018).count());

    }

    @Test
    void filterCoursesTerm() {
        Filterable filter = new FilterTerm(10);

        ArrayList<Course> courses = search.filterCourses(filter, Main.getCourses());

        assertEquals(courses.size(), courses.stream().filter(c -> c.getTrm_code() == 10).count());
    }

    @Test
    void filterCoursesTwice() {
        Filterable filter = new FilterYear(2018);
        Filterable filter2 = new FilterYear(2019);

        ArrayList<Course> courses = search.filterCourses(filter, Main.getCourses());

        assertEquals(courses.size(), courses.stream().filter(c -> c.getYr_code() == 2018).count());
        assertEquals(search.filterCourses(filter2, courses), courses);
    }

    @Test
    void overLapTimeTest() {
        Course c1 = Main.getCourses().get(2671);
        Course c2 = Main.getCourses().get(2673);
        //2 different timed classes
        assertFalse(search.coursesOverlap(c1, c2));


        c1 = Main.getCourses().get(2673);
        //Same class, so should overlap
        assertTrue(search.coursesOverlap(c1, c2));
    }

    @Test
    void addToSchedule() {
        Course c = Main.getCourses().get(407);
        schedule.setCourses(new ArrayList<>());
        Filterable filter = new FilterYear(2018);
        Filterable filter2 = new FilterTerm(10);

        ArrayList<Course> courses = search.filterCourses(filter, Main.getCourses());
        courses = search.filterCourses(filter2, courses);

        search.addToSchedule(schedule, c, courses);

        assertEquals(schedule.getCourses().size(), 2);

    }

    @Test
    void checkForOverlap() {
        Course c = Main.getCourses().get(407);
        Course c2 = Main.getCourses().get(409);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c);
        schedule.setCourses(courses);

        assertTrue(search.checkForOverlap(c, schedule.getCourses()));

        assertFalse(search.checkForOverlap(c2, schedule.getCourses()));
    }
}