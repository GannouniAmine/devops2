package com.miro.country_service.demo;

import com.miro.country_service.beans.Country;
import com.miro.country_service.repositories.CountryRepository;
import com.miro.country_service.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceMockitoTests {
    
    @Mock
    private CountryRepository countryRepository;
    
    @InjectMocks
    private CountryService countryService;

    private AutoCloseable closeable;

    private List<Country> mycountries;
    
    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
    
    @Test
    @Order(1)
    void test_getAllCountries() {
        mycountries = new ArrayList<>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));        
        
        when(countryRepository.findAll()).thenReturn(mycountries);
        
        List<Country> result = countryService.getAllCountries();
        
        assertEquals(2, result.size());
        verify(countryRepository, times(1)).findAll();
    }
    
    @Test
    @Order(2)
    void test_getCountryByID() {
        Country country = new Country(1,"India","Delhi");
        
        when(countryRepository.findById(1)).thenReturn(Optional.of(country));
        Country result = countryService.getCountryById(1);
        
        assertEquals(1, result.getIdCountry());
        assertEquals("India", result.getName());
        assertEquals("Delhi", result.getCapital());
    }
    
    @Test
    @Order(3)
    void test_getCountryByName() {
        mycountries = new ArrayList<>();
        mycountries.add(new Country(1,"India","Delhi"));
        mycountries.add(new Country(2,"USA","Washington"));
        
        when(countryRepository.findAll()).thenReturn(mycountries);
        Country result = countryService.getCountryByName("USA");
        
        assertEquals("USA", result.getName());
    }
    
    @Test
    @Order(4)
    void test_addCountry() {
        Country country = new Country(3,"France","Paris");
        when(countryRepository.save(country)).thenReturn(country);
        
        Country result = countryService.addCountry(country);
        
        assertEquals(country, result);
        assertEquals("France", result.getName());
    }
    
    @Test
    @Order(5)
    void test_updateCountry() {
        Country country = new Country(3,"Germany","Berlin");
        when(countryRepository.save(country)).thenReturn(country);
        
        Country result = countryService.updateCountry(country);
        
        assertEquals(country, result);
        assertEquals("Germany", result.getName());
    }
    
    @Test
    @Order(6)
    void test_deleteCountry() {
        Country country = new Country(3,"Germany","Berlin");
        countryService.deleteCountry(country);
        
        verify(countryRepository, times(1)).delete(country);
    }
    
    @Test
    @Order(7)
    void test_getCountryById_NotFound() {
        when(countryRepository.findById(999)).thenReturn(Optional.empty());
        
        Country result = countryService.getCountryById(999);
        
        assertNull(result);
    }
    
    @Test
    @Order(8)
    void test_getCountryByName_NotFound() {
        mycountries = new ArrayList<>();
        mycountries.add(new Country(1,"India","Delhi"));
        
        when(countryRepository.findAll()).thenReturn(mycountries);
        Country result = countryService.getCountryByName("NonExistent");
        
        assertNull(result);
    }
}