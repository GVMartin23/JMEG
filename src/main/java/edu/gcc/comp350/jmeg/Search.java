package edu.gcc.comp350.jmeg;
import java.util.ArrayList;
import java.util.Scanner;


public class Search {
    private ArrayList<Filter> filters;
    private String inputData;
    private Schedule currentSchedule;
    public Search(Schedule schedule) {
        currentSchedule = schedule;
        filters = new ArrayList<>();
        inputData = "";
    }

    /**
     * Basic method for interaction with program
     * Everything in here can change
     * Return at end used to pass by returntoSchedule
     * If this method is only called in scheduleInteract,
     * then a return will send it back to that method
     */
    public void searchInteraction() {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter search term");

        System.out.println("If finished searching, press q");

        char exit = scnr.next().charAt(0);
        if (exit == 'q') {
            return;
        } else {
            searchInteraction();
        }
    }

    public ArrayList<Course> search() {

    private ArrayList<Course> searchCourseName(ArrayList<Course> allCourses){
        ArrayList<Course> c = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the name of the course");
        String input = scan.next();

        for(int i = 0; i < allCourses.size(); i++){
            if(allCourses.get(6).crs_title.equals(input)){
                System.out.println("Found");
                c.add(allCourses.get(i));
            }
        }
        return c;
    }
    private void clearFilters(){
        return;
    }
    private Course addFilter(Filter f){
        return null;
    }
    private Course removeFilter(Filter f){
        return null;
    }
    private void viewDetails(Course c){
        return;
    }
    private void returnToSchedule(){
        return;
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<Filter> filters) {
        this.filters = filters;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }

    public void setCurrentSchedule(Schedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }

    private void addCourse(Course c){
        return;
    }

}
