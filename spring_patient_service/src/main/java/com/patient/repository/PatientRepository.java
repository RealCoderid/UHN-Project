package com.patient.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.patient.model.Patient;


/**
 * Create a repository interface to perform generic CRUD operations on a repository
 *  
 */
@RepositoryRestResource
public interface PatientRepository extends CrudRepository<Patient, Integer> {

}
