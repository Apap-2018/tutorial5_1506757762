package com.apap.tutorial4.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;


@Controller
public class FlightController {

	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;

	
	@RequestMapping(value="/flight/add/{pilotId}", method=RequestMethod.GET)
	private String add(@PathVariable(value="pilotId") Long pilotId, Model model) {
		PilotModel pilot = pilotService.getPilotDetailById(pilotId).get();
		ArrayList<FlightModel> list = new ArrayList<FlightModel>();
		list.add(new FlightModel());
		pilot.setPilotFlight(list);
		model.addAttribute("title","Flight - Add");
		model.addAttribute("flight", pilot);
		
		return "addFlight";
	}

	@RequestMapping(value="/flight/add/{pilotId}", method = RequestMethod.POST, params= {"addRow"})
	public String addRow(@ModelAttribute PilotModel pilot, BindingResult bindingResult, Model model) {
		if (pilot.getPilotFlight() == null) {
            pilot.setPilotFlight(new ArrayList<FlightModel>());
        }
		pilot.getPilotFlight().add(new FlightModel());
		model.addAttribute("pilot", pilot);
		return "view-pilot";
	}
	
	@RequestMapping(value="/flight/add/{rowId}", method = RequestMethod.POST, params={"removeRow"})
	public String removeRow(@ModelAttribute PilotModel pilot, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
	    final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    pilot.getPilotFlight().remove(rowId.intValue());
	    
	    model.addAttribute("pilot", pilot);
	    return "view-pilot";
	}
	@RequestMapping(value="/flight/add/{pilotId}", method=RequestMethod.POST, params= {"save"})
	private String addFlightSubmit(@ModelAttribute PilotModel pilot, Model model) {
		PilotModel curPilot = pilotService.getPilotDetailById(pilot.getId()).get();
		for(FlightModel flight: pilot.getPilotFlight()) {
			flight.setPilot(curPilot);
			flightService.addFlight(flight);
		}
		model.addAttribute("title","Flight - Add Success!");
		return "add";
	}
	
	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getId());
		}
		return "delete-flight";
	}
	

	
}
