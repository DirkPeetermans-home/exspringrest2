package be.abis.exercise.converter;

import java.time.LocalDate;
import java.time.Period;

import be.abis.exercise.model.ApiPerson;
import be.abis.exercise.model.Person;

public class PersonConverter {
	
	public static ApiPerson convert(Person p) {
		ApiPerson ap = new ApiPerson();
		ap.setBirthDate(LocalDate.of(LocalDate.now().getYear()-p.getAge(), 4, 10));
		ap.setCompany(p.getCompany());
		ap.setEmailAddress(p.getEmailAddress());
		ap.setFirstName(p.getFirstName());
		ap.setLanguage(p.getLanguage());
		ap.setLastName(p.getLastName());
		ap.setPassword(p.getPassword());
		ap.setPersonId(p.getPersonId());
		return ap;
	}
	
	public static Person convert(ApiPerson ap) {
		Person p = new Person();
		p.setAge(Period.between(ap.getBirthDate(), LocalDate.now()).getYears());
		p.setCompany(ap.getCompany());
		p.setEmailAddress(ap.getEmailAddress());
		p.setFirstName(ap.getFirstName());
		p.setLanguage(ap.getLanguage());
		p.setLastName(ap.getLastName());
		p.setPassword(ap.getPassword());
		p.setPersonId(ap.getPersonId());
		return p;
	}

}
