package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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


}