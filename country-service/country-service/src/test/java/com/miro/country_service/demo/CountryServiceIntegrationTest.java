package com.miro.country_service.demo;

import com.miro.country_service.beans.Country;
import com.miro.country_service.repositories.CountryRepository;
import com.miro.country_service.services.CountryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountryServiceIntegrationTest {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepository;

    private Country country;

    @Test
    @Order(1)
    void testAddCountry() {
        country = new Country(100, "TestLand", "TestCity");
        countryService.addCountry(country);

        Country found = countryService.getCountryById(100);
        assertNotNull(found);
        assertEquals("TestLand", found.getName());
        assertEquals("TestCity", found.getCapital());
    }

    @Test
    @Order(2)
    void testGetAllCountries() {
        assertTrue(countryService.getAllCountries().size() > 0);
    }

    @Test
    @Order(3)
    void testGetCountryByName() {
        Country found = countryService.getCountryByName("TestLand");
        assertNotNull(found);
        assertEquals("TestCity", found.getCapital());
    }

    @Test
    @Order(4)
    void testUpdateCountry() {
        country.setCapital("NewTestCity");
        countryService.updateCountry(country);

        Country found = countryService.getCountryById(100);
        assertEquals("NewTestCity", found.getCapital());
    }

    @Test
    @Order(5)
    void testDeleteCountry() {
        countryService.deleteCountry(country);
        Country found = countryService.getCountryById(100);
        assertNull(found);
    }
}
