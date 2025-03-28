package com.cgi.flightplanner.repository;

import com.cgi.flightplanner.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByAirportCode(String airportCode);
}
