package edu.gcc.comp350.jmeg;

/**
 * Class for setting up an AutoSave feature
 * Implements Runnable
 * @author Garrett Martin
 */
public class AutoSave implements Runnable{

    private final int numSeconds;
    private boolean run = true;

    /**
     * Implementation of run from Runnable, allowing it to be run on a separate thread.
     * Saves all schedules every 1 minute.
     */
    @Override
    public void run() {
        IO io = IO.getInstance();
        while (run) {
            try {
                Thread.sleep(numSeconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Schedule schedule : Main.getSchedules()) {
                io.saveSchedule(schedule);
            }
        }
    }

    public AutoSave(int numSeconds) {
        this.numSeconds = numSeconds;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
