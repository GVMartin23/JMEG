package edu.gcc.comp350.jmeg;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        User user=new User("Sally Senior", "Computer Science", "AI and ML", 2024);
        System.out.println(user.getName());
    }
private static ArrayList<Schedule> schedules;

    public static ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public static void setSchedules(ArrayList<Schedule> schedules) {
        Main.schedules = schedules;
    }

    public void Main(){

}
private static Schedule createSchedule(){
    return null;
}
private static void loadSchedule(Schedule s){

}
public void saveSchedule(Schedule s){
    return;
}
}
