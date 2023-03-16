package edu.gcc.comp350.jmeg;

import java.io.*;
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
    public static ArrayList<Course> completeCourseList;

    public static void setCourses(ArrayList<Course> courses) {
        Main.courses = courses;
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public static void setSchedules(ArrayList<Schedule> schedules) {
        Main.schedules = schedules;
    }




    public static void main(String[] args) {
//Making dummy courses and putting them in our complete class list
       makeDummyCourses();



        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to this software.  Enter your name");
        String userName= scanner.nextLine();
        System.out.println("Enter your major");
        String major=scanner.nextLine();
        System.out.println("Enter your minors");
        String minor = scanner.nextLine();
        System.out.println("Enter your year");
        int year=scanner.nextInt();
        scanner.nextLine();
        User user=new User(userName, major, minor, year);//Create a user
        System.out.println("Welcome, "+user.getName());

        ArrayList<Schedule> userSchedules=new ArrayList<Schedule>();//Creates a new arrayList of schedules for the new user
        Schedule schedule=new Schedule(user, "Test schedule");//Puts a new schedule into that list
        schedules.add(schedule);


        for(int i=0; i<schedules.size(); i++){
            if(schedules.get(i).getUser().getName()==userName){//Puts all the schedules containing that user's name from the master schedule list into the list
                userSchedules.add(schedules.get(i));
            }
        }
        userScheduleSelect(user, userSchedules);

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


    /**
     * Loads saved schedules into memory
     * Looks in working directory (Not Final)
     */
    public static void loadSchedule() throws IOException {
        //Current working directory
        File directory = new File(System.getProperty("user.dir"));

        //lambda sorting files in directory by those that are csv files
        File[] schedules = directory.listFiles((dir, name) -> name.endsWith(".csv") && !isDataCSV(name));

        if (Main.schedules == null) {
            Main.schedules = new ArrayList<>();
        }
        //Load each schedule individually
        for (File file : schedules) {
            Main.schedules.add(parseSavedSchedule(file));
        }
    }

    /**
     * Method to check if file is one of the data csvs instead of a schedule
     * Only Needed when course data is stored in CSVs
     * @param filename name of file to check
     * @return true if filename is same as data files
     */
    private static boolean isDataCSV(String filename) {
        return filename.equals("2018-2019.csv") || filename.equals("2019-2020.csv") || filename.equals("2020-2021.csv");
    }

    /**
     * Takes input CSV file and parse information to load Schedule
     * @param file File to parse
     * @throws IOException if fileNotFound, or data read incorrectly
     */
    private static Schedule parseSavedSchedule(File file) throws IOException {
        String scheduleData;
        String userData;
        String coursesData;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            scheduleData = reader.readLine();
            userData = reader.readLine();
            coursesData = reader.readLine();
        }

        String[] scheduleVars = scheduleData.split(",");
        String[] userVars = userData.split(",");
        List<String> courseVars = Arrays.asList(coursesData.split(","));

        Schedule schedule = new Schedule(scheduleVars[0], Integer.parseInt(scheduleVars[1]));

        User user = new User(userVars[0], userVars[1], userVars[2], Integer.parseInt(userVars[3]));

        schedule.setUser(user);

        ArrayList<Course> scheduleCourses = new ArrayList<>();

        for (Course course : courses) {
            if (courseVars.contains(course.getCrs_code())) {
                scheduleCourses.add(course);
            }
        }

        schedule.setCourses(scheduleCourses);

        return schedule;

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
            sb.append(c.getCrs_code()).append(",");
        }

        return sb.substring(0, sb.length() - 1) + "\n";
    }

        public static void userScheduleSelect(User user, ArrayList<Schedule> userSchedules){
            Scanner scanner=new Scanner(System.in);
            System.out.println("Your schedules are: ");//Print out all the schedules for the current user
            for(Schedule i:userSchedules){
                System.out.println(i.getTitle());
            }
            System.out.println("Which schedule do you wish to edit?");
            String userSelectedSchedule=scanner.nextLine();
            System.out.println("You selected "+userSelectedSchedule);
            Schedule currentSchedule=new Schedule(user, "meh");
            ArrayList<Course> userCurrentCourses=new ArrayList<Course>();
            for(Schedule i:userSchedules){
                if(i.getTitle().equals(userSelectedSchedule)){//Matches the string input with the actual schedule
                    currentSchedule=i;
                    userCurrentCourses=currentSchedule.getCourses();
                }
            }
            System.out.println("Editing schedule "+currentSchedule.getTitle());
            System.out.println("Entire class list:");//Lists out all the possible classes from master list
            for(Course c:completeCourseList){
                System.out.print(" "+c.getCrs_title());
            }
            System.out.println("\nEnter a course name to add it to your schedule");
            Boolean addClass=true;
            String courseName=scanner.nextLine();
            addCourseToSchedule(courseName, userCurrentCourses);

            System.out.println("Now showing current schedule");
            for(Course c: userCurrentCourses){
                System.out.println(c.getCrs_title());
            }
            saveSchedule(currentSchedule);
        }

        public static void addCourseToSchedule(String courseName, ArrayList<Course> userCurrentCourses){
            for(int i=0; i<userCurrentCourses.size(); i++) {
                if (userCurrentCourses.size() > 0) {
                    if (userCurrentCourses.get(i).getCrs_title() == courseName) {
                        System.out.println("You already have this course added");
                    }
                }
            }
            System.out.println("Searching for "+courseName);
            for (int j = 0; j < completeCourseList.size(); j++) {
                if (completeCourseList.get(j).getCrs_title().equals(courseName)) {
                    userCurrentCourses.add(completeCourseList.get(j));
                    System.out.println("Successfully added class" + completeCourseList.get(j).getCrs_title());
                }

            }
        }
public static void makeDummyCourses(){
    Course course1=new Course("Biology");
    Course course2=new Course("Physics");
    completeCourseList=new ArrayList<Course>();
    completeCourseList.add(course1);
    completeCourseList.add(course2);
    schedules=new ArrayList<Schedule>();
}
}
