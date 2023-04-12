package edu.gcc.comp350.jmeg;

public class Calendar {
    private final Schedule currentSchedule;

    public Calendar(Schedule currentSchedule){
this.currentSchedule=currentSchedule;
    }

    /**
     * Converts schedule into Calendar based on what time classes are taken
     * @return Formatted Schedule in String
     */
    public String showCalendar(){
        String[][] calendarArray=new String[5][10];
        calendarArray[0][0]="Monday\t\t";
        calendarArray[1][0]="Tuesday\t\t";
        calendarArray[2][0]="Wednesday\t";
        calendarArray[3][0]="Thursday\t";
        calendarArray[4][0]="Friday\t\t";

        for(int k=0; k<currentSchedule.getCourses().size(); k++) {
            Course course = currentSchedule.getCourses().get(k);
            String courseName = currentSchedule.getCourses().get(k).getCrs_title();
            TimeSlot slot = course.getTimeSlot();
            int timeCode = slot.getBeginTimeCode() - 8;
            if (slot.getBeginTimeCode() == 24) {
                //Value used to represent classes that have no time
                //Cant be shown on tradition calendar
                //Maybe make a new Row to show classes without a specific time or day?
                continue;
            }
            if (slot.isOnMonday()) {
                calendarArray[0][timeCode]=course.getBegin_tim()+" "+courseName;
            }
            if (slot.isOnTuesday()) {
                calendarArray[1][timeCode]=course.getBegin_tim()+" "+courseName;
            }
            if (slot.isOnWednesday()) {
                calendarArray[2][timeCode]=course.getBegin_tim()+" "+courseName;
            }
            if (slot.isOnThursday()) {
                calendarArray[3][timeCode]=course.getBegin_tim()+" "+courseName;
            }
            if (slot.isOnFriday()) {
                calendarArray[4][timeCode]=course.getBegin_tim()+" "+courseName;
            }
        }

        StringBuilder string= new StringBuilder();
        for(int i=0; i<5; i++){
            for(int j=0; j<10; j++){
                if(calendarArray[i][j]==null){
                    if(j>4){
                        string.append(j - 4).append("|");
                    }else{
                        string.append(j + 8).append("|");
                    }
                }else {
                    string.append(calendarArray[i][j]).append("|");//Building the calendar string
                }
            }
            string.append("\n");
        }
        return string.toString();
    }
}
