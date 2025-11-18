package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.*;
import com.safetynetalerts.api.mapper.FireStationMapper;
import com.safetynetalerts.api.mapper.FireStationPersonMapper;
import com.safetynetalerts.api.mapper.MedicalRecordMapper;
import com.safetynetalerts.api.mapper.PersonMapper;
import com.safetynetalerts.api.model.FireStation;
import com.safetynetalerts.api.repository.DataRepository;
import com.safetynetalerts.api.service.FireStationService;
import com.safetynetalerts.api.utils.DateFormatterUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.safetynetalerts.api.utils.DateFormatterUtil.calculateAge;

@Service
public class FireStationServiceImpl implements FireStationService {

    private final DataRepository dataRepository;
    private final FireStationMapper fireStationMapper;
    private final MedicalRecordMapper medicalRecordMapper;
    private final FireStationPersonMapper fireStationPersonMapper;

    public FireStationServiceImpl(DataRepository dataRepository, FireStationMapper fireStationMapper, PersonMapper personMapper, MedicalRecordMapper medicalRecordMapper, FireStationPersonMapper fireStationPersonMapper, DateFormatterUtil dateFormatterUtil) {
        this.dataRepository = dataRepository;
        this.fireStationMapper = fireStationMapper;
        this.medicalRecordMapper = medicalRecordMapper;
        this.fireStationPersonMapper = fireStationPersonMapper;
    }


    @Override
    public List<FireStationDTO> getAll() {
        return dataRepository.getFireStations()
                .stream()
                .map(fireStationMapper::fireStationToFireStationDto)
                .toList();
    }

    @Override
    public FireStationDTO getByAddress(String address) {
        return dataRepository.getFireStations()
                .stream()
                .filter(fireStation -> fireStation.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .map(fireStationMapper::fireStationToFireStationDto)
                .orElse(null);
    }

    @Override
    public FireStationStatsDTO getByStationNumber(String stationNumber) {
        //1 chercher dans firestation, adresses avec station = stationNumber
        List<String> listAdresses = dataRepository.getFireStations()
                .stream()
                .filter(fireStation -> fireStation.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .toList();

        //2 récupérer les personnes liées aux adresses trouvées (liste)
        List<FireStationPersonDTO> listPersons = dataRepository.getPersons()
                .stream()
                .filter(person -> listAdresses.contains(person.getAddress()))
                .map(fireStationPersonMapper::fireStationPersonToFireStationPersonDto)
                .toList();


        //3 incrémenter liste mineur ou liste majeur selon age de la personne. Aller dans medicalRecord pour récupérer le birthdate des personnes de la liste.
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


//        // Mis à jour. Voir collection map pour éviter les deux boucles.
//        int adults = 0;
//        int children = 0;
//
//        List<MedicalRecord> medicalRecords = dataRepository.getMedicalRecords();
//
//        for (FireStationPersonDTO person : listPersons) {
//
//            MedicalRecordDTO medicalRecordPerson = null;
//            for (MedicalRecord medicalRecord : medicalRecords) {
//                MedicalRecordDTO dto = medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord);
//
//                if (dto.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
//                        dto.getLastName().equalsIgnoreCase(person.getLastName())) {
//                    medicalRecordPerson = dto;
//                    break;
//                }
//            }
//
//            if (medicalRecordPerson != null) {
//                int age = calculateAge(medicalRecordPerson.getBirthdate());
//
//                if (age <= 18) {
//                    children++;
//                } else {
//                    adults++;
//                }
//            }
//        }

        //original version
//        int adults = 0;
//        int children = 0;
//
//        for(PersonDTO person : listPersons){
//            MedicalRecordDTO medicalRecordPerson = dataRepository.getMedicalRecords()
//                    .stream()
//                    .map(medicalRecordMapper::medicalRecordToMedicalRecordDto)
//                    .filter(medicalRecordDTO -> medicalRecordDTO.getFirstName().equalsIgnoreCase(person.getFirstName()) && medicalRecordDTO.getLastName().equalsIgnoreCase(person.getLastName()))
//                    .findFirst()
//                    .orElse(null);
//
//            if (medicalRecordPerson != null){
//                int age = calculateAge(medicalRecordPerson.getBirthdate());
//
//                if(age <= 18){
//                    children ++;
//                }else {
//                    adults ++;
//                }
//            }

        FireStationStatsDTO fireStationStatsDTO = new FireStationStatsDTO();
        fireStationStatsDTO.setListPersons(listPersons);
        fireStationStatsDTO.setAdults(adults);
        fireStationStatsDTO.setChildren(children);
        return fireStationStatsDTO;
    }

    @Override
    public FireStationDTO create(FireStationDTO fireStationDTO) {
        FireStation fireStation = fireStationMapper.fireStationDtoToFireStation(fireStationDTO);
        dataRepository.getFireStations().add(fireStation);
        return fireStationMapper.fireStationToFireStationDto(fireStation);
    }

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

    @Override
    public void delete(String address) {
        dataRepository.getFireStations()
                .removeIf(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
    }

}
