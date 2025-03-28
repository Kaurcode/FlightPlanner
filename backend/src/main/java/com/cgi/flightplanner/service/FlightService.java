package com.cgi.flightplanner.service;

import com.cgi.flightplanner.entities.Airport;
import com.cgi.flightplanner.entities.Flight;
import com.cgi.flightplanner.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FlightService extends CrudService<Flight, FlightRepository>{
    public FlightService(FlightRepository flightRepository) {
        super(flightRepository);
    }

    public List<Flight> findDeparturesAfterTime(Airport origin, Instant earliestTime) {
        return repository.findByOriginAndDepartureTimeAfter(origin, earliestTime);
    }

    public List<Flight> findDeparturesAfterAndBeforeTime(
            Airport origin, Instant earliestDepartureTime, Instant latestArrivalTime
    ) {
        return repository.findByOriginAndDepartureTimeAfterAndArrivalTimeBefore(
                origin, earliestDepartureTime, latestArrivalTime
        );
    }
}
