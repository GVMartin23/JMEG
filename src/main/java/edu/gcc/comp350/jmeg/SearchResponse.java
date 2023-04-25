package edu.gcc.comp350.jmeg;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SearchResponse {
    private Search search;

    @GetMapping("/search")
    public String retrieveResults(@RequestParam(value = "code", defaultValue = "") String code) {
        code = code.toUpperCase();
        Schedule currentSchedule;
        try {
            currentSchedule = Main.getCurrentSchedule();
        } catch (Exception e) {
            currentSchedule = new Schedule("TEST", 0, "SPRING", 2019);;
        }

        search = new Search(currentSchedule);

        ArrayList<Course> courses = search.search("CODE", code, search.getResults());

        StringBuilder sb = new StringBuilder();
        for (Course c : courses) {
            sb.append(c.toString());
        }

        return sb.toString();
    }

    @GetMapping("/addCourse")
    public boolean addCourse(@RequestParam(value = "code", defaultValue = "") String code) {
        Schedule currentSchedule;
        try {
            currentSchedule = Main.getCurrentSchedule();
        } catch (Exception e) {
            return false;
        }

        ArrayList<Course> results = search.getResults();

        Course course = results.stream().filter(c -> c.getCrs_code().equals(code)).collect(Collectors.toList()).get(0);

        if (search.checkForOverlap(course, currentSchedule.getCourses())) {
            return false;
        } else {
            search.addToSchedule(currentSchedule, course, results);
            return true;
        }
    }
}
