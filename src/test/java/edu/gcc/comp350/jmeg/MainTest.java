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
    void loadScheduleCorrect() throws IOException {
        saveSchedule();

        Main.loadSchedule();

        ArrayList<Schedule> schedules = Main.getSchedules();

        ArrayList<String> titles = new ArrayList<>();

        for (Schedule schedule : schedules) {

        }

        assertEquals("Test schedule", schedules.get(0).getTitle());
    }


}