package edu.gcc.comp350.jmeg;

public class Calendar {
    private Schedule currentSchedule;

    public Calendar(Schedule currentSchedule){
this.currentSchedule=currentSchedule;
    }

    /**
     *
     * @param schedule
     * @return string visualizing the calendar. Use sysout(showCalendar) to print it out.
     */
    public String showCalendar(Schedule schedule){
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
            int[] timeCodes = slot.beginTimeCodes;
            if (slot.isOnMonday()) {//TODO change k
                int timeCode = timeCodes[0] - 8;
                calendarArray[0][timeCode]=course.getBegin_tim()+" "+courseName;
            }
            if (slot.isOnTuesday()) {
                int timeCode = timeCodes[1] - 8;
                calendarArray[1][timeCode]=course.getBegin_tim()+" "+courseName;
            }
            if (slot.isOnWednesday()) {
                int timeCode = timeCodes[2] - 8;
                calendarArray[2][timeCode]=course.getBegin_tim()+" "+courseName;
            }
            if (slot.isOnThursday()) {
                int timeCode = timeCodes[3] - 8;
                calendarArray[3][timeCode]=course.getBegin_tim()+" "+courseName;
            }
            if (slot.isOnFriday()) {
                int timeCode = timeCodes[4] - 8;
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
    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }

    public void setCurrentSchedule(Schedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }

}
