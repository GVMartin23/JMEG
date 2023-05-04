package edu.gcc.comp350.jmeg;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CourseResponse {

    @GetMapping("/courseDetails")
    public String getDetails(@RequestParam(value = "code", defaultValue = "")String code) {
        if (code.equals("")) {
            return "";
        }
        try {
            Schedule currentSchedule = Main.getCurrentSchedule();
            Search search = new Search(currentSchedule);
            ArrayList<Course> results = search.search("CODE", code, search.getResults());
            return results.get(0).toString();
        } catch (Exception e) {
            return "";
        }
    }

    @GetMapping("/getDetailsByName")
    public String getDetailsByName(@RequestParam(value = "title", defaultValue = "") String title) {
        if (title.isEmpty()) {
            return "";
        }
        try {
            Schedule currentSchedule = Main.getCurrentSchedule();
            Course course = currentSchedule.getCourses().
                    stream()
                    .filter(c -> c.getCrs_title().toUpperCase().equals(title))
                    .collect(Collectors.toList()).get(0);
            return course.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
