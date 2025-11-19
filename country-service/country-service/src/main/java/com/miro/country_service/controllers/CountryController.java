package com.miro.country_service.controllers;

import com.miro.country_service.beans.Country;
import com.miro.country_service.services.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {
    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/getcountries")
    public ResponseEntity<List<Country>> getCountries() {
        List<Country> countries = countryService.getAllCountries();
        return new ResponseEntity<>(countries, HttpStatus.FOUND);
    }

    @GetMapping("/getcountries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable int id) {
        Country country = countryService.getCountryById(id);
        return new ResponseEntity<>(country, HttpStatus.FOUND);
    }

    @GetMapping("/getcountries/name/{name}")
    public ResponseEntity<Country> getCountryByName(@PathVariable String name) {
        Country country = countryService.getCountryByName(name);
        return new ResponseEntity<>(country, HttpStatus.FOUND);
    }

    @PostMapping("/addcountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        Country savedCountry = countryService.addCountry(country);
        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }

    @PutMapping("/updatecountry")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) {
        Country updatedCountry = countryService.updateCountry(country);
        return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
    }

    @DeleteMapping("/deletecountry/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable int id) {
        Country country = countryService.getCountryById(id);
        countryService.deleteCountry(country);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}