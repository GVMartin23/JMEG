package edu.gcc.comp350.jmeg;

import java.util.*;


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

    public static void setSchedules(ArrayList<Schedule> schedules) {
        Main.schedules = schedules;
    }

    public static void main(String[] args) {
        io = IO.getInstance();
        io.importCSVData();//Load courses into arrayList from CSV
        io.loadSchedules();//Load saved schedules
        User user=makeUser();//Use command prompt to make new user
        ArrayList<Schedule> userSchedules=fillUserSchedules(user);//Create arrayList of schedules for the user,
        userScheduleSelect(user, userSchedules);//Allow for search/add class interface
    }

    private static Schedule createSchedule(){
        return null;
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

    public static User makeUser() {
        Scanner scanner = io.getScanner();
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
            User user = new User(userName, major, minor, year);//Create a new user
            System.out.println("\nWelcome, " + user.getName());
            return user;
        }
    }
}
