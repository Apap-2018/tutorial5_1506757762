package com.apap.tutorial4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

@Controller
public class PilotController {

	@Autowired
	private PilotService pilotService;
	
	@Autowired
	private FlightService flightService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title","Home");
		return "home";
	}
	
	@RequestMapping(value="/pilot/add", method=RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		model.addAttribute("title","Pilot - Add");
		return "addPilot";
	}
	
	@RequestMapping(value="/pilot/add", method=RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot, Model model) {
		pilotService.addPilot(pilot);
		model.addAttribute("title","Pilot - Add Success!");
		return "add";
	}
	
	@RequestMapping(value="/pilot/view", method=RequestMethod.GET)
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		pilot.setPilotFlight(flightService.getListFlight(pilot.getId()));
		pilot.getPilotFlight().add(new FlightModel());
		model.addAttribute("pilot", pilot);
		model.addAttribute("title","Pilot - Data");
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/delete")
	public String delete(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		pilotService.deletePilot(pilot);
		model.addAttribute("title","Pilot");
		return "delete-pilot";
	}
	
	@RequestMapping(value="/pilot/update", method=RequestMethod.GET)
	public String update(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		if(pilot != null) {
			model.addAttribute("pilot", pilot);
			model.addAttribute("title","Pilot - Update");
			return "updatePilot";
		} else {
			model.addAttribute("title","Error!");
			return "not-found";
		}
		
	}
	
	@RequestMapping(value="/pilot/update", method=RequestMethod.POST)
	private String updatePilotSubmit(@ModelAttribute PilotModel pilot, Model model) {
		pilotService.addPilot(pilot);
		model.addAttribute("title","Pilot - Data");
		return "update";
	}
	
	
	
}
