package edu.gcc.comp350.jmeg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SearchResponse {

    @GetMapping("/search")
    public ArrayList<Course> retrieveResults(@RequestParam(value = "code", defaultValue = "") String code) {
        Schedule currentSchedule = new Schedule("Title", 0, "SPRING", 2019);
        Search search = new Search(currentSchedule);

        return search.search("CODE", code, search.getResults());
    }
}
