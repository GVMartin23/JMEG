package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    Schedule schedule;

    @BeforeAll
    static void init() {
        if (Main.getCourses().isEmpty()) {
            IO.getInstance().importCSVData();
        }
    }

    @BeforeEach
    void prelim() {
        schedule = new Schedule("Test", 0, "SPRING", 2019);
    }

    @Test
    void switchSection() {
        schedule.getCourses().add(Main.getCourses().get(345));
        schedule.switchSection(Main.getCourses().get(345), Main.getCourses().get(346));

        assertTrue(schedule.getCourses().contains(Main.getCourses().get(346)));
        assertFalse(schedule.getCourses().contains(Main.getCourses().get(345)));
    }
}