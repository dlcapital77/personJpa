package telran.person.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import telran.person.dto.PersonDto;
import telran.person.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;
	
	@PostMapping
	public boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}
	
	@GetMapping("/{id}")
	public PersonDto finPerson(@PathVariable int id) {
		return personService.findPersonById(id);
	}
	
	@GetMapping("/{ageMin}/{ageMax}")
	@ResponseBody
	public Iterable<PersonDto> findPersonsByAges(@PathVariable int ageMin, @PathVariable int ageMax) {
		return personService.findPersonsByAges(ageMin, ageMax);
	}
	
	@GetMapping("/{name}")
	@ResponseBody
	public Iterable<PersonDto> findPersonsByName(@PathVariable String name) {
		return personService.findPersonsByName(name);
	}
}