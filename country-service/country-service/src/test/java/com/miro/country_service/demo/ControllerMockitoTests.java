package com.miro.country_service.demo;

import com.miro.country_service.beans.Country;
import com.miro.country_service.controllers.CountryController;
import com.miro.country_service.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerMockitoTests {

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> mycountries;
    Country country;

    @Test
    @Order(1)
    void test_getAllCountries() {
        mycountries = new ArrayList<>();
        mycountries.add(new Country(1, "India", "Delhi"));
        mycountries.add(new Country(2, "USA", "Washington"));

        when(countryService.getAllCountries()).thenReturn(mycountries);
        ResponseEntity<List<Country>> response = countryController.getCountries();

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(countryService, times(1)).getAllCountries();
    }

    @Test
    @Order(2)
    void test_getCountryById() {
        country = new Country(1, "India", "Delhi");
        
        when(countryService.getCountryById(1)).thenReturn(country);
        ResponseEntity<Country> response = countryController.getCountryById(1);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("India", response.getBody().getName());
        verify(countryService, times(1)).getCountryById(1);
    }

    @Test
    @Order(3)
    void test_getCountryByName() {
        country = new Country(1, "India", "Delhi");
        
        when(countryService.getCountryByName("India")).thenReturn(country);
        ResponseEntity<Country> response = countryController.getCountryByName("India");

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("Delhi", response.getBody().getCapital());
        verify(countryService, times(1)).getCountryByName("India");
    }

    @Test
    @Order(4)
    void test_addCountry() {
        country = new Country(3, "France", "Paris");
        
        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.addCountry(country);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("France", response.getBody().getName());
        verify(countryService, times(1)).addCountry(country);
    }

    @Test
    @Order(5)
    void test_updateCountry() {
        country = new Country(1, "Germany", "Berlin");
        
        when(countryService.updateCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.updateCountry(country);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Berlin", response.getBody().getCapital());
        verify(countryService, times(1)).updateCountry(country);
    }

    @Test
    @Order(6)
    void test_deleteCountry() {
        country = new Country(1, "India", "Delhi");
        
        when(countryService.getCountryById(1)).thenReturn(country);
        doNothing().when(countryService).deleteCountry(country);
        
        ResponseEntity<Void> response = countryController.deleteCountry(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(countryService, times(1)).getCountryById(1);
        verify(countryService, times(1)).deleteCountry(country);
    }

    @Test
    @Order(7)
    void test_getCountryById_NotFound() {
        when(countryService.getCountryById(999)).thenReturn(null);
        ResponseEntity<Country> response = countryController.getCountryById(999);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}