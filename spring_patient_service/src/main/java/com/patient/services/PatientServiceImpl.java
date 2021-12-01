package com.patient.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patient.model.Patient;
import com.patient.repository.PatientRepository;

@Service
public class PatientServiceImpl implements IPatientService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private PatientRepository patientRepository;

	@Autowired
	public PatientRepository setPatientRepository(PatientRepository patientRepository) {
		return this.patientRepository = patientRepository;
	}

	/**
	 * Add a patient into the database. Here H2 is used as a database running as in
	 * memory mode.
	 * @param patient The Patient stored in the database.
	 */
	@Override
	public Patient storePatient(Patient patient) {
		logger.info("Persisting patient data = " + patient);
		return patientRepository.save(patient);
	}

	/**
	 * Gets the list of all patient in the database.
	 * 
	 * @return The patient list with all records in the database. Returns empty list
	 *         if no record is found.
	 */
	@Override
	public Iterable<Patient> listAllPaitients() {
		logger.info("listing all patients");
		return patientRepository.findAll();
	}

	/**
	 * Gets the unique patient in the database with the input patient ID.
	 * @param patient id The unique identity of the patient.
	 * @return The patient with matching patient id. Returns null if no record is
	 *         found.
	 */
	@Override
	public Patient getPaitientById(Integer id) {
		logger.info("Retrieving patients by inpout id = " + id);
		return patientRepository.findById(id).orElse(null);
	}

	/**
	 * Gets the list of unique patient in the database with the input patient c.
	 * @param patient name The first name of the patient.
	 * @return The patient list with matching patient names. Returns empty list if
	 *         no record is found.
	 */
	@Override
	public List<Patient> getPaitientByfirstName(String firstName) {
		logger.info("Retrieving records by patient's first name = " + firstName);
		List<Patient> resultList = new ArrayList<Patient>();
		Iterator<Patient> itr = patientRepository.findAll().iterator();
		while (itr.hasNext()) {
			Patient patient = itr.next();
			if (firstName.equalsIgnoreCase(patient.getFirstName())) {
				resultList.add(patient);
			}
		}
		return resultList;
	}

}
