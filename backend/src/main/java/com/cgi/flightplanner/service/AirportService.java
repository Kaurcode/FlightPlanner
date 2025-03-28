package com.cgi.flightplanner.service;

import com.cgi.flightplanner.entities.Airport;
import com.cgi.flightplanner.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirportService extends CrudService<Airport, AirportRepository> {
    public AirportService(AirportRepository airportRepository) {
        super(airportRepository);
    }

    public Optional<Airport> findByCode(String airportCode) {
        return repository.findByAirportCode(airportCode);
    }
}
