package com.safetynetalerts.api.controller;

import com.safetynetalerts.api.dto.MedicalRecordDTO;
import com.safetynetalerts.api.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/medicalRecord")
@Tag(name = "Medical Records", description = "Medical records list.")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all medical records.", description = "Returns the complete list of medical records recorded in the JSON.")
    public List<MedicalRecordDTO> getAll(){
       return medicalRecordService.getAll();
    }

    @GetMapping("/{firstName}/{lastName}")
    @Operation(summary = "Get a medical record.", description = "Returns a medical record by entering firstname and lastname.")
    public MedicalRecordDTO getMedicalRecord(@PathVariable String firstName, @PathVariable String lastName){
        return medicalRecordService.getByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping
    @Operation(summary = "Add a medical record.", description = "Adding a medical record by using firstname, lastname, birthdate, medications and allergies.")
    public void addMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO){
        medicalRecordService.create(medicalRecordDTO);
    }

    @PutMapping("/{firstName}/{lastName}")
    @Operation(summary = "Edit a medical record.", description = "Edit a medical record, except firstname and lastname.")
    public void updateMedicalRecord(@PathVariable String firstName, @PathVariable String lastName, @RequestBody MedicalRecordDTO medicalRecordDTO){
        medicalRecordService.update(firstName, lastName, medicalRecordDTO);
    }

    @DeleteMapping("/{firstName}/{lastName}")
    @Operation(summary = "Delete a medical record.", description = "Delete a medical record by using firstname and lastname.")
    public void deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName){
        medicalRecordService.delete(firstName, lastName);
    }
}
