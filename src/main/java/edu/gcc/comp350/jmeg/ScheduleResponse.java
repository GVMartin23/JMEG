package edu.gcc.comp350.jmeg;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
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
    public int createSchedule(@RequestParam(value = "title", defaultValue = "") String scheduleTitle,
                                  @RequestParam(value = "semester", defaultValue = "") String semester,
                                  @RequestParam(value = "year", defaultValue = "") String year) {
        semester = semester.toUpperCase().strip();
        if (scheduleTitle.isEmpty() || semester.isEmpty() || year.isEmpty()) {
            return 2;
        }

        if (Main.getSchedules().stream().filter(s -> s.getTitle().equals(scheduleTitle)).count() > 0) {
            return 1;
        }

        try {
            int yearVal = Integer.parseInt(year);
            Schedule newSchedule = new Schedule(scheduleTitle, 0, semester, yearVal);
            User user = new User(" ", " ", " ");
            newSchedule.setUser(user);
            Main.getSchedules().add(newSchedule);
            Main.setCurrentSchedule(newSchedule);
            return 0;
        } catch (Exception e) {
            return 3;
        }
    }

    @GetMapping("/removeByTitle")
    public boolean removeByTitle(@RequestParam(value = "title", defaultValue = "") String title) {
        if (title.equals("")) {
            return false;
        }

        title = title.toUpperCase().strip();
        Schedule currentSchedule;

        try {
            currentSchedule = Main.getCurrentSchedule();
        } catch (Exception e) {
            return false;
        }

        String finalTitle = title;
        Course remover = currentSchedule.getCourses()
                .stream()
                .filter(c -> c.getCrs_title().toUpperCase().strip().equals(finalTitle))
                .collect(Collectors.toList()).get(0);

        return currentSchedule.removeCourse(remover);
    }

    @GetMapping("/deleteSchedule")
    public int deleteSchedule(@RequestParam(value = "title", defaultValue = "") String title) {
        title = title.toUpperCase().strip();
        if (title.equals("")) {
            return 4;
        }

        try {
            for (Schedule s : Main.getSchedules()) {
                IO.getInstance().saveSchedule(s);
            }

            File directory = new File(System.getProperty("user.dir"));
            //lambda sorting files in directory by those that are csv files and are not data csv files
            File[] schedules = directory.listFiles((dir, name) -> name.endsWith(".csv") && IO.getInstance().isNotDataCSV(name));

            for (File f : schedules) {
                f.delete();
            }

            String finalTitle = title;
            Main.getSchedules().removeIf(s -> s.getTitle().toUpperCase().equals(finalTitle));

            for (Schedule s : Main.getSchedules()) {
                IO.getInstance().saveSchedule(s);
            }

            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
}
