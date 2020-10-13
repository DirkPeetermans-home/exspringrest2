package be.abis.exercise.service;

import java.util.List;

import be.abis.exercise.model.Person;

public interface PersonService {
	
	    List<Person> getAllPersons();
	    Person findPerson(int id) throws Exception ;
	    Person findPerson(String emailAddress, String passWord) throws Exception ;
	    void addPerson(Person p);
	    void deletePerson(int id) ;
	    void changePassword(Person p);

}
