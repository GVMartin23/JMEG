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

    @Test
    void getCode() {
        TimeSlot slot = new TimeSlot();
        assertEquals(24*60, slot.getCode("12:00AM"));
        assertEquals(12*60, slot.getCode("12:00PM"));
        assertEquals(13 * 60 + 50, slot.getCode("1:50PM"));
        assertEquals(8 * 60, slot.getCode("8:00AM"));
    }
}