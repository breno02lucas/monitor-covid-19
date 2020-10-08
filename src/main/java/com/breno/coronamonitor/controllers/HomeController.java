package com.breno.coronamonitor.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.breno.coronamonitor.models.LocationStats;
import com.breno.coronamonitor.services.CoronavirusDataServices;

@Controller
public class HomeController {
	
	@Autowired
	CoronavirusDataServices coronavirusDataServices;
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<LocationStats> allStats = coronavirusDataServices.getAllStats();
		int totalCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum(); // Transformando os valores para INT e somando
		
		// Criamos um módulo que passa o nome do atributo e o obj do atributo.
		model.addAttribute("locationStats", allStats);
		model.addAttribute("totalReportedCases", totalCases);
		
		return "home";
		
	}

}
