package be.abis.exercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import be.abis.exercise.converter.PersonConverter;
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
	public Person findPerson(int id) {
		ApiPerson ap = rt.getForObject(baseUri+"/"+id,ApiPerson.class);
		System.out.println("bday= " +ap.getBirthDate());
		return PersonConverter.convert(ap);
	}

	@Override
	public Person findPerson(String emailAddress, String passWord) {
		Login login = new Login();
		login.setEmail(emailAddress);
		login.setPassword(passWord);
		ApiPerson ap = rt.postForObject(baseUri+"/login",login,ApiPerson.class);
		return PersonConverter.convert(ap);
	}


	@Override
	public void addPerson(Person p) {
		ApiPerson ap = PersonConverter.convert(p);
		rt.postForObject(baseUri,ap,Void.class);
		System.out.println("person added ");
	}

	@Override
	public void deletePerson(int id) {
		rt.delete(baseUri+"/"+id);
        System.out.println("person deleted ");
	}
	
	@Override
	public void changePassword(Person p, String newPwd){
		p.setPassword(newPwd);
		ApiPerson ap = PersonConverter.convert(p);
        rt.put(baseUri+"/"+p.getPersonId(),ap);
        System.out.println("password changed ");
	}

}
