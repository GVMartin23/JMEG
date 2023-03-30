package edu.gcc.comp350.jmeg;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;


public class Main {
    private static ArrayList<Schedule> schedules;
    private static ArrayList<Course> courses;

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

    public static void setSchedules(ArrayList<Schedule> schedules) {
        Main.schedules = schedules;
    }

    public static void main(String[] args) {
        testCSV();//Load courses into arrayList from CSV
        loadSchedule();
        User user=makeUser();//Use command prompt to make new user
        ArrayList<Schedule> userSchedules=fillUserSchedules(user);//Create arrayList of schedules for the user,
        userScheduleSelect(user, userSchedules);//Allow for search/add class interface
    }

    /**
     * Uses the loadCSV() function to retrieve the data on each course in the CSV
     * Loops through each row and declares it as a course then adds it to the course
     * arraylist
     */
    public static void testCSV(){
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

                ArrayList<Course> courseList = getCourses();

                Course course = new Course(yr_code, trm_code, crs_code,
                        crs_comp1, crs_comp2, crs_comp3, crs_title,
                        credit_hrs, x_listed_parnt_crs, acad_credit_varies,
                        acad_credit_label, crs_capacity, max_capacity, crs_enrollment,
                        bldg_cde, room_cde, monday_cde, tuesday_cde, wednesday_cde,
                        thursday_cde, friday_cde, begin_tim, end_tim, last_name, first_name,
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
    public static ArrayList<String[]> loadCSV() throws IOException {
        String csvFile = "2018-2019.csv";
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

    private static Schedule createSchedule(){
        return null;
    }


    /**
     * Loads saved schedules into memory
     * Looks in working directory (Not Final)
     */
    public static void loadSchedule() {
        //Current working directory
        File directory = new File(System.getProperty("user.dir"));
        //lambda sorting files in directory by those that are csv files
        File[] schedules = directory.listFiles((dir, name) -> name.endsWith(".csv") && !isDataCSV(name));

        if (schedules == null) {
            schedules = new File[0];
        }

        //Load each schedule individually
        for (File file : schedules) {
            ArrayList<Schedule> scheduleList = Main.getSchedules();
            try {
                scheduleList.add(parseSavedSchedule(file));
            } catch (IOException e) {
                //File not found error or failed in reading file
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to check if file is one of the data csvs instead of a schedule
     * Only Needed when course data is stored in CSVs
     * @param filename name of file to check
     * @return true if filename is same as data files
     */
    public static boolean isDataCSV(String filename) {
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
        //Reads each line of saved schedule
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            scheduleData = reader.readLine();
            userData = reader.readLine();
            coursesData = reader.readLine();
        }

        //Split lines of csv into unique vals
        String[] scheduleVars = scheduleData.split(",");
        String[] userVars = userData.split(",");
        if(coursesData==null){
            coursesData="";
        }
        List<String> courseVars = Arrays.asList(coursesData.split(","));

        Schedule schedule = new Schedule(scheduleVars[0], Integer.parseInt(scheduleVars[1]));

        User user = new User(userVars[0], userVars[1], userVars[2], Integer.parseInt(userVars[3]));

        schedule.setUser(user);

        ArrayList<Course> scheduleCourses = new ArrayList<>();

        //Go through each Crs_code in file and add that course to schedule
        for (Course course : getCourses()) {
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
            try (PrintWriter pw = new PrintWriter(fileName)) {

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

        if (sb.length() == 0) {
            return "";
        }
        return sb.substring(0, sb.length() - 1) + "\n";
    }

    /**
     *
     * @param user Specified User to narrow schedules to
     * @param userSchedules List of schedules to select from
     * Provides interface to select which schedule to edit
     */
    public static void userScheduleSelect(User user, ArrayList<Schedule> userSchedules){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Your schedules are: ");//Print out all the schedules for the current user
        for(Schedule i:userSchedules){
            System.out.println(i.getTitle());
        }
        System.out.println("Which schedule do you wish to edit?");
        String userSelectedSchedule=scanner.nextLine();
        System.out.println("You selected "+userSelectedSchedule);
        Schedule currentSchedule=new Schedule(user, "empty schedule");

        for (Schedule i : userSchedules) {//TODO: make the user input a correct schedule
            if (i.getTitle().equals(userSelectedSchedule)) {//Matches the string input with the actual schedule
                currentSchedule = i;
            }
        }


        currentSchedule.scheduleInteract();

        saveSchedule(currentSchedule);
    }

    /**
     *
     * @param user User to sort schedules by
     * @return
     * Fills the User's array of schedules with all their schedules, whether new or returning user
     */
    public static ArrayList<Schedule> fillUserSchedules(User user){
        ArrayList<Schedule> userSchedules=new ArrayList<>();//Creates a new arrayList of schedules for the new user
        for (Schedule value : schedules) {
            if (value.getUser().getName().equals(user.getName())) {//Puts all the schedules containing that user's name from the master schedule list into the list
                userSchedules.add(value);
            }
        }
        if(userSchedules.isEmpty()){//If you are a new user you don't have any schedules so make a new one
            Scanner scanner=new Scanner(System.in);
            System.out.println("You have no schedules currently.  Type the title of your first schedule");
            String title=scanner.nextLine();
            Schedule newSchedule=new Schedule(user, title);
            userSchedules.add(newSchedule);
        }
        return userSchedules;//Returns the schedule you want to edit
    }

    public static User makeUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Have you used this software before? Y/N");
        String returningUser=scanner.nextLine().toLowerCase();
        while(!returningUser.equals("y")&&!returningUser.equals("n")){
            System.out.println("Invalid answer, please try again using Y/N");
            returningUser=scanner.nextLine().toLowerCase();
        }
        if(returningUser.equals("y")){//Existing user
            System.out.println("Enter your name");
            String username=scanner.nextLine();
            for(Schedule s: schedules){
                if(s.getUser().getName().equals(username)){
                    System.out.println("Welcome back "+s.getUser().getName());
                    return s.getUser();
                }
            }
            System.out.println("Didnt find the user.  Making fake: ");
            return new User("Doug", "CS", "AI", 2024);
            //return null;//TODO change so this doesn't make a fake user

        }else {//New user
            System.out.println("Welcome.  Enter your name");
            String userName = scanner.nextLine();
            System.out.println("Enter your major");
            String major = scanner.nextLine();
            System.out.println("Enter your minors");
            String minor = scanner.nextLine();
            System.out.println("Enter your year");
            int year = scanner.nextInt();
            System.out.println("\n");
            User user = new User(userName, major, minor, year);//Create a new user
            System.out.println("Welcome, " + user.getName());
            return user;
        }
    }
}
