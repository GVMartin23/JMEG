package edu.gcc.comp350.jmeg;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SearchResponse {
    private Search search;

    @GetMapping("/search")
    public ArrayList<Course> retrieveResults(@RequestParam(value = "code", defaultValue = "") String code) {
        code = code.toUpperCase();
        Schedule currentSchedule;
        try {
            currentSchedule = Main.getCurrentSchedule();
        } catch (Exception e) {
            return null;
        }

        search = new Search(currentSchedule);

        ArrayList<Course> codeResults = search.search("CODE", code, search.getResults());
        search.resetSearch();
        ArrayList<Course> titleResults = search.search("NAME", code, search.getResults());
        search.resetSearch();
        ArrayList<Course> dayResults = search.search("DAY", code, search.getResults());
        search.resetSearch();
        ArrayList<Course> timeResults = search.search("TIME", code, search.getResults());

        HashSet<Course> courses = new HashSet<>(codeResults);
        courses.addAll(titleResults);
        courses.addAll(dayResults);
        courses.addAll(timeResults);

        return new ArrayList<>(courses);
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
