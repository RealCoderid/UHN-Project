package com.patient.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.patient.model.Patient;
import com.patient.repository.PatientRepository;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceImplTests {

	@InjectMocks
	private PatientServiceImpl patientServiceImpl;

	@Mock
	private PatientRepository patientRepository;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		patientServiceImpl = new PatientServiceImpl();
		patientServiceImpl.setPatientRepository(patientRepository);
	}

	@Autowired
	public PatientRepository setPatientRepository(PatientRepository patientRepository) {
		return this.patientRepository = patientRepository;
	}

	@Test
	public void storePatientTest() {
		Patient patient = new Patient("Kento", "Momota", LocalDate.parse("1992-12-19"));
		when(patientRepository.save(patient)).thenReturn(patient);
		Patient resultPatient = patientServiceImpl.storePatient(patient);
		assertThat(resultPatient, is(equalTo(patient)));

	}

	@Test
	public void listAllPaitientsTest() {
		List<Patient> list = new ArrayList<Patient>();
		Patient patient = new Patient("Kento", "Momota", LocalDate.parse("1992-12-19"));
		list.add(patient);

		when(patientRepository.findAll()).thenReturn(list);

		Iterable<Patient> resultList = patientServiceImpl.listAllPaitients();

		assertThat(resultList, is(equalTo(list)));

	}

	@Test
	public void getPaitientByIdTest() {
		Patient patient = new Patient("Kento", "Momota", LocalDate.parse("1992-12-19"));
		when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

		Patient resultPatient = patientServiceImpl.getPaitientById(1);

		assertThat(resultPatient, is(equalTo(patient)));
	}

	@Test
	public void getPaitientByfirstNameTest() {
		List<Patient> list = new ArrayList<Patient>();
		Patient patient = new Patient("Kento", "Momota", LocalDate.parse("1992-12-19"));
		list.add(patient);

		when(patientRepository.findAll()).thenReturn(list);

		List<Patient> resultList = new ArrayList<Patient>();
		Iterator<Patient> itr = patientRepository.findAll().iterator();
		while (itr.hasNext()) {
			Patient tempPatient = itr.next();
			if (patient.getFirstName().equalsIgnoreCase(tempPatient.getFirstName())) {
				resultList.add(tempPatient);
			}
		}

		Iterable<Patient> finalList = patientServiceImpl.getPaitientByfirstName(patient.getFirstName());

		assertThat(finalList, is(equalTo(resultList)));
	}

}
