package com.miro.country_service.demo;

import com.miro.country_service.beans.Country;
import com.miro.country_service.repositories.CountryRepository;
import com.miro.country_service.services.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CountryServiceIntegrationTest {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepository;

    private Country testCountry;

    @BeforeEach
    public void setUp() {
        // On vide la base et on ajoute un pays pour le test
        countryRepository.deleteAll();
        testCountry = new Country();
        testCountry.setName("Germany");
        testCountry.setCapital("Berlin");
        testCountry = countryRepository.save(testCountry);
    }

    @Test
    public void testUpdateCountry() {
        // On récupère le pays existant
        Country countryToUpdate = countryRepository.findById(testCountry.getIdCountry()).orElse(null);
        assertNotNull(countryToUpdate, "Le pays doit exister avant la mise à jour");

        countryToUpdate.setCapital("Munich");
        Country updated = countryRepository.save(countryToUpdate);

        assertEquals("Munich", updated.getCapital(), "La capitale doit être mise à jour");
    }

    @Test
    public void testDeleteCountry() {
        // On récupère le pays existant
        Country countryToDelete = countryRepository.findById(testCountry.getIdCountry()).orElse(null);
        assertNotNull(countryToDelete, "Le pays doit exister avant suppression");

        countryRepository.delete(countryToDelete);

        assertFalse(countryRepository.existsById(testCountry.getIdCountry()), "Le pays doit être supprimé");
    }
}
