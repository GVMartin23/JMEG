package edu.gcc.comp350.jmeg;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ScheduleResponse {

    @GetMapping("/currentSchedule")
    public Schedule getCurrentSchedule() {
        try {
            return Main.getCurrentSchedule();
        } catch (Exception e) {
            return null;
        }

    }

    @GetMapping("/scheduleList")
    public ArrayList<Schedule> retrieveSchedules() {
        return Main.getSchedules();
    }

    @GetMapping("/schedulePick")
    public boolean selectSchedule(@RequestParam(value = "title", defaultValue = "") String scheduleTitle) {
        List<Schedule> scheduleList = Main.getSchedules().stream().filter(s -> s.getTitle().equals(scheduleTitle)).collect(Collectors.toList());

        try {
            Schedule oldSchedule = Main.getCurrentSchedule();
            IO.getInstance().saveSchedule(oldSchedule);
        } catch (Exception ignored) {}

        if (scheduleList.size() == 0) {
            return false;
        }

        Main.setCurrentSchedule(scheduleList.get(0));
        return true;
    }

    @GetMapping("/scheduleCreate")
    public boolean createSchedule(@RequestParam(value = "title", defaultValue = "") String scheduleTitle,
                                  @RequestParam(value = "semester", defaultValue = "") String semester,
                                  @RequestParam(value = "year", defaultValue = "") String year) {
        if (scheduleTitle.isEmpty() || semester.isEmpty() || year.isEmpty()) {
            return false;
        }

        try {
            int yearVal = Integer.parseInt(year);
            Schedule newSchedule = new Schedule(scheduleTitle, 0, semester, yearVal);
            Main.getSchedules().add(newSchedule);
            Main.setCurrentSchedule(newSchedule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
