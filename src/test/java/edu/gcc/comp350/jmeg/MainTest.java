package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testSaveSchedule() {
        Schedule schedule = new Schedule("TITLE", 0);
        User user = new User("Joe" , "MAJOR", "MINOR", 1);
        schedule.setUser(user);
        ArrayList<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setCrs_code("CS1");
        courses.add(course);
        schedule.setCourses(courses);

        Main.saveSchedule(schedule);

        File directory = new File(System.getProperty("user.dir"));

        File[] schedules = directory.listFiles((dir, name) -> name.endsWith(".csv") && !Main.isDataCSV(name));

        ArrayList<String> titles = new ArrayList<>();
        for (File file : schedules) {
            titles.add(file.getName());
        }

        assertTrue(titles.contains("TITLE_Joe.csv"));
    }

    @Test
    void loadScheduleCorrect() throws IOException {
        testSaveSchedule();

        Main.loadSchedule();

        ArrayList<Schedule> schedules = Main.getSchedules();

        ArrayList<String> titles = new ArrayList<>();

        for (Schedule schedule : schedules) {
            titles.add(schedule.getTitle());
        }

        assertTrue(titles.contains("TITLE"));
    }


    @Test
    void testCSV() {
        Main.testCSV();

        HashSet<Integer> termCodes = new HashSet<>();

        for (Course c : Main.getCourses()) {
            termCodes.add(c.getTrm_code());
        }

        //Only 2 termCodes currently, so should be 2
        assertEquals(2, termCodes.size());
    }

    @Test
    void loadCSV() throws IOException {
        ArrayList<String[]> courses = Main.loadCSV();

        ArrayList<String> courseCodes = new ArrayList<>();

        for (String[] course : courses) {
            courseCodes.add(course[2]);
        }

        //Ensures that courseCodes contains one of course codes in csv
        assertTrue(courseCodes.contains("COMP 441  A"));
    }
}