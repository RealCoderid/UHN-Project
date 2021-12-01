package com.patient.services;

import java.util.List;

import com.patient.model.Patient;



public interface IPatientService {
	
	  Iterable<Patient> listAllPaitients();

	  Patient getPaitientById(Integer id);

	  Patient storePatient(Patient patient);
	  
	  List<Patient> getPaitientByfirstName(String firstName);
} 