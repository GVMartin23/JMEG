package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void saveSchedule() {
        Schedule schedule = new Schedule("TITLE", 0);
        User user = new User("Joe" , "MAJOR", "MINOR", 1);
        schedule.setUser(user);
        ArrayList<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setCrs_code("CS1");
        courses.add(course);
        schedule.setCourses(courses);

        Main.saveSchedule(schedule);
    }

    @Test
    void loadSchedule() throws IOException {
        Course course = new Course();
        course.setCrs_code("CS1");
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course);
        Main.setCourses(courses);

        Main.loadSchedule();

        ArrayList<Schedule> schedules = Main.getSchedules();

        assertEquals("Test schedule", schedules.get(0).getTitle());
    }
}