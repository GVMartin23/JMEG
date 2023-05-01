package edu.gcc.comp350.jmeg;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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
}
