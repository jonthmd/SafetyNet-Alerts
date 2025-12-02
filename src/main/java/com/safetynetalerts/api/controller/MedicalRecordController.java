package com.safetynetalerts.api.controller;

import com.safetynetalerts.api.dto.MedicalRecordDTO;
import com.safetynetalerts.api.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller with endpoints operations related to medical record.
 */
@Slf4j
@RestController
@RequestMapping("/medicalRecord")
@Tag(name = "Medical Records", description = "Medical records list.")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Retrieves all medical records.
     * @return A list of MedicalRecordDTO representing all medical records.
     */
    @GetMapping("/all")
    @Operation(summary = "Get all medical records.", description = "Returns the complete list of medical records recorded in the JSON.")
    public List<MedicalRecordDTO> getAllMedicalRecords(){
            log.info("Start getAllMedicalRecords() process...");
            return medicalRecordService.getAll();
    }

    /**
     * Retrieves a specific medical record.
     * @param firstName The person's first name of the medical record.
     * @param lastName The person's last name of the medical record.
     * @return MedicalRecordDTO representing the medical record found.
     */
    @GetMapping("/{firstName}/{lastName}")
    @Operation(summary = "Get a medical record.", description = "Returns a medical record by entering firstname and lastname.")
    public MedicalRecordDTO getMedicalRecord(@PathVariable String firstName, @PathVariable String lastName){
            log.info("Start getMedicalRecord() process...");
            return medicalRecordService.getByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Creates a new medical record.
     * @param medicalRecordDTO The information of the medical record to create.
     */
    @PostMapping
    @Operation(summary = "Add a medical record.", description = "Adding a medical record by using firstname, lastname, birthdate, medications and allergies.")
    public void addMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO){
            log.info("Start addMedicalRecord() process...");
            medicalRecordService.create(medicalRecordDTO);
    }

    /**
     * Updates an existing medical record.
     * @param firstName The person's first name of the existing medical record.
     * @param lastName The person's last name of the existing medical record.
     * @param medicalRecordDTO The updated medical record information.
     */
    @PutMapping("/{firstName}/{lastName}")
    @Operation(summary = "Edit a medical record.", description = "Edit a medical record, except firstname and lastname.")
    public void updateMedicalRecord(@PathVariable String firstName, @PathVariable String lastName, @RequestBody MedicalRecordDTO medicalRecordDTO){
            log.info("Start updateMedicalRecord() process...");
            medicalRecordService.update(firstName, lastName, medicalRecordDTO);
    }

    /**
     * Deletes an existing medical record.
     * @param firstName The person's first name of the existing medical record.
     * @param lastName The person's last name of the existing medical record.
     */
    @DeleteMapping("/{firstName}/{lastName}")
    @Operation(summary = "Delete a medical record.", description = "Delete a medical record by using firstname and lastname.")
    public void deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName){
            log.info("Start deleteMedicalRecord() process...");
            medicalRecordService.delete(firstName, lastName);
    }
}
