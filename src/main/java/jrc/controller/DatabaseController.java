package jrc.controller;

import jrc.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/clear-database")
    public String clearDatabase() {
        databaseService.truncateAllTables();
        return "redirect:/";
    }
}
