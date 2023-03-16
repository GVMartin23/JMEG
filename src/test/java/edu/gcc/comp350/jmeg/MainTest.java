package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void saveSchedule() {
        Schedule schedule = new Schedule();
        User user = new User("Joe" , "MAJOR", "MINOR", 1);
        schedule.setUser(user);
        schedule.setTitle("TITLE");
        ArrayList<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setCourseCode("CS1");
        courses.add(course);
        schedule.setCourses(courses);

        Main.saveSchedule(schedule);
    }
}