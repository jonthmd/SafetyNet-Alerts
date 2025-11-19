package com.safetynetalerts.api.controller;

import com.safetynetalerts.api.dto.FireDTO;
import com.safetynetalerts.api.dto.FireStationDTO;
import com.safetynetalerts.api.dto.FireStationStatsDTO;
import com.safetynetalerts.api.service.FireStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/fireStation")
@Tag(name = "Fire Stations", description = "Fire Stations list.")
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all fire stations.", description = "Returns the complete list of fire stations recorded in the JSON.")
    public List<FireStationDTO> getAll(){
        return fireStationService.getAll();
    }

    @GetMapping("/{address}")
    @Operation(summary = "Get a fire station.", description = "Returns a station by entering address.")
    public FireStationDTO getFireStation(@PathVariable String address){
        return fireStationService.getByAddress(address);
    }

    @GetMapping("/fire")
    @Operation(summary = "Get medical records person covered by station number.", description = "Returns medical records person covered by station number.")
    public List<FireDTO> getRecordsPersons(@RequestParam String address){
        return fireStationService.getRecordsPersonByAddress(address);
    }

    @GetMapping(params = "stationNumber")
    @Operation(summary = "Get persons covered by station number.", description = "Returns persons covered by station number.")
    public FireStationStatsDTO getPersonsByStationNumber(@RequestParam String stationNumber){
        return fireStationService.getByStationNumber(stationNumber);
    }


    @PostMapping
    @Operation(summary = "Add a fire station.", description = "Adding a station by using station and address.")
    public void addFireStation(@RequestBody FireStationDTO fireStationDTO){
        fireStationService.create(fireStationDTO);
    }

    @PutMapping("/{address}")
    @Operation(summary = "Edit a fire station.", description = "Edit the number station.")
    public void updateFireStation(@PathVariable String address, @RequestBody FireStationDTO fireStationDTO){
        fireStationService.update(address, fireStationDTO);
    }

    @DeleteMapping("/{address}")
    @Operation(summary = "Delete a fire station.", description = "Delete a fire station by using address.")
    public void deleteFireStation(@PathVariable String address){
        fireStationService.delete(address);
    }

}
