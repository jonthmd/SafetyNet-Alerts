package com.safetynetalerts.api.controller;

import com.safetynetalerts.api.dto.*;
import com.safetynetalerts.api.service.FireStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller with endpoints operations related to fire station.
 */
@Slf4j
@RestController
//@RequestMapping("/fireStation")
@Tag(name = "Fire Stations", description = "Fire Stations list.")
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    /**
     * Retrieves all fire stations.
     * @return A list of FireStationDTO representing all fire stations.
     */
    @GetMapping("/fireStation/all")
    @Operation(summary = "Get all fire stations.", description = "Returns the complete list of fire stations recorded in the JSON.")
    public List<FireStationDTO> getAllFireStations() {
        log.info("Start getAllFireStations() process...");
        return fireStationService.getAll();
    }

    /**
     * Retrieves a specific fire station.
     * @param address The address of the fire station.
     * @return FireStationDTO representing the fire station found.
     */
    @GetMapping("/fireStation/{address}")
    @Operation(summary = "Get a fire station.", description = "Returns a station by entering address.")
    public FireStationDTO getFireStation(@PathVariable String address) {
        log.info("Start getFireStation() process...");
        return fireStationService.getByAddress(address);
    }

    /**
     * Retrieves persons covered by specific fire station.
     * @param stationNumber The specified station number.
     * @return FireStationStatsDTO, a list containing persons information with their age.
     */
    @GetMapping(value = "/fireStation", params = "stationNumber")
    @Operation(summary = "Get persons covered by station number.", description = "Returns persons covered by station number.")
    public FireStationStatsDTO getPersonsByStationNumber(@RequestParam String stationNumber) {
            log.info("Start getPersonsByStationNumber() process...");
            return fireStationService.getByStationNumber(stationNumber);
    }

    /**
     * Retrieves phone numbers of persons covered by the fire station.
     * @param fireStation The fire station number.
     * @return FireStationPhoneAlertDTO, a set of phone numbers.
     */
    @GetMapping("/phoneAlert")
    @Operation(summary = "Get phones.", description = "Returns the complete list of phones covered by fire station.")
    public FireStationPhoneAlertDTO getPhonesByFireStation(@RequestParam String fireStation) {
            log.info("Start getPhonesByFireStation() process...");
            return fireStationService.getPhones(fireStation);
    }

    /**
     * Retrieves all information of the person living at the specified address.
     * @param address The specified address.
     * @return FireDTO, a list containing the station number and medical record information.
     */
    @GetMapping("/fire")
    @Operation(summary = "Get medical records person covered by station number.", description = "Returns medical records person covered by station number.")
    public FireDTO getRecordsPersons(@RequestParam String address) {
            log.info("Start getRecordsPersons() process...");
            return fireStationService.getRecordsPersonByAddress(address);
    }

    /**
     * Retrieves homes covered by a list of fire station.
     * @param stationNumbers The list of fire stations.
     * @return FireStationFloodDTO containing addresses and associated persons.
     */
    @GetMapping("/flood/stations")
    @Operation(summary = "Get medical records person grouped by address.", description = "Returns medical records person grouped by address.")
    public FireStationFloodDTO getFlood(@RequestParam List<String> stationNumbers) {
            log.info("Start getFlood() process...");
            return fireStationService.getHomes(stationNumbers);
    }

    /**
     * Creates a new fire station.
     * @param fireStationDTO The information of the fire station to create.
     */
    @PostMapping("/fireStation")
    @Operation(summary = "Add a fire station.", description = "Adding a station by using station and address.")
    public void addFireStation(@RequestBody FireStationDTO fireStationDTO) {
            log.info("Start addFireStation() process...");
            fireStationService.create(fireStationDTO);
    }

    /**
     * Updates an existing fire station.
     * @param address The specified address.
     * @param fireStationDTO The updated fire station information.
     */
    @PutMapping("/fireStation/{address}")
    @Operation(summary = "Edit a fire station.", description = "Edit the number station.")
    public void updateFireStation(@PathVariable String address, @RequestBody FireStationDTO fireStationDTO) {
            log.info("Start updateFireStation() process...");
            fireStationService.update(address, fireStationDTO);
    }

    /**
     * Deletes an existing fire station.
     * @param address The address of the existing fire station.
     */
    @DeleteMapping("/fireStation/{address}")
    @Operation(summary = "Delete a fire station.", description = "Delete a fire station by using address.")
    public void deleteFireStation(@PathVariable String address) {
            log.info("Start deleteFireStation() process...");
            fireStationService.delete(address);
    }
}
