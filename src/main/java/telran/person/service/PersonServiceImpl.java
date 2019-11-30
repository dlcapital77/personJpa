package telran.person.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.person.dao.PersonRepository;
import telran.person.dto.PersonDto;
import telran.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;

	@Override
	@Transactional
	public boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		Person person = convertToPerson(personDto);
		personRepository.save(person);
		return true;
	}

	private Person convertToPerson(PersonDto personDto) {
		return Person.builder().id(personDto.getId()).name(personDto.getName())
				.birthDate(LocalDate.parse(personDto.getBirthDate())).age(personDto.getAge()).build();
	}

	@Override
	public PersonDto findPersonById(int id) {
		Person person = personRepository.findById(id).orElse(null);
		if (person == null) {
			return null;
		}
		return convertToPersonDto(person);
	}

	private PersonDto convertToPersonDto(Person person) {
		return PersonDto.builder().name(person.getName()).id(person.getId()).birthDate(person.getBirthDate().toString())
				.age(person.getAge())
				.build();
	}

	@Override
	public Iterable<PersonDto> findPersonsByName(String name) {
		List<Person> list = personRepository.findByName(name);
		return convertPersonListToPersonDtoList(list);
	}

	@Override
	public Iterable<PersonDto> findPersonsByAges(int min, int max) {
		List<Person> list = personRepository.findByAgeBetween(min, max);
		return convertPersonListToPersonDtoList(list);
	}
	
	
	private List<PersonDto> convertPersonListToPersonDtoList(List<Person> list) {
//		List<PersonDto> resultList = new ArrayList<>();
//		list.forEach(p -> resultList.add(convertToPersonDto(p)));
//		return resultList;
		return list.stream()
				.map(p -> convertToPersonDto(p))
				.collect(Collectors.toList());
	}
}
