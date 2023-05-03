package edu.gcc.comp350.jmeg;

import java.util.ArrayList;

public class Schedule {
    private User user;
    private String title;
    private int credits;
    private ArrayList<Course> courses;
    private String semester;
    private int year;

    public Schedule(User user, String title) {
        this.user = user;
        this.title = title;
        credits = 0;
    }

    /**
     * Constructor for Schedule
     * Used in loading a schedule
     * @param title String title
     * @param credits int credits
     */
    public Schedule(String title, int credits, String semester, int year) {
        this.title = title;
        this.credits = credits;
        this.semester = semester;
        this.year = year;
        this.user = new User("","","");
    }

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits() {
        credits = 0;
        for (Course c: courses) {
            credits += c.getCredit_hrs();
        }
    }

    public ArrayList<Course> getCourses() {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getSemester() {
        if (semester == null) {
            semester = "";
        }
        return semester;
    }

    public int getYear() {
        return year;
    }

    /**
     * Removes course from schedule courses list
     * @param course course to remove
     * @return true if successful, false otherwise
     */
   public boolean removeCourse(Course course) {
       boolean didRemove = false;
       while (courses.contains(course)) {
           didRemove = courses.remove(course);
       }

       setCredits();
       return didRemove;
   }

   public void switchSection(Course old_course, Course new_course){
        removeCourse(old_course);
        courses.add(new_course);
   }

   @Override
   public String toString(){
       StringBuilder schedules = new StringBuilder(String.format("%s's current schedule:\n--------------------------------\n", user));
       for (Course course: getCourses()) {
           schedules.append(course.toString());
           if(courses.indexOf(course) != courses.size() - 1)
               schedules.append("--------------------------------\n");
       }
       return schedules.toString();
   }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Schedule)) {
           return false;
       }

       Schedule schedule = (Schedule) obj;

       if (this == schedule) {
           return true;
       }

        return this.title.equals(schedule.title)
                && this.semester.equals(schedule.semester)
                && this.year == schedule.year;
    }
}
