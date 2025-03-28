package com.cgi.flightplanner.dto;

import com.cgi.flightplanner.entities.FlightPath;

import java.time.Duration;
import java.util.List;

public class FlightPathDto {
    private final List<FlightDto> flights;
    private final Duration totalTravelTime;

    public FlightPathDto(FlightPath flightPath) {
        this.flights = flightPath.getFlights().stream().map(FlightDto::new).toList();
        this.totalTravelTime = flightPath.getTotalTravelTime();
    }

    public List<FlightDto> getFlights() {
        return flights;
    }

    public Duration getTotalTravelTime() {
        return totalTravelTime;
    }
}
