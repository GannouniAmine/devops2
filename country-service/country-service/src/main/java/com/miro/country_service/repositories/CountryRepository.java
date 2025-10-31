package com.miro.country_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miro.country_service.beans.Country;

public interface CountryRepository extends JpaRepository<Country,Integer>  {

}
