package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Person created with ID ");
    }

    public List<PersonDTO> listAll() {
        List<Person> people = personRepository.findAll();
        return people.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfPersonExists(id);

        return personMapper.toDTO(person);
    }

    public MessageResponseDTO update(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfPersonExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToUpdate);

        return createMessageResponse(updatedPerson.getId(), "Person updated with ID ");
    }

    private MessageResponseDTO createMessageResponse(Long personId, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + personId)
                .build();
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfPersonExists(id);
        personRepository.deleteById(id);
    }

    private Person verifyIfPersonExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }


}
