package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {
    @BeforeAll
    static void init() {
        if (Main.getCourses().isEmpty()) {
            IO.getInstance().importCSVData();
        }
    }

    @Test
    void dayOverlap() {
        Course c1 = Main.getCourses().get(2674);
        Course c2 = Main.getCourses().get(2675);
        // 2 overlapping days
        assertTrue(TimeSlot.dayOverlap(c1.getTimeSlot(), c2.getTimeSlot()));

        c1 = Main.getCourses().get(2676);
        //Non overlapping
        assertFalse(TimeSlot.dayOverlap(c1.getTimeSlot(), c2.getTimeSlot()));

    }
}