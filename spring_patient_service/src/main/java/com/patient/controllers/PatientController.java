package com.patient.controllers;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.patient.exception.CustomException;
import com.patient.exception.ResourceNotFoundException;
import com.patient.model.Patient;
import com.patient.services.IPatientService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/patient")
public class PatientController {

	private IPatientService patientService;

	@Autowired
	public void setPatientService(IPatientService patientService) {
		this.patientService = patientService;
	}

	/**
	 * Returns the list of all the patients in the database.
	 * 
	 * @exception ResourceNotFoundException If no matching records are found with
	 *                                      patient ID
	 * @param patient The list of patients present in the database. Returns empty
	 *                list for no records.
	 */
	@ApiOperation(value = "list all patients", response = Patient.class)
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Iterable<Patient>> listPatient() {
		Iterable<Patient> patientList = patientService.listAllPaitients();

		if (patientList == null) {
			throw new ResourceNotFoundException("No patient records found");
		}
		return new ResponseEntity<Iterable<Patient>>(patientList, HttpStatus.OK);
	}

	/**
	 * Gets the list of unique patient in the database with the input patient ID.
	 * 
	 * @exception ResourceNotFoundException If no matching records are found with
	 *                                      patient ID
	 * @param patient id The unique identity of the patient. id cannot be empty or
	 *                null.
	 * @return The patient with matching patient id. This will always be only one
	 *         record, as the ID is unique
	 */
	@ApiOperation(value = "Search for a specific patient with the patient ID", response = Patient.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Patient> getSpecificPatient(@PathVariable Integer id) {
		Patient patient = patientService.getPaitientById(id);
		if (patient == null) {
			throw new ResourceNotFoundException("Invalid patient id : " + id);
		}
		return new ResponseEntity<Patient>(patient, HttpStatus.OK);
	}

	/**
	 * Gets the list of all the patients in the database with the input first name.
	 * 
	 * @exception ResourceNotFoundException If no matching records are found with
	 *                                      patient first name.
	 * @param patient firstName The firstName of the patient. firstName cannot be
	 *                empty or null.
	 * @return The patient list with matching first name. It Can be more than one
	 *         records.
	 * 
	 */
	@ApiOperation(value = "Search for all patient with same first name", response = Patient.class)
	@RequestMapping(value = "/name", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Patient>> getPatientbyName(@RequestParam String firstName) {
		List<Patient> patientList = patientService.getPaitientByfirstName(firstName);
		if (patientList == null) {
			throw new ResourceNotFoundException("No Records found with patient firstname : " + firstName);
		}
		return new ResponseEntity<List<Patient>>(patientList, HttpStatus.OK);
	}

	/**
	 * Add a list of patients in the database. Patient ID is Auto generated with or
	 * without user input.
	 * 
	 * @exception CustomException If the date of birth of the patient is of a future
	 *                            date.
	 * @param patient The list of Patients to be stored in the database.
	 */
	@ApiOperation(value = "Add multiple Patient records")
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> savePatient(@Valid @RequestBody List<Patient> patient) {

		if (patient == null || patient.size() == 0) {
			throw new CustomException("Patient List cannot be empty");
		}
		for (Patient var : patient) {			
			if (var.getDateOfBirth().isAfter(LocalDate.now())) {
				throw new CustomException("Date of birth cannot be a future value: = " + var.getDateOfBirth());
			}
			patientService.storePatient(var);
		}
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

}
