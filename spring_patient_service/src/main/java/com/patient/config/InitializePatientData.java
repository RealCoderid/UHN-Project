package com.patient.config;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.patient.model.Patient;
import com.patient.repository.PatientRepository;

/**
 * @author singh
 *
 */

@Component
public class InitializePatientData implements ApplicationListener<ContextRefreshedEvent> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private PatientRepository patientRepository;

	@Autowired
	public PatientRepository setPatientRepository(PatientRepository patientRepository) {
		return this.patientRepository = patientRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		createTestPatients();
	}
	/**
	 * Create dummy patient data during server startup.
	 * The Patient ID is generated automatically
	 */
	private void createTestPatients() {
		Patient patient1 = new Patient("Kento", "Momota", LocalDate.parse("1992-12-19"));
		Patient patient2 = new Patient("John", "Snow", LocalDate.parse("2000-10-15"));
		patientRepository.save(patient1);
		patientRepository.save(patient2);
		logger.info("Created patient test data ", patient1);
		logger.info("Created patient test data ", patient2);

	}
}