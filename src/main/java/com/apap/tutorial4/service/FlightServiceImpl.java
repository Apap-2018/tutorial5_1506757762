package com.apap.tutorial4.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.repository.FlightDb;

@Service
@Transactional
public class FlightServiceImpl implements FlightService{

	@Autowired
	private FlightDb flightDb;
	
	@Override
	public void addFlight(FlightModel flight) {
		// TODO Auto-generated method stub
		flightDb.save(flight);
		
	}
	public void deleteFlightById(Long id) {
		flightDb.deleteById(id);
	}
	@Override
	public List<FlightModel> getListFlight(long id) {
		return flightDb.findPilotById(id);
	}
	
}
