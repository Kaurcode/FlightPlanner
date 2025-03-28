package com.cgi.flightplanner.controller;

import com.cgi.flightplanner.dto.FlightPathDto;
import com.cgi.flightplanner.entities.Airport;
import com.cgi.flightplanner.service.AirportService;
import com.cgi.flightplanner.service.FlightPathService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
public class FlightSearchController {
    private final FlightPathService flightPathService;
    private final AirportService airportService;

    public FlightSearchController(FlightPathService flightPathService, AirportService airportService) {
        this.flightPathService = flightPathService;
        this.airportService = airportService;
    }

    @GetMapping("/flightPaths")
    public List<FlightPathDto> getFlightPaths(
            @RequestParam(value = "origin") String originCode,
            @RequestParam(value = "destination") String destinationCode,
            @RequestParam(value = "earliestTime")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            ZonedDateTime earliestDepartureTime,
            @RequestParam(value = "latestTime", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Optional<ZonedDateTime> latestArrivalTime)
    {
        Airport origin = airportService.findByCode(originCode).orElseThrow();
        Airport destination = airportService.findByCode(destinationCode).orElseThrow();

        Instant earliest = earliestDepartureTime.toInstant();

        return latestArrivalTime
                .map(latest -> flightPathService.getAllFlightPathsSorted(
                        origin, destination, earliest, latest.toInstant()
                ))
                .orElseGet(() -> flightPathService.getAllFlightPathsSorted(origin, destination, earliest))
                .stream().map(FlightPathDto::new).toList();
    }
}
