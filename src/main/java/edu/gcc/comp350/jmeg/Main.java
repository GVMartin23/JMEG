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
    public static ArrayList<Course> completeCourseList;

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
            System.out.println("Editing schedule"+currentSchedule.getTitle());
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
