package be.abis.exercise.service;

import org.springframework.stereotype.Service;

import be.abis.exercise.model.Person;

@Service
public class AbisPersonService implements PersonService {

	@Override
	public Person getPerson(int id) {
		return new Person("Sandy","Schillebeeckx");
	}

}
