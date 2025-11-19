package com.miro.country_service.services;

import com.miro.country_service.beans.Country;
import com.miro.country_service.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private CountryRepository countryRepository;

	public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(int id) {
        Optional<Country> country = countryRepository.findById(id);
        return country.orElse(null);
    }

    public Country getCountryByName(String name) {
        List<Country> countries = countryRepository.findAll();
        return countries.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(Country country) {
        return countryRepository.save(country);
    }

    public void deleteCountry(Country country) {
        countryRepository.delete(country);
    }
}