package com.miro.country_service.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miro.country_service.beans.Country;
import com.miro.country_service.controllers.CountryController;
import com.miro.country_service.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerMockMvcTests {

    private MockMvc mockMvc;

    @Mock
    private CountryService countryService;

    private AutoCloseable closeable;

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<Country> mycountries;
    private Country country;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new CountryController(countryService)).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @Order(1)
    void test_getAllCountries() throws Exception {
        mycountries = new ArrayList<>();
        mycountries.add(new Country(1, "India", "Delhi"));
        mycountries.add(new Country(2, "USA", "Washington"));

        when(countryService.getAllCountries()).thenReturn(mycountries);

        mockMvc.perform(get("/api/getcountries"))
               .andExpect(status().isFound())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].name").value("India"))
               .andExpect(jsonPath("$[1].name").value("USA"));
    }

    @Test
    @Order(2)
    void test_getCountryById() throws Exception {
        country = new Country(1, "India", "Delhi");

        when(countryService.getCountryById(1)).thenReturn(country);

        mockMvc.perform(get("/api/getcountries/{id}", 1))
               .andExpect(status().isFound())
               .andExpect(jsonPath("$.idCountry").value(1))
               .andExpect(jsonPath("$.name").value("India"))
               .andExpect(jsonPath("$.capital").value("Delhi"));
    }

    @Test
    @Order(3)
    void test_getCountryByName() throws Exception {
        country = new Country(1, "India", "Delhi");

        when(countryService.getCountryByName("India")).thenReturn(country);

        mockMvc.perform(get("/api/getcountries/name/{name}", "India"))
               .andExpect(status().isFound())
               .andExpect(jsonPath("$.name").value("India"))
               .andExpect(jsonPath("$.capital").value("Delhi"));
    }

    @Test
    @Order(4)
    void test_addCountry() throws Exception {
        country = new Country(3, "France", "Paris");

        when(countryService.addCountry(any(Country.class))).thenReturn(country);

        mockMvc.perform(post("/api/addcountry")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(country)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.name").value("France"));
    }

    @Test
    @Order(5)
    void test_updateCountry() throws Exception {
        country = new Country(1, "Germany", "Berlin");

        when(countryService.updateCountry(any(Country.class))).thenReturn(country);

        mockMvc.perform(put("/api/updatecountry")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(country)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.capital").value("Berlin"));
    }

    @Test
    @Order(6)
    void test_deleteCountry() throws Exception {
        country = new Country(1, "India", "Delhi");

        when(countryService.getCountryById(1)).thenReturn(country);
        doNothing().when(countryService).deleteCountry(country);

        mockMvc.perform(delete("/api/deletecountry/{id}", 1))
               .andExpect(status().isNoContent());
    }
}