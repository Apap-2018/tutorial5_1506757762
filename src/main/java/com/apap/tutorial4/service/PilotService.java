package com.apap.tutorial4.service;

import java.util.Optional;

import com.apap.tutorial4.model.PilotModel;


public interface PilotService {
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	Optional<PilotModel> getPilotDetailById(Long id);
	void addPilot(PilotModel pilot);
	void deletePilot(PilotModel pilot);
}
