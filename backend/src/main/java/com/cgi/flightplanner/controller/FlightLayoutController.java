package com.cgi.flightplanner.controller;

import com.cgi.flightplanner.dto.layout.FlightSeatPlanDto;
import com.cgi.flightplanner.entities.Flight;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;
import com.cgi.flightplanner.service.FlightService;
import com.cgi.flightplanner.service.SeatFinderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/layouts")
public class FlightLayoutController {
    private final FlightService flightService;
    private final SeatFinderService seatFinderService;

    public FlightLayoutController(FlightService flightService, SeatFinderService seatFinderService) {
        this.flightService = flightService;
        this.seatFinderService = seatFinderService;
    }

    @GetMapping("/layout")
    public FlightSeatPlanDto getFlightLayout(@RequestParam("flightId") Long flightId) {
        Flight flight = flightService
                .findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found"));
        return new FlightSeatPlanDto(flight, Set.of());
    }

    @GetMapping("/layoutWithSeating")
    public FlightSeatPlanDto getFlightLayoutWithSeating(
            @RequestParam("flightId") Long flightId,
            @RequestParam("nrOfSeats") int nrOfSeats,
            @RequestParam("areSeatsClose") boolean areSeatsClose,
            @RequestParam("cabinSection") String cabinSection,
            @RequestParam("needsMoreLegRoom") boolean needsMoreLegRoom,
            @RequestParam("needsCloseToExit") boolean needsCloseToExit,
            @RequestParam("isCloseToWindow") boolean isCloseToWindow
    ) {
        Flight flight = flightService
                .findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found"));

        Set<SeatDefinition> selectedSeats = seatFinderService.findSeats(
                flight,
                nrOfSeats,
                areSeatsClose,
                Set.of(cabinSection),
                needsMoreLegRoom,
                needsCloseToExit,
                isCloseToWindow
        );

        return new FlightSeatPlanDto(flight, selectedSeats);
    }
}
