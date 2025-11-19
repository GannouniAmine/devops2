package com.miro.country_service.controllers;

import com.miro.country_service.beans.Country;
import com.miro.country_service.repositories.CountryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

   
    @GetMapping
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

 
    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable int id) {
        return countryRepository.findById(id).orElse(null);
    }

 
    @GetMapping("/name/{name}")
    public Country getCountryByName(@PathVariable String name) {
        return countryRepository.findByName(name);
    }


    @PostMapping
    public Country addCountry(@RequestBody Country country) {
        return countryRepository.save(country);
    }

 
    @PutMapping("/{id}")
    public Country updateCountry(@PathVariable int id, @RequestBody Country country) {
        Country existing = countryRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(country.getName());
            existing.setPopulation(country.getPopulation());
            return countryRepository.save(existing);
        }
        return null;
    }

 
    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable int id) {
        countryRepository.deleteById(id);
    }
}
