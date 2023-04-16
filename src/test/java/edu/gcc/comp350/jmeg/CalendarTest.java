package edu.gcc.comp350.jmeg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CalendarTest {

    @BeforeAll
    static void setup() {
        if (Main.getCourses().isEmpty()) {
            IO.getInstance().importCSVData();
        }
    }

    @Test
    public void calendarTest(){
        ArrayList<Course> courses=Main.getCourses();

        User u=new User("Ethan", "CS", "AI");
        Course c= courses.get(100);
        Course c2= courses.get(2);
        Course c3=courses.get(3);
        Course c4=courses.get(78);
        Course c5=courses.get(200);

        ArrayList<Course> fakeCourseList=new ArrayList<Course>();
        fakeCourseList.add(c);
        fakeCourseList.add(c2);
        fakeCourseList.add(c3);
        fakeCourseList.add(c4);
        fakeCourseList.add(c5);
        Schedule s=new Schedule(u,"Test Calendar");
        s.setCourses(fakeCourseList);
        Calendar cal=new Calendar(s);
        System.out.println(cal.showCalendar());
    }
}