package com.miro.country_service.demo;

import com.miro.country_service.beans.Country;
import com.miro.country_service.repositories.CountryRepository;
import com.miro.country_service.services.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CountryServiceIntegrationTest {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    void testIntegrationGetAllCountries() {
        // Given
        Country country1 = new Country(1, "France", "Paris");
        Country country2 = new Country(2, "Germany", "Berlin");
        countryRepository.save(country1);
        countryRepository.save(country2);

        // When
        List<Country> countries = countryService.getAllCountries();

        // Then
        assertNotNull(countries);
        assertTrue(countries.size() >= 2);
    }

    @Test
    void testIntegrationGetCountryById() {
        // Given
        Country country = new Country(1, "France", "Paris");
        Country savedCountry = countryRepository.save(country);

        // When
        Country foundCountry = countryService.getCountryById(savedCountry.getIdCountry());

        // Then
        assertNotNull(foundCountry);
        assertEquals("France", foundCountry.getName());
        assertEquals("Paris", foundCountry.getCapital());
    }

    @Test
    void testIntegrationGetCountryByName() {
        // Given
        Country country = new Country(1, "France", "Paris");
        countryRepository.save(country);

        // When
        Country foundCountry = countryService.getCountryByName("France");

        // Then
        assertNotNull(foundCountry);
        assertEquals("France", foundCountry.getName());
    }

    @Test
    void testIntegrationAddCountry() {
        // Given
        Country country = new Country(3, "Italy", "Rome");

        // When
        Country savedCountry = countryService.addCountry(country);

        // Then
        assertNotNull(savedCountry);
        assertEquals("Italy", savedCountry.getName());
        assertEquals("Rome", savedCountry.getCapital());
    }

    @Test
    void testIntegrationUpdateCountry() {
        // Given
        Country country = new Country(1, "France", "Paris");
        Country savedCountry = countryRepository.save(country);
        savedCountry.setCapital("Lyon");

        // When
        Country updatedCountry = countryService.updateCountry(savedCountry);

        // Then
        assertNotNull(updatedCountry);
        assertEquals("Lyon", updatedCountry.getCapital());
    }

    @Test
    void testIntegrationDeleteCountry() {
        // Given
        Country country = new Country(1, "France", "Paris");
        Country savedCountry = countryRepository.save(country);

        // When
        countryService.deleteCountry(savedCountry);

        // Then
        Country deletedCountry = countryService.getCountryById(savedCountry.getIdCountry());
        assertNull(deletedCountry);
    }
}