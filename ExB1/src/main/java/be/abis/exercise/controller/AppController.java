package be.abis.exercise.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import be.abis.exercise.exception.PersonCanNotBeDeletedException;
import be.abis.exercise.model.Course;
import be.abis.exercise.model.Login;
import be.abis.exercise.model.Password;
import be.abis.exercise.model.Person;
import be.abis.exercise.service.TrainingService;

@Controller
public class AppController {
     
	@Autowired
	TrainingService trainingService;
	
	Person loggedInPerson;
	List<Course> coursesFound;
	List<Person> personsFound;
	Person removedPerson;
	Person addedPerson;
	
	@GetMapping("/error")
	public String showErrorPage() {
		return "error";
	}
	
	@GetMapping("/")
	public String login(Model model){
		model.addAttribute("login",new Login());
		return "login";
	}
	
	@PostMapping("/")
	public String welcome(Model model, @Valid Login login, BindingResult result) {
		if (result.hasErrors()) {
			return "login";
		}
		loggedInPerson =trainingService.findPerson(login.getEmail(),login.getPassword());
		if (loggedInPerson==null) {
		   result.reject("email", "Login failed, try again");
		   return "login";
		}
		return "redirect:/welcome";
	}
	
	@GetMapping("/welcome")
	public String showWelcome(Model model){
		model.addAttribute("person", loggedInPerson);
		return "welcome";
	}
	
	@GetMapping("/logout")
	public RedirectView logout(){
		return new RedirectView("");
	}	
	
	@GetMapping("/coursesearch")
	public String searchCourses(Model model){
		model.addAttribute("course",new Course());
		return "coursesearch";
	}	
	
	@GetMapping("/courseList")
	public RedirectView showAllCourses(){
		coursesFound = trainingService.getCourseService().findAllCourses();		
		return new RedirectView("showcourses");
	}
	
	@GetMapping("/showcourses")
	public String showCourses(Model model){
		System.out.println(coursesFound);
		model.addAttribute("courses", coursesFound);
		return "showcourses";
	}
	
	
	@GetMapping("/backToCourseSearch")
	public RedirectView backToCourseSearch(){
		return new RedirectView("coursesearch");
	}	
	
	@PostMapping("/findCourseById")
	public String findCourseById(Model model, @Valid @ModelAttribute("course") Course course, BindingResult result){
		
		int id = Integer.parseInt(course.getCourseId());
		System.out.println("Id="+id);
		Course courseFound = trainingService.getCourseService().findCourse(id);	
		System.out.println("coursefound: " + courseFound);
		if (courseFound==null) {
			   result.rejectValue("courseId","", "No course found with this id");
			   return "coursesearch";
		}
		coursesFound = new ArrayList<Course>();
		coursesFound.add(courseFound);
		return "redirect:/showcourses";
	}
	
	@PostMapping("/findCourseByTitle")
	public String findCourseByTitle(Model model, @Valid @ModelAttribute("course") Course course,BindingResult result){

		Course courseFound = trainingService.getCourseService().findCourse(course.getShortTitle());	
		if (courseFound==null) {
			   result.rejectValue("shortTitle", "","No course found with this title");
			   return "coursesearch";
		}
		coursesFound = new ArrayList<Course>();
		coursesFound.add(courseFound);
		return "redirect:/showcourses";
	}
	
	@GetMapping("/backToWelcome")
	public RedirectView backToWelcome(Model model){
		model.addAttribute("person", loggedInPerson);
		return new RedirectView("welcome");
	}
	
	@GetMapping("/backToPersonAdmin")
	public RedirectView backToPersonAdmin(){
		return new RedirectView("personadmin");
	}
	
	
	@GetMapping("/personadmin")
	public String gotopersonadmin(Model model){
		return "personadmin";
	}
	
	@GetMapping("/changepwd")
	public String showChangePwd(Model model) {
		model.addAttribute("pwd",new Password());
		return "changepwd";
	}
	
	@PostMapping("/changepwd")
	public String postNewPassword(Model model, @Valid @ModelAttribute("pwd") Password password, BindingResult result) {
		try {
			System.out.println("new password: " +password.getPassword1());
			if (result.hasErrors()) {
				return "changepwd";
			}
			trainingService.changePassword(loggedInPerson, password.getPassword1());
			password.setConfirmMessage("password was changed");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "changepwd";
		
	}
	
	@GetMapping("/confirmpwdchanged")
	public String showPwdChanged(Model model) {
		model.addAttribute("person", loggedInPerson);
		return "confirmpwdchanged";
	}
	
	@GetMapping("/personsearch")
	public String searchPersons(Model model){
		model.addAttribute("person1",new Person());
		return "personsearch";
	}	
	
	@GetMapping("/personList")
	public RedirectView showAllPersons(){
		personsFound = trainingService.getAllPersons();		
		return new RedirectView("showpersons");
	}
	
	@GetMapping("/showpersons")
	public String showPersons(Model model){
		model.addAttribute("persons", personsFound);
		return "showpersons";
	}
	
	@GetMapping("/backToPersonSearch")
	public RedirectView backToPersonSearch(){
		return new RedirectView("personsearch");
	}
	
	@PostMapping("/findPersonById")
	public String findPersonById(@Valid @ModelAttribute("person1") Person person1, BindingResult result){
		Person personFound = trainingService.findPerson(person1.getPersonId());	
		if (personFound==null) {
			   result.reject("personId", "No person found with this id");
			   return "personsearch";
		}
		personsFound = new ArrayList<Person>();
		personsFound.add(personFound);
		return "redirect:/showpersons";
	}
	
	@GetMapping("/removeperson")
	public String removePerson(Model model){
		model.addAttribute("person", new Person());
		return "removeperson";
	}
	
	@PostMapping("/removePersonById")
	public String removePersonById(@Valid @ModelAttribute("person") Person person,BindingResult result){
        int id = person.getPersonId();
        if (loggedInPerson.getPersonId()==id) {
        	result.reject("personId", "You cannot delete yourself");
			 return "removeperson";
        }
		removedPerson=trainingService.findPerson(id);
		try {
			trainingService.deletePerson(id);
		} catch (PersonCanNotBeDeletedException e) {
			 result.reject("personId", e.getMessage());
			 return "removeperson";
		}	
		return "redirect:/confirmpersonremoved";
	}
	
	@GetMapping("/confirmpersonremoved")
	public String showPersonRemoved(Model model) {
		model.addAttribute("person",removedPerson);
		return "confirmpersonremoved";
	}
	
	@GetMapping("/addperson")
	public String addPerson(Model model){
		model.addAttribute("person", new Person());
		return "addperson";
	}
	
	@PostMapping("/addPerson")
	public String addPerson(@Valid Person person, BindingResult result){
		try {
			if (result.hasErrors()) {
				return "addPerson";
			} 
			trainingService.addPerson(person);
			addedPerson = trainingService.findPerson(person.getPersonId());
		} catch (IOException e) {
			result.reject("personId","This person already exists in our system");
			return "addPerson";
		}	
		return "redirect:/confirmpersonadded";
	}
	
	@GetMapping("/confirmpersonadded")
	public String showPersonAdded(Model model) {
		model.addAttribute("person",addedPerson);
		return "confirmpersonadded";
	}
}
