package edu.gcc.comp350.jmeg;
import java.util.ArrayList;
import java.util.Scanner;



public class Search {
    private ArrayList<Filter> filters;
    private String inputData;
    private Schedule currentSchedule;
    public void Search(){

    }

//    public static void main(String[] args) {
//        ArrayList<Course> cs;
//        Search s = new Search();
//        cs = s.getCourses();
//
//
//    }


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
