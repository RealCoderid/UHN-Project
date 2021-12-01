package com.patient.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

@Validated
@Entity
public class Patient {

	public Patient() {
		
	}
	
	public Patient(String firstName, String lastName, LocalDate dateOfBirth) {
		super();	
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	
	@ApiModelProperty(notes = "Auto generated unique patient ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer patientID;

	@ApiModelProperty(notes = "The first name of the patient")
	@NotNull(message = "The First Name cannot be null.")
	@NotEmpty(message = "The First Name cannot be empty.")
	private String firstName;

	@ApiModelProperty(notes = "The last name of the patient")
	@NotNull(message = "The last name cannot be null.")
	private String lastName;

	@ApiModelProperty(notes = "The date of birth name of the patient")
	@NotNull(message = "The date of birth cannot be null.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	public Integer getPatientID() {
		return patientID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setPatientID(Integer patientID) {
		this.patientID = patientID;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Patient [patientID=" + patientID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(patientID, other.patientID);
	}

}
