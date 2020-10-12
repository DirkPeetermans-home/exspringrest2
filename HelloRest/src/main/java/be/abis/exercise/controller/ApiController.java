package be.abis.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.abis.exercise.model.Person;
import be.abis.exercise.service.PersonService;

@RestController
//@RequestMapping("persons")
public class ApiController {
	
	@Autowired
	PersonService personService;
	
	@GetMapping("persons/{id}")
	public Person findPerson(@PathVariable("id") int id) {
		return personService.getPerson(id);
	}
	
	

}
