package edu.gcc.comp350.jmeg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ScheduleResponse {

    @GetMapping("/schedule")
    public ArrayList<Schedule> retrieveSchedules() {
        return Main.getSchedules();
    }
}
