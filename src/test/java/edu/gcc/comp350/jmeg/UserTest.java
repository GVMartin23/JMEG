package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testEquals() {
        User base = new User("Garrett", "CS", "");
        User clone = new User("Garrett", "CS", "");
        User diffName = new User("G", "CS", "");
        User diffMajor = new User("Garrett", "", "");
        User diffMinor = new User("Garrett", "CS", "AI");
        TimeSlot diffObject = new TimeSlot();

        assertEquals(base, clone);
        assertEquals(base, base);
        assertNotEquals(base, diffName);
        assertNotEquals(base, diffMajor);
        assertNotEquals(base, diffMinor);
        assertNotEquals(base, diffObject);
    }
}