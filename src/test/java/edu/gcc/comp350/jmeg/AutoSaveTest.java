package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutoSaveTest {

    @Test
    void testRunStop() {
        Thread thread = new Thread(new AutoSave(60));
        thread.start();

        thread.interrupt();

        assertTrue(thread.isInterrupted());
    }

    @Test
    void testRunStopBool() throws InterruptedException {
        AutoSave save = new AutoSave(1);
        Thread thread = new Thread(save);
        thread.start();
        save.setRun(false);


        Thread.sleep(5000);

        assertFalse(thread.isAlive());
    }
}