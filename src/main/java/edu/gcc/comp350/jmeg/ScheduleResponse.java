package edu.gcc.comp350.jmeg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class ScheduleResponse {

    @GetMapping("/schedule")
    public ArrayList<Schedule> retrieveSchedules() {
        return Main.getSchedules();
    }

    @GetMapping("/schedulePick")
    public void selectSchedule(@RequestParam(value = "title", defaultValue = "") String scheduleTitle) {
        Main.setCurrentSchedule(Main.getSchedules().stream().filter(s -> s.getTitle().equals(scheduleTitle)).collect(Collectors.toList()).get(0));
    }
}
