package com.technoelevate.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoelevate.file.dto.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	Person findByPersonId(int personId);
}
