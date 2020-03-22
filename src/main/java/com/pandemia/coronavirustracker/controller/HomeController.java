package com.pandemia.coronavirustracker.controller;

import com.pandemia.coronavirustracker.models.LocationStats;
import com.pandemia.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model){

        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalCases = allStats.stream().mapToInt(stat -> stat.getCases()).sum();
        int totalDeaths = allStats.stream().mapToInt(stat -> stat.getDeaths()).sum();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalCases);
        model.addAttribute("totalDeaths", totalDeaths);

        return "home";
    }
}
