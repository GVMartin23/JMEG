package edu.gcc.comp350.jmeg;

import edu.gcc.comp350.jmeg.filter.FilterTerm;
import edu.gcc.comp350.jmeg.filter.FilterYear;
import edu.gcc.comp350.jmeg.filter.Filterable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
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

        ArrayList<Course> fullList = new ArrayList<>(courses);
        fullList.sort(Comparator.comparing(Course::getCrs_title));

        return fullList;
    }

    @GetMapping("/addCourse")
    public int addCourse(@RequestParam(value = "code", defaultValue = "") String code) {
        Schedule currentSchedule;
        try {
            currentSchedule = Main.getCurrentSchedule();
        } catch (Exception e) {
            return 0;//0 for invalid schedule
        }

        ArrayList<Course> results = search.getResults();

        Course course = results.stream().filter(c -> c.getCrs_code().equals(code)).collect(Collectors.toList()).get(0);
        if(search.checkIfAlreadyAdded(course, currentSchedule.getCourses())){
            return 4;//4 for course already added
        }
        if (search.checkForOverlap(course, currentSchedule.getCourses())) {
            return 1;//1 for course overlap
        }

        if (currentSchedule.getCredits() + course.getCredit_hrs() > 18) {
            return 2;//2 for max credits
        }

        search.addToSchedule(currentSchedule, course, results);
        return 3; //3 for success
    }

    @GetMapping("/removeCourse")
    public boolean removeSchedule(@RequestParam(value = "code", defaultValue = "") String code) {
        Schedule currentSchedule;

        try {
            currentSchedule = Main.getCurrentSchedule();
        } catch (Exception e) {
            return false;
        }

        int term = (currentSchedule.getSemester().equals("SPRING")) ? 30 : 10;
        Filterable filterTerm = new FilterTerm(term);
        Filterable filterYear = new FilterYear(currentSchedule.getYear());
        ArrayList<Course> courses = filterTerm.filter(Main.getCourses());
        courses = filterYear.filter(courses);
        Course course = courses.stream().filter(c -> c.getCrs_code().equals(code)).collect(Collectors.toList()).get(0);
        return currentSchedule.removeCourse(course);
    }

    @GetMapping("/courseDetailsTitle")
    public String detailsByTitle(@RequestParam(value = "title", defaultValue = "") String title) {
        title = title.toUpperCase().strip();
        Schedule currentSchedule;

        try {
            currentSchedule = Main.getCurrentSchedule();
        } catch (Exception e) {
            return "";
        }

        //Get the corresponding course from given title in Schedule's courseList
        String finalTitle = title;
        Course course = currentSchedule.getCourses()
                .stream()
                .filter(c -> c.getCrs_title().toUpperCase().strip().equals(finalTitle))
                .collect(Collectors.toList()).get(0);
        return course.toString();
    }
}
