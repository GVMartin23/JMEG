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
            String timeMinusAmPM="";
            if(time.contains("AM")) {
timeMinusAmPM=time.replace("AM", "");
            }else if(time.contains("PM")) {
                timeMinusAmPM = time.replace("PM", "");
            }

            int timeInt=Integer.parseInt(timeMinusAmPM);
           // System.out.println("time: "+time.charAt(0);
            String courseName = currentSchedule.getCourses().get(k).getCrs_title();
            if (currentSchedule.getCourses().get(k).getMonday_cde().equals("M")) {//TODO change k
              if(timeInt>4) {
                  calendarArray[0][timeInt-8] = time + " " + courseName;//[0][?] change from k+1
              }else{
                  calendarArray[0][timeInt+5]=time+" "+courseName;
              }
              }
            if (currentSchedule.getCourses().get(k).getTuesday_cde().equals("T")) {
                if(timeInt>4) {
                    calendarArray[1][timeInt-8] = time + " " + courseName;//[0][?] change from k+1
                }else{
                    calendarArray[1][timeInt+5]=time+" "+courseName;
                }            }
            if (currentSchedule.getCourses().get(k).getWednesday_cde().equals("W")) {
                if(timeInt>4) {
                    calendarArray[2][timeInt-8] = time + " " + courseName;//[0][?] change from k+1
                }else{
                    calendarArray[2][timeInt+5]=time+" "+courseName;
                }            }
            if (currentSchedule.getCourses().get(k).getThursday_cde().equals("R")) {
                if(timeInt>4) {
                    calendarArray[3][timeInt-8] = time + " " + courseName;//[0][?] change from k+1
                }else{
                    calendarArray[3][timeInt+5]=time+" "+courseName;
                }            }
            if (currentSchedule.getCourses().get(k).getFriday_cde().equals("F")) {
                if(timeInt>4) {
                    calendarArray[4][timeInt-8] = time + " " + courseName;//[0][?] change from k+1
                }else{
                    calendarArray[4][timeInt+5]=time+" "+courseName;
                }            }
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
