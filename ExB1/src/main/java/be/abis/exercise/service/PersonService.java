package be.abis.exercise.service;

import java.util.List;

import be.abis.exercise.model.Person;

public interface PersonService {
	
	    List<Person> getAllPersons();
	    Person findPerson(int id) ;
	    Person findPerson(String emailAddress, String passWord) ;
	    void addPerson(Person p);
	    void deletePerson(int id) ;
	    void changePassword(Person p, String newPwd);

}
