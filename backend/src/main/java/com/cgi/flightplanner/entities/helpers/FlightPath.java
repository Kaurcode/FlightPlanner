package com.cgi.flightplanner.entities.helpers;

import com.cgi.flightplanner.entities.Flight;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class FlightPath {
    private List<Flight> flights;
    private Duration totalTravelTime;

    public FlightPath(List<Flight> flights) {
        this.flights = flights;

        Flight firstFlight = flights.getFirst();
        Flight lastFlight = flights.getLast();

        Instant firstDepartureTime = firstFlight.getDepartureTime();
        Instant lastArrivalTime = lastFlight.getArrivalTime();

        this.totalTravelTime = Duration.between(firstDepartureTime, lastArrivalTime);
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Duration getTotalTravelTime() {
        return totalTravelTime;
    }
}
