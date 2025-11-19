package com.miro.country_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miro.country_service.beans.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Country findByName(String name);
}
