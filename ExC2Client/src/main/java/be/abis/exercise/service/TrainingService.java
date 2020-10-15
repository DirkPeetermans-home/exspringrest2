package be.abis.exercise.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import be.abis.exercise.exception.EnrollException;
import be.abis.exercise.exception.PersonCanNotBeDeletedException;
import be.abis.exercise.model.Course;
import be.abis.exercise.model.Person;

public interface TrainingService {
	
	List<Person> getAllPersons();
    Person findPerson(int id) throws Exception;
    Person findPerson(String emailAddress, String passWord) throws Exception;
    void addPerson(Person p) throws Exception;
    public void deletePerson(int id) throws PersonCanNotBeDeletedException, Exception;
    void changePassword(Person p, String newPswd) throws IOException;
	public List<Course> showFollowedCourses(Person person);
	public void enrollForSession(Person person, Course course, LocalDate date) throws EnrollException;
	
	public CourseService getCourseService();
	public void setCourseService(CourseService courseService);
}
