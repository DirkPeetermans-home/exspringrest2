package be.abis.exercise.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.abis.exercise.exception.EnrollException;
import be.abis.exercise.exception.PersonCanNotBeDeletedException;
import be.abis.exercise.model.Course;
import be.abis.exercise.model.Person;

@Service
public class AbisTrainingService implements TrainingService {
    
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private PersonService personService;
	
	@Override
	public CourseService getCourseService() {
		return courseService;
	}


	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	@Override
	public List<Person> getAllPersons() {
		return personService.getAllPersons();
	}

	@Override
	public Person findPerson(int id)  {
		return personService.findPerson(id);
	}

	@Override
	public Person findPerson(String emailAddress, String passWord)  {
		return personService.findPerson(emailAddress, passWord);
	}

	@Override
	public void addPerson(Person p) throws IOException {
		personService.addPerson(p);
	}

	@Override
	public void deletePerson(int id) throws PersonCanNotBeDeletedException {
		personService.deletePerson(id);	
	}

	@Override
	public void changePassword(Person p, String newPswd) throws IOException {
		personService.changePassword(p,newPswd);		
	}

	@Override
	public List<Course> showFollowedCourses(Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enrollForSession(Person person, Course course, LocalDate date) throws EnrollException {
		// TODO Auto-generated method stub

	}






}
