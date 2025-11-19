package com.miro.country_service.demo;

import com.miro.country_service.beans.Country;
import com.miro.country_service.repositories.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CountryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    void testGetAllCountries() throws Exception {
        // Given
        Country country = new Country(1, "France", "Paris");
        countryRepository.save(country);

        // When & Then
        mockMvc.perform(get("/api/getcountries"))
               .andExpect(status().isFound())
               .andExpect(jsonPath("$[0].name").value("France"));
    }

    @Test
    void testGetCountryById() throws Exception {
        // Given
        Country country = new Country(1, "France", "Paris");
        Country savedCountry = countryRepository.save(country);

        // When & Then
        mockMvc.perform(get("/api/getcountries/{id}", savedCountry.getIdCountry()))
               .andExpect(status().isFound())
               .andExpect(jsonPath("$.name").value("France"))
               .andExpect(jsonPath("$.capital").value("Paris"));
    }

    @Test
    void testAddCountry() throws Exception {
        // Given
        String countryJson = """
            {
                "idCountry": 1,
                "name": "Spain",
                "capital": "Madrid"
            }
        """;

        // When & Then
        mockMvc.perform(post("/api/addcountry")
               .contentType(MediaType.APPLICATION_JSON)
               .content(countryJson))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.name").value("Spain"));
    }
}