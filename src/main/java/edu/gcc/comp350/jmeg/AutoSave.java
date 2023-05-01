package edu.gcc.comp350.jmeg;

public class AutoSave implements Runnable{

    private final int numSeconds;
    private boolean run = true;

    @Override
    public void run() {
        IO io = IO.getInstance();
        while (run) {
            for (Schedule schedule : Main.getSchedules()) {
                io.saveSchedule(schedule);
            }
            try {
                Thread.sleep(numSeconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
