package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CalendarTest {

    @Test
    public void calendarTest(){
        IO.getInstance().importCSVData();
        ArrayList<Course> courses=Main.getCourses();

        User u=new User("Ethan", "CS", "AI", 2024);
        Course c= courses.get(100);//100);//new Course("Course1", "12/12/2000 08:00:00", "12/12/2000 09:00:00");
        Course c2= courses.get(2);//176);// Course("Course2", "12/12/2000 12:00:00", "12/12/2000 02:00:00");
        Course c3=courses.get(3);//12);
        Course c4=courses.get(78);
        Course c5=courses.get(200);

        ArrayList<Course> fakeCourseList=new ArrayList<Course>();
        fakeCourseList.add(c);
        fakeCourseList.add(c2);
        fakeCourseList.add(c3);
        fakeCourseList.add(c4);
        fakeCourseList.add(c5);
        Schedule s=new Schedule(u,"Test Calendar", fakeCourseList);
        Calendar cal=new Calendar(s);
        System.out.println( cal.showCalendar());

    }

}