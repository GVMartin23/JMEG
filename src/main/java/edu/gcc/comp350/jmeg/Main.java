package edu.gcc.comp350.jmeg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class Main {
    private static ArrayList<Schedule> schedules;
    private static ArrayList<Course> courses;
    private static IO io;

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


    public static void main(String[] args) {
        io = IO.getInstance();
        io.importCSVData();//Load courses into arrayList from CSV
        io.loadSchedules();//Load saved schedules
        getSchedules();
        SpringApplication.run(Main.class, args);
        User user=makeUser();//Use command prompt to make new user
        ArrayList<Schedule> userSchedules=fillUserSchedules(user);//Create arrayList of schedules for the user,
        userScheduleSelect(user, userSchedules);//Allow for search/add class interface
    }

    /**
     *
     * @param user Specified User to narrow schedules to
     * @param userSchedules List of schedules to select from
     * Provides interface to select which schedule to edit
     */
    public static void userScheduleSelect(User user, ArrayList<Schedule> userSchedules) {
        Scanner scanner = io.getScanner();

        if (!userSchedules.isEmpty()) {
            System.out.println("Your schedules are: ");//Print out all the schedules for the current user
            for (Schedule i : userSchedules) {
                System.out.println(i.getTitle());
            }
            System.out.println("Which schedule do you wish to edit?");
            System.out.println("If you want to create a new schedule, type the name below:");
        } else {
            System.out.println("Enter the name of your first schedule:");
        }

        String userSelectedSchedule = scanner.nextLine().strip();

        while(userSelectedSchedule.equals("")){
            userSelectedSchedule=scanner.nextLine().strip();
        }
        System.out.println("You selected " + userSelectedSchedule);

        Schedule currentSchedule = new Schedule(user, userSelectedSchedule);

        for (Schedule i : userSchedules) {//TODO: make the user input a correct schedule
            if (i.getTitle().equals(userSelectedSchedule)) {//Matches the string input with the actual schedule
                currentSchedule = i;
            }else{
                currentSchedule = new Schedule(user, userSelectedSchedule);
            }
        }

        currentSchedule.scheduleInteract();

        io.saveSchedule(currentSchedule);
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
        return userSchedules;//Returns the schedule you want to edit
    }

    public static User newUser() {
        Scanner scanner = io.getScanner();
        System.out.println("Welcome.  Enter your name");
        String userName = scanner.nextLine().toUpperCase().strip();
        while (userName.isEmpty()) {
            System.out.println("Please enter a name:");
            userName = scanner.nextLine().toUpperCase().strip();
        }

        System.out.println("Enter your major");
        String major = scanner.nextLine().toUpperCase().strip();
        while (major.isEmpty()) {
            System.out.println("Please enter your major:");
            major = scanner.nextLine().toUpperCase().strip();
        }

        System.out.println("Enter your minors");
        String minor = scanner.nextLine().toUpperCase().strip();
        while (minor.isEmpty()) {
            System.out.println("Please enter your minor:");
            minor = scanner.nextLine().toUpperCase().strip();
        }

        User user = new User(userName, major, minor); //Create a new user
        System.out.println("\nWelcome, " + user.getName());
        return user;
    }
    public static User makeUser() {
        if (schedules.isEmpty()) { //user has no schedules so they're new
            return newUser();
        }
        //otherwise ask if they would like to make a new schedule or load existing schedules
        //System.out.println(schedules.get(0).getTitle());
        Scanner scanner = io.getScanner();
        System.out.println("Would you like to make a new schedule or load an existing schedule?" +
                "\nCreate new\t\tLoad existing");
        String choice =scanner.nextLine().toUpperCase().strip();
        while (!choice.equals("CREATE NEW") && !choice.equals("LOAD EXISTING")) {
            System.out.println("Please enter:\n" +
                    "Create new\t\tLoad existing");
            choice = scanner.nextLine().toUpperCase().strip();
        }

        if(choice.equals("LOAD EXISTING")){ //Existing user
            System.out.println("Enter your name");
            String sessionName=scanner.nextLine().toUpperCase().strip();
            while (sessionName.isEmpty()) {
                System.out.println("Please enter a name:");
                sessionName = scanner.nextLine().toUpperCase().strip();
            }
            for(Schedule s: schedules){
                if(s.getUser().getName().toUpperCase().equals(sessionName)){
                    System.out.println("Welcome back "+s.getUser().getName());
                    return s.getUser();
                }
            }
            System.out.println("Could not find your profile, sorry");
        }
        //New User
        return newUser();
    }
}
