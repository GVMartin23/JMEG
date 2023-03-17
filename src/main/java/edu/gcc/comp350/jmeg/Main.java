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

    public static ArrayList<Course> courses;

    public static ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public static void setSchedules(ArrayList<Schedule> schedules) {
        Main.schedules = schedules;
    }

    public static ArrayList<Course> getCourses(){
        return courses;
    }

    public static void setCourses(ArrayList<Course> courses) {
        Main.courses = courses;
    }

    public static void main(String[] args) {
        //testing to make sure each course is declared and in the course arraylist
        testCSV();


    }

    /**
     * Uses the loadCSV() function to retrieve the data on each course in the CSV
     * Loops through each row and declares it as a course then adds it to the course
     * arraylist
     */
    public static void testCSV(){
        try {
            List<String[]> list = loadCSV();
            list.remove(0);
            for(String[] str : list) {
                //System.out.println("TEST"+str[0]+"TEST");
                System.out.println(Arrays.asList(str));
                if(str[0].isEmpty()){
                    str[0] = "0";
                }
                int yr_code = Integer.parseInt(str[0]);
                if(str[1].isEmpty()){
                    str[1] = "0";
                }
                int trm_code = Integer.parseInt(str[1]);
                String crs_code = str[2];
                String crs_comp1 = str[3];
                String crs_comp2 = str[4];
                String crs_comp3 = str[5];
                String crs_title = str[6];
                if(str[7].isEmpty()){
                    str[7] = "0";
                }
                int credit_hrs = Integer.parseInt(str[7]);
                String x_listed_parnt_crs = str[8];
                String acad_credit_varies = str[9];
                String acad_credit_label = str[10];
                if(str[11].isEmpty()){
                    str[11] = "0";
                }
                int crs_capacity = Integer.parseInt(str[11]);
                if(str[12].isEmpty()){
                    str[12] = "0";
                }
                int max_capacity = Integer.parseInt(str[12]);
                if(str[13].isEmpty()){
                    str[13] = "0";
                }
                int crs_enrollment = Integer.parseInt(str[13]);
                String bldg_cde = str[14];
                String room_cde = str[15];
                String monday_cde = str[16];
                String tuesday_cde = str[17];
                String wednesday_cde = str[18];
                String thursday_cde = str[19];
                String friday_cde = str[20];
                String begin_tim = str[21];
                String end_tim = str[22];
                String last_name = str[23];
                String first_name = str[24];
                String preferred_name = " ";
                if(str.length == 26){
                    preferred_name = str[25];
                }
                String comment_txt = " ";
                if(str.length == 27){
                    comment_txt = str[26];

                }

                if (courses == null) {
                    courses = new ArrayList<>();
                }


                Course course = new Course(yr_code, trm_code, crs_code,
                        crs_comp1, crs_comp2, crs_comp3, crs_title,
                        credit_hrs, x_listed_parnt_crs, acad_credit_varies,
                        acad_credit_label, crs_capacity, max_capacity, crs_enrollment,
                        bldg_cde, room_cde, monday_cde, tuesday_cde, wednesday_cde,
                        thursday_cde, friday_cde, begin_tim, end_tim, last_name, first_name,
                        preferred_name, comment_txt);
                courses.add(course);


            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method loads the 2018-2019 csv in the project
     * Reads each line/row and delimits by commas
     * @return string list of courses (taken from the csv)
     * @throws IOException
     */
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
            //sb.append(c.getCourseCode()).append(",");
        }

        return sb.substring(0, sb.length() - 1) + "\n";
    }
}
