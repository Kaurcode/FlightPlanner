package com.cgi.flightplanner.config;

import com.cgi.flightplanner.entities.Airport;
import com.cgi.flightplanner.entities.Flight;
import com.cgi.flightplanner.entities.Plane;
import com.cgi.flightplanner.entities.planelayout.PlaneModel;
import com.cgi.flightplanner.service.AirportService;
import com.cgi.flightplanner.service.FlightService;
import com.cgi.flightplanner.service.PlaneModelService;
import com.cgi.flightplanner.service.PlaneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final AirportService airportService;
    private final PlaneModelService planeModelService;
    private final PlaneService planeService;
    private final FlightService flightService;

    public DatabaseInitializer(
            AirportService airportService,
            PlaneModelService planeModelService,
            PlaneService planeService,
            FlightService flightService
    ) {
        this.airportService = airportService;
        this.planeModelService = planeModelService;
        this.planeService = planeService;
        this.flightService = flightService;
    }

    // This method is entirely ChatGPT generated, because I'm too lazy to think of fake flight data
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create airports
        Airport tallinn = airportService.save(new Airport("Tallinn", "TLL", "Estonia"));
        Airport london = airportService.save(new Airport("London", "LHR", "United Kingdom"));
        Airport paris = airportService.save(new Airport("Paris", "CDG", "France"));
        Airport frankfurt = airportService.save(new Airport("Frankfurt", "FRA", "Germany"));
        Airport helsinki = airportService.save(new Airport("Helsinki", "HEL", "Finland"));

        // Create plane models (manufacturers)
        PlaneModel airbusA320 = planeModelService.save(new PlaneModel("A320", "Airbus"));
        PlaneModel boeing737 = planeModelService.save(new PlaneModel("737", "Boeing"));
        PlaneModel embraerE190 = planeModelService.save(new PlaneModel("E190", "Embraer"));

        // Create planes
        Plane plane1 = planeService.save(new Plane("PL-A320-01", airbusA320));
        Plane plane2 = planeService.save(new Plane("PL-737-07", boeing737));
        Plane plane3 = planeService.save(new Plane("PL-E190-03", embraerE190));

        // Define available airline companies
        List<String> airlines = List.of("Lufthansa", "Finnair", "Ryanair", "British Airways", "Air France");

        // Create flights
        LocalDate startDate = LocalDate.of(2025, 4, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 30);

        List<Airport> airports = List.of(tallinn, london, paris, frankfurt, helsinki);
        List<Plane> planes = List.of(plane1, plane2, plane3);
        Random random = new Random();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            for (int i = 0; i < 3; i++) { // 3 flights per day
                Airport origin = airports.get(random.nextInt(airports.size()));
                List<Airport> destinations = airports.stream()
                        .filter(a -> !a.equals(origin))
                        .toList();
                Airport destination = destinations.get(random.nextInt(destinations.size()));

                Plane selectedPlane = planes.get(random.nextInt(planes.size()));
                String airline = airlines.get(random.nextInt(airlines.size()));

                LocalTime departureLocal = LocalTime.of(random.nextInt(18) + 6, random.nextBoolean() ? 0 : 30);
                Instant departureTime = date.atTime(departureLocal).atZone(ZoneId.of("UTC")).toInstant();
                Instant arrivalTime = departureTime.plus(Duration.ofHours(2 + random.nextInt(3))); // 2–4 hours

                String identifier = airline.substring(0, 2).toUpperCase() + "-" + String.format("%04d", random.nextInt(10000));

                Flight flight = new Flight(
                        origin,
                        destination,
                        departureTime,
                        arrivalTime,
                        identifier,
                        airline,
                        selectedPlane
                );

                flightService.save(flight);
            }
        }
    }
}
