package edu.gcc.comp350.jmeg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Schedule> schedules;

    public static ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public static void setSchedules(ArrayList<Schedule> schedules) {
        Main.schedules = schedules;
    }

    public static void main(String[] args) {

    }
    private static Schedule createSchedule(){
        return null;
    }


    /**
     * Loads saved schedules into memory
     * Looks in working directory (Not Final)
     */
    private static void loadSchedule(){
        //Current working directory
        File directory = new File(System.getProperty("user.dir"));

        //lambda sorting files in directory by those that are csv files
        File[] schedules = directory.listFiles((dir, name) -> name.endsWith(".csv"));

        //Load each schedule individually
        for (File file : schedules) {

        }
    }

    /**
     * Writes schedule to csv, saving the data
     * Names csv with "schedule.title _ user.name .csv"
     * First line is values of schedule, formatted by schedule.toString()
     * Second line is values of course, formatted by course.toString()
     *
     * @param schedule schedule to write to csv
     */
    public static void saveSchedule(Schedule schedule) {
        String fileName = schedule.getTitle() + "_" + schedule.getUser().getName() + ".csv";

        try {
            try (PrintWriter pw = new PrintWriter(new File(fileName))) {
                
                pw.write( formatScheduleCSV(schedule));
                pw.write(formatUserCSV(schedule.getUser()));

                pw.write(formatCourseCSV(schedule.getCourses()));
            }
        } catch (FileNotFoundException fne) {
            System.out.println("Error, file not found");
        }
    }

    /**
     * CSV formats schedule data
     * @param schedule schedule to format
     * @return string of schedule variables in csv format
     */
    private static String formatScheduleCSV(Schedule schedule) {
        return schedule.getTitle() + "," +
                schedule.getCredits() + "\n";
    }

    /**
     * CSV formats User data
     * @param user user to format
     * @return string of user variables in csv format
     */
    private static String formatUserCSV(User user) {
        return user.getName() + "," +
                user.getMajor() + "," +
                user.getMinor() + "," +
                user.getYear() + "\n";
    }

    /**
     * CSV formats a list of courses by course code
     * @param Courses list of courses to format
     * @return string of csv formated course codes
     */
    private static String formatCourseCSV(ArrayList<Course> Courses) {
        StringBuilder sb = new StringBuilder();
        for (Course c : Courses) {
            sb.append(c.getCourseCode()).append(",");
        }

        return sb.substring(0, sb.length() - 1) + "\n";
    }
}
