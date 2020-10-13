package be.abis.exercise.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.abis.exercise.exception.ApiError;
import be.abis.exercise.exception.PersonCanNotBeDeletedException;
import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.Login;
import be.abis.exercise.model.Person;
import be.abis.exercise.model.Persons;
import be.abis.exercise.service.PersonService;

@RestController
@RequestMapping("persons")
public class PersonController {

	@Autowired PersonService ps;
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findPerson(@PathVariable("id") int id) {

		try {
			Person p = ps.findPerson(id);
			return new ResponseEntity<Object>(p, HttpStatus.OK);
		} catch (PersonNotFoundException pnfe) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			ApiError err = new ApiError("person not found", status.value(), pnfe.getMessage());
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("content-type", MediaType.APPLICATION_PROBLEM_JSON_VALUE);
			return new ResponseEntity<Object>(err, responseHeaders, status);
		}

	}

	@GetMapping("")
	public ArrayList<Person> getAllPersons(){
		return ps.getAllPersons();
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> findPersonByMailAndPwd(@RequestBody Login login) {
		try {
			Person p = ps.findPerson(login.getEmail(), login.getPassword());
			return new ResponseEntity<Object>(p, HttpStatus.OK);
		} catch (PersonNotFoundException pnfe) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			ApiError err = new ApiError("person not found", status.value(), pnfe.getMessage());
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("content-type", MediaType.APPLICATION_PROBLEM_JSON_VALUE);
			return new ResponseEntity<Object>(err, responseHeaders, status);
		}
	}
	
	@GetMapping(path="/findbycompname", produces=MediaType.APPLICATION_XML_VALUE)
	public Persons findPersonsByCompanyName(@RequestParam("compname") String compName) {
		Persons persons = new Persons();
		persons.setPersons(ps.findPersonsByCompanyName(compName));
		return persons;
	}
	
	@PostMapping(path="", consumes= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public void addPerson(@RequestBody Person p){
    	try {
			ps.addPerson(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@DeleteMapping("{id}")
    public void deletePerson(@PathVariable("id") int id) {
		try {
			ps.deletePerson(id);
		} catch (PersonCanNotBeDeletedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	
	
	@PutMapping("{id}")
    public void changePassword(@PathVariable("id") int id, @RequestBody Person person)  {
    	try {
    		System.out.println("changing password to newpswd= " + person.getPassword());
			ps.changePassword(person, person.getPassword());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	
	
	
}
