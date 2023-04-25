package edu.gcc.comp350.jmeg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SearchResponse {

    @GetMapping("/search")
    public ArrayList<Course> retrieveResults(@RequestParam(value = "code", defaultValue = "") String code) {
        Schedule currentSchedule;
        try {
            currentSchedule = Main.getCurrentSchedule();
        } catch (Exception e) {
            return null;
        }

        Search search = new Search(currentSchedule);

        return search.search("CODE", code, search.getResults());
    }
}
