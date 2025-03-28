package com.cgi.flightplanner.service;

import com.cgi.flightplanner.entities.Airport;
import com.cgi.flightplanner.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Optional<Airport> findByCode(String airportCode) {
        return airportRepository.findByAirportCode(airportCode);
    }
}
