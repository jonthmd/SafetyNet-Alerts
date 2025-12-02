package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.*;
import com.safetynetalerts.api.mapper.*;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.DataRepository;
import com.safetynetalerts.api.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.safetynetalerts.api.utils.DateFormatterUtil.calculateAge;

/**
 * Implementations of the person service interface.
 */
@Service
public class PersonServiceImpl implements PersonService {

    private final DataRepository dataRepository;
    private final PersonMapper personMapper;
    private final PersonChildMapper personChildMapper;
    private final MedicalRecordMapper medicalRecordMapper;

    public PersonServiceImpl(DataRepository dataRepository, PersonMapper personMapper, PersonChildMapper personChildMapper, MedicalRecordMapper medicalRecordMapper) {
        this.dataRepository = dataRepository;
        this.personMapper = personMapper;
        this.personChildMapper = personChildMapper;
        this.medicalRecordMapper = medicalRecordMapper;
    }

    /**
     * Retrieves a list of persons with their information.
     * @return A list of PersonDTO.
     */
    @Override
    public List<PersonDTO> getAll() {
        return dataRepository.getPersons()
                .stream()
                .map(personMapper::personToPersonDto)
                .toList();
    }

    /**
     * Searches a person by first name and last name.
     * @param firstName The first name of the person.
     * @param lastName The last name of the person.
     * @return The matching PersonDTO.
     */
    @Override
    public PersonDTO getByFirstNameAndLastName(String firstName, String lastName) {
        return dataRepository.getPersons()
                .stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .map(personMapper::personToPersonDto)
                .orElse(null);
    }

    /**
     * Retrieves children and adults living at the specified address.
     * @param address The specified address used to filter persons.
     * @return PersonChildAlertDTO containing a children list with age, and an adults list.
     */
    @Override
    public PersonChildAlertDTO getChildren(String address){
        List<PersonChildDTO> listPersons = dataRepository.getPersons()
                .stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .map(personChildMapper::personChildToPersonChildDTO)
                .toList();

        Map<String, MedicalRecordDTO> medicalRecordDTOMap = dataRepository.getMedicalRecords()
                .stream()
                .map(medicalRecordMapper::medicalRecordToMedicalRecordDto)
                .collect(Collectors.toMap(
                        medicalRecordDTO -> (medicalRecordDTO.getFirstName() +" "+medicalRecordDTO.getLastName()),
                        medicalRecordDTO -> medicalRecordDTO
                ));

        List<PersonChildDTO> children = new ArrayList<>();
        List<PersonFamilyDTO> adults = new ArrayList<>();

        for(PersonChildDTO person : listPersons){
            String key = (person.getFirstName() +" "+person.getLastName());
            MedicalRecordDTO medicalRecordDTO = medicalRecordDTOMap.get(key);

            if (medicalRecordDTO != null) {
                int age = calculateAge(medicalRecordDTO.getBirthdate());

                if(age <= 18){
                    PersonChildDTO childDTO = new PersonChildDTO();
                    childDTO.setFirstName(person.getFirstName());
                    childDTO.setLastName(person.getLastName());
                    childDTO.setAge(age);
                    children.add(childDTO);

                }else {
                    PersonFamilyDTO familyDTO = new PersonFamilyDTO();
                    familyDTO.setFirstName(person.getFirstName());
                    familyDTO.setLastName(person.getLastName());
                    adults.add(familyDTO);
                }
            }
        }
        return new PersonChildAlertDTO(children, adults);
    }

    /**
     * Retrieves a persons list with their data : address, age, email and medical record.
     * @param lastName The last name of the person.
     * @return PersonInfoLastNameDTO, a list of PersonInfoDTO containing a person info and medical record.
     */
    @Override
    public PersonInfoLastNameDTO getInfoLastName(String lastName) {
        List<PersonDTO> personDTOList = dataRepository.getPersons()
                .stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .map(personMapper::personToPersonDto)
                .toList();

        Map<String, MedicalRecordDTO> medicalRecordDTOMap = dataRepository.getMedicalRecords()
                .stream()
                .map(medicalRecordMapper::medicalRecordToMedicalRecordDto)
                .collect(Collectors.toMap(
                        medicalRecordDTO -> (medicalRecordDTO.getFirstName() +" "+medicalRecordDTO.getLastName()),
                        medicalRecordDTO -> medicalRecordDTO
                ));

        List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();

        for(PersonDTO person : personDTOList){
            String key = (person.getFirstName() +" "+person.getLastName());
            MedicalRecordDTO medicalRecordDTO = medicalRecordDTOMap.get(key);

            if (medicalRecordDTO != null) {
                int age = calculateAge(medicalRecordDTO.getBirthdate());

                PersonInfoDTO personInfoDTO = new PersonInfoDTO();
                personInfoDTO.setLastName(person.getLastName());
                personInfoDTO.setAddress(person.getAddress());
                personInfoDTO.setAge(age);
                personInfoDTO.setEmail(person.getEmail());
                personInfoDTO.setMedications(medicalRecordDTO.getMedications());
                personInfoDTO.setAllergies(medicalRecordDTO.getAllergies());
                personInfoDTOList.add(personInfoDTO);
            }
        }

        PersonInfoLastNameDTO personInfoLastNameDTO = new PersonInfoLastNameDTO();
        personInfoLastNameDTO.setPersons(personInfoDTOList);

        return personInfoLastNameDTO;
    }

    /**
     * Retrieves an email list of people living at the specified city.
     * @param city The specified city to retrieve emails.
     * @return PersonEmailDTO containing a list of emails.
     */
    @Override
    public PersonEmailDTO getEmails(String city) {
        Set<String> emailsList = dataRepository.getPersons()
                .stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .collect(Collectors.toSet());

        return new PersonEmailDTO(emailsList);
    }

    /**
     * Creates a new person.
     * @param personDTO Mapped object containing the person details to be created.
     * @return PersonDTO, the created person.
     */
    @Override
    public PersonDTO create(PersonDTO personDTO) {
        Person person = personMapper.personDtoToPerson(personDTO);
        dataRepository.getPersons().add(person);
        return personMapper.personToPersonDto(person);
    }

    /**
     * Updates an existing person based on first name, last name and PersonDTO.
     * @param firstName The first name of the person to update.
     * @param lastName The last name of the person to update.
     * @param personDTO Mapped object containing the person details to be updated.
     * @return PersonDTO, the updated person.
     */
    @Override
    public PersonDTO update(String firstName, String lastName, PersonDTO personDTO) {
        return dataRepository.getPersons()
                .stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .map(current -> {
                    current.setAddress(personDTO.getAddress());
                    current.setCity(personDTO.getCity());
                    current.setZip(personDTO.getZip());
                    current.setPhone(personDTO.getPhone());
                    current.setEmail(personDTO.getEmail());
                    return personMapper.personToPersonDto(current);
                })
                .orElse(null);
    }

    /**
     * Deletes an existing person based on first name and last name.
     * @param firstName The first name of the person.
     * @param lastName The last name of the person.
     */
    @Override
    public void delete(String firstName, String lastName) {
        dataRepository.getPersons()
                .removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName));
    }
}
