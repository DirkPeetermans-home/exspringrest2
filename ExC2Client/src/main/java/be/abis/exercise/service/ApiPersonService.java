package be.abis.exercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.abis.exercise.converter.PersonConverter;
import be.abis.exercise.exception.ApiError;
import be.abis.exercise.exception.PersonAlreadyExistsException;
import be.abis.exercise.exception.PersonCanNotBeDeletedException;
import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.ApiPerson;
import be.abis.exercise.model.Login;
import be.abis.exercise.model.Person;

@Service
public class ApiPersonService implements PersonService {
	
	@Autowired
	private RestTemplate rt;
	
	private String baseUri = "http://localhost:8085/exercise/api/persons";
	

	@Override
	public List<Person> getAllPersons() {		
		ResponseEntity<ArrayList<ApiPerson>> persons = rt.exchange(baseUri,HttpMethod.GET,null,new ParameterizedTypeReference<ArrayList<ApiPerson>>(){});
		ArrayList<ApiPerson> apiPersonList = persons.getBody();
		List<Person> personList=apiPersonList.stream().map(ap -> PersonConverter.convert(ap)).collect(Collectors.toList());
		return personList;
	}

	@Override
	public Person findPerson(int id) throws Exception {
		ResponseEntity<? extends Object> re=null;
		ApiPerson ap = null;
		try {
			re= rt.getForEntity(baseUri+"/"+id,ApiPerson.class);
			ap=(ApiPerson)re.getBody();
			return PersonConverter.convert(ap);
		}catch (HttpStatusCodeException e) {
			if (HttpStatus.NOT_FOUND == e.getStatusCode()) {
				String serr = e.getResponseBodyAsString();
				ApiError ae=new ObjectMapper().readValue(serr,ApiError.class);
				throw new PersonNotFoundException(ae.getDescription());
				
			} else {
				throw new Exception("some other error occurred");
			}
		}
		
	
	}

	@Override
	public Person findPerson(String emailAddress, String passWord) throws Exception{
		Login login = new Login();
		login.setEmail(emailAddress);
		login.setPassword(passWord);
		ResponseEntity<? extends Object> re=null;
		ApiPerson ap = null;
		try {
			re= rt.postForEntity(baseUri+"/login",login,ApiPerson.class);
			ap=(ApiPerson)re.getBody();
			return PersonConverter.convert(ap);
		}catch (HttpStatusCodeException e) {
			if (HttpStatus.NOT_FOUND == e.getStatusCode()) {
				String serr = e.getResponseBodyAsString();
				ApiError ae=new ObjectMapper().readValue(serr,ApiError.class);
				throw new PersonNotFoundException(ae.getDescription());
				
			} else {
				throw new Exception("some other error occurred");
			}
		}
		
	}

	@Override
	public void addPerson(Person p) throws Exception {
		ApiPerson ap = PersonConverter.convert(p);
		ResponseEntity<? extends Object> re=null;
		try {
			re=rt.postForEntity(baseUri,ap,Void.class);
			System.out.println("person added ");
		}catch (HttpStatusCodeException e) {
			if (HttpStatus.CONFLICT == e.getStatusCode()) {
				String serr = e.getResponseBodyAsString();
				ApiError ae=new ObjectMapper().readValue(serr,ApiError.class);
				throw new PersonAlreadyExistsException(ae.getDescription());
				
			} else {
				throw new Exception("some other error occurred");
			}
		}
	}

	@Override
	public void deletePerson(int id) throws Exception {
		ResponseEntity<? extends Object> re=null;
		try {
			re=rt.exchange(baseUri+"/"+id,HttpMethod.DELETE,null,Void.class);
            System.out.println("person deleted ");
		}catch (HttpStatusCodeException e) {
			if (HttpStatus.CONFLICT == e.getStatusCode()) {
				String serr = e.getResponseBodyAsString();
				ApiError ae=new ObjectMapper().readValue(serr,ApiError.class);
				throw new PersonCanNotBeDeletedException(ae.getDescription());
				
			} else {
				throw new Exception("some other error occurred");
			}
		}
	}
	
	@Override
	public void changePassword(Person p){
		ApiPerson ap = PersonConverter.convert(p);
        rt.put(baseUri+"/"+p.getPersonId(),ap);
        System.out.println("password changed ");
	}

}
