package com.miro.country_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miro.country_service.beans.Country;
import com.miro.country_service.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerMockMvcTests {

    @Autowired
    MockMvc mockMvc;

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> mycountries;
    Country country;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
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
               .andExpect(jsonPath("$[1].name").value("USA"))
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(2)
    void test_getCountryById() throws Exception {
        country = new Country(1, "India", "Delhi");

        when(countryService.getCountryById(1)).thenReturn(country);

        mockMvc.perform(get("/api/getcountries/{id}", 1))
               .andExpect(status().isFound())
               .andExpect(MockMvcResultMatchers.jsonPath("$.idCountry").value(1))
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("India"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.capital").value("Delhi"))
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(3)
    void test_getCountryByName() throws Exception {
        country = new Country(1, "India", "Delhi");

        when(countryService.getCountryByName("India")).thenReturn(country);

        mockMvc.perform(get("/api/getcountries/name/{name}", "India"))
               .andExpect(status().isFound())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("India"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.capital").value("Delhi"))
               .andDo(MockMvcResultHandlers.print());
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
               .andExpect(jsonPath("$.name").value("France"))
               .andDo(MockMvcResultHandlers.print());
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
               .andExpect(jsonPath("$.capital").value("Berlin"))
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(6)
    void test_deleteCountry() throws Exception {
        country = new Country(1, "India", "Delhi");

        when(countryService.getCountryById(1)).thenReturn(country);
        doNothing().when(countryService).deleteCountry(country);

        mockMvc.perform(delete("/api/deletecountry/{id}", 1))
               .andExpect(status().isNoContent())
               .andDo(MockMvcResultHandlers.print());
    }
}