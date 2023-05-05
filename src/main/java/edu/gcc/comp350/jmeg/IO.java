package edu.gcc.comp350.jmeg;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class IO {
    private static IO io;

    private IO() {
    }


    public static IO getInstance() {
        if (io == null) {
            io = new IO();
        }

        return io;
    }

    /**
     * Uses the loadCSV() function to retrieve the data on each course in the CSV
     * Loops through each row and declares it as a course then adds it to the course
     * arraylist
     */
    public void importCSVData() {
        try {
            ArrayList<String[]> list = loadCSV();
            list.remove(0);
            for(String[] str : list) {
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
                if(str[8].isEmpty() || str[8].equals(" ")){
                    str[8] = "0";
                }
                int crs_capacity = Integer.parseInt(str[8]);
                if(str[9].isEmpty()){
                    str[9] = "0";
                }
                int crs_enrollment = Integer.parseInt(str[9]);
                String monday_cde = str[10];
                String tuesday_cde = str[11];
                String wednesday_cde = str[12];
                String thursday_cde = str[13];
                String friday_cde = str[14];
                String begin_tim = str[15];
                String end_tim = str[16];
                String last_name = str[17];
                String first_name = str[18];
                String preferred_name = "";
                if(str.length == 20){
                    preferred_name = str[19];
                }
                String comment_txt = "";
                if(str.length == 21){
                    comment_txt = str[20];

                }

                ArrayList<Course> courseList = Main.getCourses();

                Course course = new Course(yr_code, trm_code, crs_code,
                        crs_comp1, crs_comp2, crs_comp3, crs_title,
                        credit_hrs, crs_capacity, crs_enrollment, monday_cde,
                        tuesday_cde, wednesday_cde, thursday_cde, friday_cde,
                        begin_tim, end_tim, last_name, first_name,
                        preferred_name, comment_txt);
                courseList.add(course);

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method loads the 2018-2019 csv in the project
     * Reads each line/row and delimits by commas
     * @return string list of courses (taken from the csv)
     * @throws IOException if reading file fails
     */
    private ArrayList<String[]> loadCSV() throws IOException {
        String csvFile = "UnifiedCSV.csv";
        ArrayList<String[]> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            String line;
            while((line = br.readLine()) != null){
                courses.add(line.split(","));
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Loads saved schedules into memory
     * Looks in working directory (Not Final)
     */
    public void loadSchedules() {
        //Current working directory
        File directory = new File(System.getProperty("user.dir"));
        //lambda sorting files in directory by those that are csv files and are not data csv files
        File[] schedules = directory.listFiles((dir, name) -> name.endsWith(".csv") && isNotDataCSV(name));

        if (schedules == null) {
            return;
        }

        ArrayList<Schedule> scheduleList = Main.getSchedules();

        //Load each schedule individually
        for (File file : schedules) {
            Schedule schedule = parseSavedFile(file);

            if (schedule != null) {
                scheduleList.add(schedule);
            }
        }
    }

    /**
     * Takes input CSV file and parse information to create a Schedule
     * @param file File to parse
     * @return Schedule based on file inputs
     */
    private Schedule parseSavedFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Schedule schedule = parseSavedSchedule(reader.readLine());
            User user = parseSavedUser(reader.readLine());
            ArrayList<Course> courses = parseSavedCourses(reader.readLine());
            schedule.setUser(user);
            schedule.setCourses(courses);
            return schedule;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Takes string of csv values and then creates Schedule based on the values in the String
     * @param scheduleData raw data to parse
     * @return Schedule based on parsed values
     */
    private Schedule parseSavedSchedule(String scheduleData) {
        String[] scheduleVars = scheduleData.split(",");

        return new Schedule(scheduleVars[0], Integer.parseInt(scheduleVars[1]), scheduleVars[2], Integer.parseInt(scheduleVars[3]));
    }

    /**
     * Takes string of csv values and then creates User based on values in String
     * @param userData raw data to parse
     * @return User based on parsed values
     */
    private User parseSavedUser(String userData) {
        String[] userVars = userData.split(",");

        return new User(userVars[0], userVars[1], userVars[2]);
    }

    /**
     * Takes string of potential csv Course raw data
     * Converts each Course '|' seperated raw data into a course
     * Returns list of Courses that were describe in raw String
     * @param coursesData raw csv data to be parsed
     * @return if null, empty list, else ArrayList of Courses parsed from raw data
     */
    private ArrayList<Course> parseSavedCourses(String coursesData) {
        if(coursesData==null){
            coursesData="";
        }
        List<String> courseVars = Arrays.asList(coursesData.split(","));

        courseVars.replaceAll(String::strip);

        ArrayList<Course> scheduleCourses = new ArrayList<>();

        //Go through each Crs_code in file and add that course to schedule
        for (Course course : Main.getCourses()) {
            String courseID = course.getCrs_code() + "|" + course.getYr_code() + "|" + course.getTrm_code();
            if (courseVars.contains(courseID)) {
                scheduleCourses.add(course);
            }
        }

        return scheduleCourses;
    }


    /**
     * Method to check if file is not one of the data CSVs
     * Only Needed when course data is stored in CSVs
     * @param filename name of file to check
     * @return true if filename is not a data file
     */
    public boolean isNotDataCSV(String filename) {
        return !(filename.equals("2018-2019.csv") || filename.equals("2019-2020.csv") || filename.equals("2020-2021.csv") || filename.contentEquals("UnifiedCSV.csv"));
    }

    /**
     * Writes schedule to csv, saving the data
     * Names csv with "schedule.title _ user.name .csv"
     * First line is values of schedule, formatted by schedule.toString()
     * Second line is values of course, formatted by course.toString()
     *
     * @param schedule schedule to write to csv
     */
    public void saveSchedule(Schedule schedule) {
        String fileName = schedule.getTitle() + "_" + schedule.getUser().getName() + ".csv";

        try {
            try (PrintWriter pw = new PrintWriter(fileName)) {

                pw.write(formatScheduleCSV(schedule));
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
    private String formatScheduleCSV(Schedule schedule) {
        return schedule.getTitle() + "," +
                schedule.getCredits() + "," +
                schedule.getSemester() + "," +
                schedule.getYear() + "\n";
    }

    /**
     * CSV formats User data
     * @param user user to format
     * @return string of user variables in csv format
     */
    private String formatUserCSV(User user) {
        return user.getName() + "," +
                user.getMajor() + "," +
                user.getMinor() + "\n";
    }

    /**
     * CSV formats a list of courses by course code
     * @param Courses list of courses to format
     * @return string of csv formatted course codes
     */
    private String formatCourseCSV(ArrayList<Course> Courses) {
        StringBuilder sb = new StringBuilder();
        for (Course c : Courses) {
            //Creates string to represent each unique Course
            //has format "Crs_code|Yr_code|Trm_code,"
            sb.append(c.getCrs_code()).append("|").append(c.getYr_code()).append("|").append(c.getTrm_code()).append(",");
        }

        if (sb.length() == 0) {
            return "";
        }
        return sb.substring(0, sb.length() - 1) + "\n";//Returns whole string except last ','
    }


}
