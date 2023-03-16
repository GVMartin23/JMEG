package edu.gcc.comp350.jmeg;

import java.util.ArrayList;

public class Course {
    private String name;
    private String courseCode;
    private ArrayList<Course> preReqs;
    private String time;
    private String professor;
    private String building;
    private String sectionCode;
    private int roomNum;
    private String description;
    private int capacity;
    private int seatsLeft;

    public Course(String name, String courseCode, ArrayList<Course> preReqs,
                  String time, String professor, String building, String sectionCode,
                  int roomNum, String description, int capacity, int seatsLeft) {
        this.name = name;
        this.courseCode = courseCode;
        this.preReqs = preReqs;
        this.time = time;
        this.professor = professor;
        this.building = building;
        this.sectionCode = sectionCode;
        this.roomNum = roomNum;
        this.description = description;
        this.capacity = capacity;
        this.seatsLeft = seatsLeft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public ArrayList<Course> getPreReqs() {
        return preReqs;
    }

    public void setPreReqs(ArrayList<Course> preReqs) {
        this.preReqs = preReqs;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    @Override
    public String toString(){
        String crs = "";
        crs += name + "\n";
        crs += "--------------------------------\n";
        crs += "Course and Section Code: " + courseCode + sectionCode;

        return null;
    }
}
