package com.cgi.flightplanner.service;

import com.cgi.flightplanner.entities.Airport;
import com.cgi.flightplanner.entities.Flight;
import com.cgi.flightplanner.entities.FlightPath;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FlightPathService {
    private final FlightService flightService;

    public FlightPathService(FlightService flightService) {
        this.flightService = flightService;
    }

    @FunctionalInterface
    private interface FlightSupplier {
        List<Flight> getFlights(Airport origin, Instant earliestDepartureTime);
    }

    public List<FlightPath> getAllFlightPaths(
            Airport origin,
            Airport destination,
            Instant earliestDepartureTime
    ) {
        List<Flight> flights = flightService.findDeparturesAfterTime(
                origin, earliestDepartureTime
        );

        List<FlightPath> flightPaths = new ArrayList<>();
        Set<Airport> visitedAirports = new HashSet<>(List.of(origin));

        dfs(
                visitedAirports,
                flights,
                new ArrayList<>(),
                flightPaths,
                destination,
                flightService::findDeparturesAfterTime
        );

        return flightPaths;
    }

    public List<FlightPath> getAllFlightPaths(
            Airport origin,
            Airport destination,
            Instant earliestDepartureTime,
            Instant latestArrivalTime
    ) {
        List<Flight> flights = flightService.findDeparturesAfterAndBeforeTime(
                origin, earliestDepartureTime, latestArrivalTime
        );

        List<FlightPath> flightPaths = new ArrayList<>();
        Set<Airport> visitedAirports = new HashSet<>(List.of(origin));

        dfs(
                visitedAirports,
                flights,
                new ArrayList<>(),
                flightPaths,
                destination,
                (Airport airport, Instant earliestTime) -> flightService.findDeparturesAfterAndBeforeTime(
                        airport,
                        earliestTime,
                        latestArrivalTime
                )
        );

        return flightPaths;
    }

    private void dfs(
            Set<Airport> visitedNodes,
            List<Flight> flights,
            List<Flight> currentPath,
            List<FlightPath> foundPaths,
            Airport finalDestination,
            FlightSupplier flightSupplier
    ) {
        flights.stream()
                .filter(flight -> !visitedNodes.contains(flight.getDestination()))
                .forEach(flight -> {
                    currentPath.add(flight);

                    if (flight.getDestination().equals(finalDestination)) {
                        List<Flight> foundPath = List.copyOf(currentPath);
                        foundPaths.add(new FlightPath(foundPath));
                    } else {
                        Airport nextAirport = flight.getDestination();
                        visitedNodes.add(nextAirport);
                        dfs(
                                visitedNodes,
                                flightSupplier.getFlights(nextAirport, flight.getArrivalTime()),
                                currentPath,
                                foundPaths,
                                finalDestination,
                                flightSupplier
                        );
                        visitedNodes.remove(nextAirport);
                    }

                    currentPath.removeLast();
                });
    }
}
