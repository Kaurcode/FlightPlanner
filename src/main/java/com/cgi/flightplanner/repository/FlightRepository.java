package com.cgi.flightplanner.repository;

import com.cgi.flightplanner.entities.Airport;
import com.cgi.flightplanner.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    List<Flight> findByOriginAndDepartureTimeAfter(Airport origin, Instant departureTime);

    List<Flight> findByOriginAndDepartureTimeAfterAndArrivalTimeBefore(
            Airport origin, Instant departureTime, Instant arrivalTime
    );
}
