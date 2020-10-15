package be.abis.exercise.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="persons")
@XmlAccessorType (XmlAccessType.FIELD)
public class Persons {

	@XmlElement(name="person")
	private  List<Person> persons;

	public  List<Person> getPersons() {
		return persons;
	}

	public  void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	
	
	
}
