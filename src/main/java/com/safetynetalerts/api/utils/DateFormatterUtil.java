package com.safetynetalerts.api.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Util used to calculate age of persons from their birthdate in String format.
 */
@Component
public class DateFormatterUtil {

    public static int calculateAge(String birthdate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(birthdate,dateTimeFormatter);
        return Period.between(localDate, LocalDate.now()).getYears();
    }

}
