package com.miro.country_service.services;

import com.miro.country_service.beans.Country;
import com.miro.country_service.repositories.CountryRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceMockitoTests {
    
    @Mock
    CountryRepository countryrep;
    
    @InjectMocks
    CountryService countryService;

    public List<Country> mycountries;
    
    @Test
    @Order(1)
    void test_getAllCountries() {
        mycountries = new ArrayList<Country>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));        
        
        when(countryrep.findAll()).thenReturn(mycountries);
        assertEquals(2, countryService.getAllCountries().size());
    }
    
    @Test
    @Order(2)
    void test_getCountryByID() {
        Country country = new Country(1,"India","Delhi");
        
        when(countryrep.findById(1)).thenReturn(Optional.of(country));
        Country result = countryService.getCountryById(1);
        
        assertEquals(1, result.getIdCountry());
        assertEquals("India", result.getName());
        assertEquals("Delhi", result.getCapital());
    }
    
    @Test
    @Order(3)
    void test_getCountryByName() {
        mycountries = new ArrayList<Country>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));
        
        when(countryrep.findAll()).thenReturn(mycountries);
        Country result = countryService.getCountryByName("USA");
        
        assertEquals("USA", result.getName());
    }
    
    @Test
    @Order(4)
    void test_addCountry() {
        Country country = new Country(3,"France","Paris");
        when(countryrep.save(country)).thenReturn(country);
        
        Country result = countryService.addCountry(country);
        
        assertEquals(country, result);
        assertEquals("France", result.getName());
    }
    
    @Test
    @Order(5)
    void test_updateCountry() {
        Country country = new Country(3,"Germany","Berlin");
        when(countryrep.save(country)).thenReturn(country);
        
        Country result = countryService.updateCountry(country);
        
        assertEquals(country, result);
        assertEquals("Germany", result.getName());
    }
    
    @Test
    @Order(6)
    void test_deleteCountry() {
        Country country = new Country(3,"Germany","Berlin");
        countryService.deleteCountry(country);
        
        verify(countryrep, times(1)).delete(country);
    }
    
    @Test
    @Order(7)
    void test_getCountryById_NotFound() {
        when(countryrep.findById(999)).thenReturn(Optional.empty());
        
        Country result = countryService.getCountryById(999);
        
        assertNull(result);
    }
    
    @Test
    @Order(8)
    void test_getCountryByName_NotFound() {
        mycountries = new ArrayList<Country>();
        mycountries.add(new Country(1,"India","Delhi"));
        
        when(countryrep.findAll()).thenReturn(mycountries);
        Country result = countryService.getCountryByName("NonExistent");
        
        assertNull(result);
    }
}