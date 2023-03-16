package edu.gcc.comp350.jmeg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Scanner;



public class Main {
    private static ArrayList<Schedule> schedules;

    private static ArrayList<Course> courses;

    public static ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public static void setSchedules(ArrayList<Schedule> schedules) {
        Main.schedules = schedules;
    }

    public static void main(String[] args) {

        try {
            List<String[]> list = loadCSV();

        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public static List<String[]> loadCSV() throws IOException{
        String csvFile = "2018-2019.csv";
        List<String[]> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            String line = "";
            while((line = br.readLine()) != null){
                courses.add(line.split(","));
                //System.out.println(line);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return courses;

    }
private static Schedule createSchedule(){
    return null;
}
private static void loadSchedule(Schedule s){

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
