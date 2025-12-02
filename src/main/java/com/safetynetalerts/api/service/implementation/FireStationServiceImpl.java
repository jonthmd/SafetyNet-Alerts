package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.*;
import com.safetynetalerts.api.mapper.*;
import com.safetynetalerts.api.model.FireStation;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.DataRepository;
import com.safetynetalerts.api.service.FireStationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.safetynetalerts.api.utils.DateFormatterUtil.calculateAge;

/**
 *Implementations of the fire station service interface.
 */
@Service
public class FireStationServiceImpl implements FireStationService {

    private final DataRepository dataRepository;
    private final FireStationMapper fireStationMapper;
    private final MedicalRecordMapper medicalRecordMapper;
    private final FireStationPersonMapper fireStationPersonMapper;
    private final PersonMapper personMapper;

    public FireStationServiceImpl(DataRepository dataRepository, FireStationMapper fireStationMapper, PersonMapper personMapper, MedicalRecordMapper medicalRecordMapper, FireStationPersonMapper fireStationPersonMapper) {
        this.dataRepository = dataRepository;
        this.fireStationMapper = fireStationMapper;
        this.medicalRecordMapper = medicalRecordMapper;
        this.fireStationPersonMapper = fireStationPersonMapper;
        this.personMapper = personMapper;
    }

    /**
     * Retrieves a list of fire stations with their information.
     * @return A list of FireStationDTO.
     */
    @Override
    public List<FireStationDTO> getAll() {
        return dataRepository.getFireStations()
                .stream()
                .map(fireStationMapper::fireStationToFireStationDto)
                .toList();
    }

    /**
     * Searches a fire station with the specified address.
     * @param address The specified address to search a fire station.
     * @return The matching FireStationDTO.
     */
    @Override
    public FireStationDTO getByAddress(String address) {
        return dataRepository.getFireStations()
                .stream()
                .filter(fireStation -> fireStation.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .map(fireStationMapper::fireStationToFireStationDto)
                .orElse(null);
    }

    /**
     * Retrieves children and adults lists living at addresses covered by the station number.
     * @param stationNumber The specified station number to retrieve information.
     * @return FireStationStatsDTO, object containing a list of children and a list of adults.
     */
    @Override
    public FireStationStatsDTO getByStationNumber(String stationNumber) {
        List<String> listAddresses = dataRepository.getFireStations()
                .stream()
                .filter(fireStation -> fireStation.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .toList();

        List<FireStationPersonDTO> listPersons = dataRepository.getPersons()
                .stream()
                .filter(person -> listAddresses.contains(person.getAddress()))
                .map(fireStationPersonMapper::fireStationPersonToFireStationPersonDto)
                .toList();

        int adults = 0;
        int children = 0;

        Map<String, MedicalRecordDTO> medicalRecordDTOMap = dataRepository.getMedicalRecords()
                .stream()
                .map(medicalRecordMapper::medicalRecordToMedicalRecordDto)
                .collect(Collectors.toMap(
                        medicalRecordDTO -> (medicalRecordDTO.getFirstName() +" "+medicalRecordDTO.getLastName()),
                        medicalRecordDTO -> medicalRecordDTO
                ));

        for(FireStationPersonDTO person : listPersons){
            String key = (person.getFirstName() +" "+person.getLastName());
            MedicalRecordDTO medicalRecordDTO = medicalRecordDTOMap.get(key);

            if (medicalRecordDTO != null){
                int age = calculateAge(medicalRecordDTO.getBirthdate());

                if(age <= 18){
                    children ++;
                }else {
                    adults ++;
                }
            }
        }

        FireStationStatsDTO fireStationStatsDTO = new FireStationStatsDTO();
        fireStationStatsDTO.setListPersons(listPersons);
        fireStationStatsDTO.setChildren(children);
        fireStationStatsDTO.setAdults(adults);
        return fireStationStatsDTO;
    }

    /**
     * Retrieves phone numbers for persons covered by the station number.
     * @param stationNumber The specified station number.
     * @return FireStationPhoneAlertDTO, a set of phone numbers.
     */
    @Override
    public FireStationPhoneAlertDTO getPhones(String stationNumber){
        List<String> listAddresses = dataRepository.getFireStations()
                .stream()
                .filter(fireStation -> fireStation.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .toList();

        Set<String> listPhones = dataRepository.getPersons()
                .stream()
                .filter(person -> listAddresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .collect(Collectors.toSet());

        return new FireStationPhoneAlertDTO(listPhones);
    }

    /**
     * Retrieves all information of persons living at the address.
     * @param address The specified address to retrieve person information.
     * @return FireDTO, a list of persons with their addressed and the covered fire station.
     */
    @Override
    public FireDTO getRecordsPersonByAddress(String address){
        List<String> stationsList = dataRepository.getFireStations()
                .stream()
                .filter(fireStation -> fireStation.getAddress().equalsIgnoreCase(address))
                .map(FireStation::getStation)
                .toList();

        List<PersonDTO> personsList = dataRepository.getPersons()
                .stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .map(personMapper::personToPersonDto)
                .toList();

        Map<String, MedicalRecordDTO> medicalRecordDTOMap = dataRepository.getMedicalRecords()
                .stream()
                .map(medicalRecordMapper::medicalRecordToMedicalRecordDto)
                .collect(Collectors.toMap(
                        medicalRecordDTO -> (medicalRecordDTO.getFirstName() +" "+medicalRecordDTO.getLastName()),
                        medicalRecordDTO -> medicalRecordDTO
                ));

        List<FirePersonDTO> firePersonDTOList = new ArrayList<>();

        for(PersonDTO person : personsList){
            String key = (person.getFirstName() +" "+person.getLastName());
            MedicalRecordDTO medicalRecordDTO = medicalRecordDTOMap.get(key);

            if (medicalRecordDTO != null) {
                int age = calculateAge(medicalRecordDTO.getBirthdate());

                FirePersonDTO firePersonDTO = new FirePersonDTO();
                firePersonDTO.setLastName(person.getLastName());
                firePersonDTO.setPhone(person.getPhone());
                firePersonDTO.setAge(age);
                firePersonDTO.setMedications(medicalRecordDTO.getMedications());
                firePersonDTO.setAllergies(medicalRecordDTO.getAllergies());
                firePersonDTOList.add(firePersonDTO);
            }
        }

        FireDTO fireDTO = new FireDTO();
        fireDTO.setStation(stationsList);
        fireDTO.setPersons(firePersonDTOList);

        return fireDTO;
    }

    /**
     * Retrieves addresses and persons living at covered by the specified station number(s).
     * @param stationNumbers A list of station numbers to retrieves the covered homes.
     * @return FireStationFloodDTO, a list of covered addresses and persons living at these homes.
     */
    @Override
    public FireStationFloodDTO getHomes(List<String> stationNumbers) {
        List<String> listAddresses = dataRepository.getFireStations()
                .stream()
                .filter(fireStation -> stationNumbers.contains(fireStation.getStation()))
                .map(FireStation::getAddress)
                .toList();

        List<PersonDTO> personsList = dataRepository.getPersons()
                .stream()
                .map(personMapper::personToPersonDto)
                .toList();

        Map<String, MedicalRecordDTO> medicalRecordDTOMap = dataRepository.getMedicalRecords()
                .stream()
                .map(medicalRecordMapper::medicalRecordToMedicalRecordDto)
                .collect(Collectors.toMap(
                        medicalRecordDTO -> (medicalRecordDTO.getFirstName() +" "+medicalRecordDTO.getLastName()),
                        medicalRecordDTO -> medicalRecordDTO
                ));

        List<FirePersonDTO> firePersonDTOList = new ArrayList<>();

        for(PersonDTO person : personsList){
            String key = (person.getFirstName() +" "+person.getLastName());
            MedicalRecordDTO medicalRecordDTO = medicalRecordDTOMap.get(key);

            if (medicalRecordDTO != null) {
                int age = calculateAge(medicalRecordDTO.getBirthdate());

                FirePersonDTO firePersonDTO = new FirePersonDTO();
                firePersonDTO.setLastName(person.getLastName());
                firePersonDTO.setPhone(person.getPhone());
                firePersonDTO.setAge(age);
                firePersonDTO.setMedications(medicalRecordDTO.getMedications());
                firePersonDTO.setAllergies(medicalRecordDTO.getAllergies());
                firePersonDTOList.add(firePersonDTO);
            }
        }

        FireStationFloodDTO fireStationFloodDTO = new FireStationFloodDTO();
        fireStationFloodDTO.setAddresses(listAddresses);
        fireStationFloodDTO.setPersons(firePersonDTOList);

        return fireStationFloodDTO;
    }

    /**
     * Creates a new fire station.
     * @param fireStationDTO Mapped object containing the fire station details to be created.
     * @return FireStationDTO, the created fire station.
     */
    @Override
    public FireStationDTO create(FireStationDTO fireStationDTO) {
        FireStation fireStation = fireStationMapper.fireStationDtoToFireStation(fireStationDTO);
        dataRepository.getFireStations().add(fireStation);
        return fireStationMapper.fireStationToFireStationDto(fireStation);
    }

    /**
     * Updates an existing fire station based on address and fireStationDTO.
     * @param address The address of the fire station to update.
     * @param fireStationDTO Mapped object containing the fire station details to be updated.
     * @return FireStationDTO, the updated fire station.
     */
    @Override
    public FireStationDTO update(String address, FireStationDTO fireStationDTO) {
        return dataRepository.getFireStations()
                .stream()
                .filter(fireStation -> fireStation.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .map(current -> {
                    current.setStation(fireStationDTO.getStation());
                    return fireStationMapper.fireStationToFireStationDto(current);
                })
                .orElse(null);
    }

    /**
     * Deletes an existing fire station based on the address.
     * @param address The specified address.
     */
    @Override
    public void delete(String address) {
        dataRepository.getFireStations()
                .removeIf(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
    }

}
