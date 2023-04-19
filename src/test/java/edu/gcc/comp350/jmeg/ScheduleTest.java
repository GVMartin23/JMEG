package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    static User user;
    Schedule schedule;

    @BeforeAll
    static void init() {
        if (Main.getCourses().isEmpty()) {
            IO.getInstance().importCSVData();
        }
        user = new User("Tester", "", "");
    }

    @BeforeEach
    void prelim() {
        schedule = new Schedule(user, "Test");
    }

    @Test
    void switchSection() {
        schedule.getCourses().add(Main.getCourses().get(345));
        schedule.switchSection(Main.getCourses().get(345), Main.getCourses().get(346));

        assertTrue(schedule.getCourses().contains(Main.getCourses().get(346)));
        assertFalse(schedule.getCourses().contains(Main.getCourses().get(345)));
    }
}