package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.*;
import com.safetynetalerts.api.mapper.MedicalRecordMapper;
import com.safetynetalerts.api.mapper.PersonChildMapper;
import com.safetynetalerts.api.mapper.PersonMapper;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.DataRepository;
import com.safetynetalerts.api.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.safetynetalerts.api.utils.DateFormatterUtil.calculateAge;

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


    @Override
    public List<PersonDTO> getAll() {
        return dataRepository.getPersons()
                .stream()
                .map(personMapper::personToPersonDto)
                .toList();
    }

    @Override
    public PersonChildAlertDTO getChildByAddress(String address){
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

    @Override
    public PersonDTO getByFirstNameAndLastName(String firstName, String lastName) {
        return dataRepository.getPersons()
                .stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .map(personMapper::personToPersonDto)
                .orElse(null);
    }

    @Override
    public PersonDTO create(PersonDTO personDTO) {
        Person person = personMapper.personDtoToPerson(personDTO);
        dataRepository.getPersons().add(person);
        return personMapper.personToPersonDto(person);
    }

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

    @Override
    public void delete(String firstName, String lastName) {
        dataRepository.getPersons()
                .removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName));
    }
}
