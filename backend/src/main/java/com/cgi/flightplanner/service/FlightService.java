package com.cgi.flightplanner.service;

import com.cgi.flightplanner.entities.Airport;
import com.cgi.flightplanner.entities.Flight;
import com.cgi.flightplanner.entities.Plane;
import com.cgi.flightplanner.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FlightService extends CrudService<Flight, FlightRepository>{
    private final BookableSeatService bookableSeatService;

    public FlightService(FlightRepository flightRepository, BookableSeatService bookableSeatService) {
        super(flightRepository);
        this.bookableSeatService = bookableSeatService;
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

    public Flight generateFlight(
            Airport origin,
            Airport destination,
            Instant departureTime,
            Instant arrivalTime,
            String identifier,
            String company,
            Plane plane
    ) {
        return this.save(new Flight(origin, destination, departureTime, arrivalTime, identifier, company, plane));
    }

    @Override
    public Flight save(Flight flight) {
        if (flight.getSeats() == null || flight.getSeats().isEmpty()) {
            flight.setSeats(bookableSeatService.generateBookableSeatsForFlight(flight));
        }

        return super.save(flight);
    }
}
