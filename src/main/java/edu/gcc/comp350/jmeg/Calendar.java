package edu.gcc.comp350.jmeg;

public class Calendar {
    private Schedule currentSchedule;

    public Calendar(Schedule currentSchedule){
this.currentSchedule=currentSchedule;
    }

    public String showCalendar(Schedule schedule){
        String[][] calendarArray=new String[5][10];
        calendarArray[0][0]="Monday\t\t";
        calendarArray[1][0]="Tuesday\t\t";
        calendarArray[2][0]="Wednesday\t";
        calendarArray[3][0]="Thursday\t";
        calendarArray[4][0]="Friday\t\t";

        for(int k=0; k<currentSchedule.getCourses().size(); k++) {
            String time = currentSchedule.getCourses().get(k).getBegin_tim();
            time = Course.convertStringToTime(time);
            String courseName = currentSchedule.getCourses().get(k).getCrs_title();
            if (currentSchedule.getCourses().get(k).getMonday_cde().equals("M")) {//TODO change k
                calendarArray[0][k+1] = time + " " + courseName;//[0][?] change from k+1
            }
            if (currentSchedule.getCourses().get(k).getTuesday_cde().equals("T")) {
                calendarArray[1][k+1] = time + " " + courseName;
            }
            if (currentSchedule.getCourses().get(k).getWednesday_cde().equals("W")) {
                calendarArray[2][k+1] = time + " " + courseName;
            }
            if (currentSchedule.getCourses().get(k).getThursday_cde().equals("R")) {
                calendarArray[3][k+1] = time + " " + courseName;
            }
            if (currentSchedule.getCourses().get(k).getFriday_cde().equals("F")) {
                calendarArray[4][k+1] = time + " " + courseName;
            }
        }

            String string="";
        for(int i=0; i<5; i++){
            for(int j=0; j<10; j++){
                if(calendarArray[i][j]==null){
                    if(j>4){
                        string+=(j-4)+"|";
                    }else{
                        string+=(j+8)+"|";
                    }
                }else {
                    string += calendarArray[i][j] + "|";//Building the calendar string
                }
            }
            string+="\n";
        }
        System.out.println(string);
        return string;
    }
    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }

    public void setCurrentSchedule(Schedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }

}
