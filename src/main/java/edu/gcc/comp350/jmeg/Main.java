package edu.gcc.comp350.jmeg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class Main {
    private static ArrayList<Schedule> schedules;
    private static ArrayList<Course> courses;
    private static Schedule currentSchedule;

    public static void setCourses(ArrayList<Course> courses) {
        Main.courses = courses;
    }

    public static ArrayList<Course> getCourses() {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        return courses;
    }

    public static ArrayList<Schedule> getSchedules() {
        if (schedules == null) {
            schedules = new ArrayList<>();
        }
        return schedules;
    }

    public static Schedule getCurrentSchedule() throws Exception {
        if (currentSchedule == null) {
            throw new Exception("No current selected Schedule");
        }
        return currentSchedule;
    }

    /**
     * Allows for currentSchedule to be changed
     * If Schedule is in list of schedules, just selects that schedule from the list
     * Else, adds the sent in schedule to the list and sets it to currentSchedule
     * @param schedule Schedule to set to currentSchedule
     */
    public static void setCurrentSchedule(Schedule schedule) {
        if (schedules.contains(schedule)) {
            currentSchedule = schedules.get(schedules.indexOf(schedule));
        } else {
            schedules.add(schedule);
            currentSchedule = schedule;
        }

    }


    public static void main(String[] args) throws Exception {
        IO io = IO.getInstance();
        io.importCSVData();//Load courses into arrayList from CSV
        io.loadSchedules();//Load saved schedules
        getSchedules();
        AutoSave save = new AutoSave(60);
        Thread autoSave = new Thread(save);
        autoSave.start();
        SpringApplication.run(Main.class, args);
    }
}
