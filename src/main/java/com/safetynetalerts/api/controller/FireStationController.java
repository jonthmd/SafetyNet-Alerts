package com.safetynetalerts.api.controller;

import com.safetynetalerts.api.dto.FireStationDTO;
import com.safetynetalerts.api.service.FireStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/firestation")
@Tag(name = "firestations", description = "Fire Stations list.")
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping
    @Operation(summary = "Get all fire stations.", description = "Returns the complete list of fire stations.")
    public List<FireStationDTO> getAll(){
        return fireStationService.getAll();
    }

    @GetMapping("/{address}")
    @Operation(summary = "Get one station.", description = "Returns a station by entering an address.")
    public FireStationDTO getFireStation(@PathVariable String address){
        return fireStationService.getByAddress(address);
    }

    @PostMapping
    @Operation(summary = "Add a station.", description = "Adding a station by using station and address.")
    public void addFireStation(@RequestBody FireStationDTO fireStationDTO){
        fireStationService.create(fireStationDTO);
    }

    @PutMapping("/{address}")
    @Operation(summary = "Edit a fire station.", description = "Edit the number station.")
    public void updateFireStation(@PathVariable String address, @RequestBody FireStationDTO fireStationDTO){
        fireStationService.update(address, fireStationDTO);
    }

    @DeleteMapping("/{address}")
    @Operation(summary = "Delete a fire station.", description = "Delete a fire station by using his address.")
    public void deleteFireStation(@PathVariable String address){
        fireStationService.delete(address);
    }

}
