package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class IOTest {
    private static IO io;

    @BeforeAll
    static void init() {
        io = IO.getInstance();
    }

    @Test
    void importCSVData() {
        Main.setCourses(new ArrayList<>());
        io.importCSVData();

        //Only 2 termCodes currently, so should be 2
        assertEquals(4526, Main.getCourses().size());
    }

    @Test
    void loadSchedules() {
        saveSchedule();

        io.loadSchedules();

        ArrayList<Schedule> schedules = Main.getSchedules();

        ArrayList<String> titles = new ArrayList<>();

        for (Schedule schedule : schedules) {
            titles.add(schedule.getTitle());
        }

        assertTrue(titles.contains("TITLE"));
    }

    @Test
    void saveSchedule() {
        Schedule schedule = new Schedule("TITLE", 0, "Fall", 2018);
        User user = new User("Joe" , "MAJOR", "MINOR");
        schedule.setUser(user);
        ArrayList<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setCrs_code("CS1");
        courses.add(course);
        schedule.setCourses(courses);

        io.saveSchedule(schedule);

        File directory = new File(System.getProperty("user.dir"));

        File[] schedules = directory.listFiles((dir, name) -> name.endsWith(".csv") && !io.isDataCSV(name));

        ArrayList<String> titles = new ArrayList<>();
        for (File file : schedules) {
            titles.add(file.getName());
        }

        assertTrue(titles.contains("TITLE_Joe.csv"));
    }
}