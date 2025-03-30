package com.cgi.flightplanner.dto.layout;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.Flight;
import com.cgi.flightplanner.entities.Plane;
import com.cgi.flightplanner.entities.planelayout.PlaneModel;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;
import com.cgi.flightplanner.entities.planelayout.SeatPlan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FlightSeatPlanDto {
    private final Long flightId;

    private final List<CabinSectionDto> cabinSections;

    public FlightSeatPlanDto(Flight flight, Set<SeatDefinition> selectedSeats) {
        this.flightId = flight.getId();
        Plane plane = flight.getPlane();
        PlaneModel planeModel = plane.getPlaneModel();
        SeatPlan seatPlan = planeModel.getSeatPlan();

        Map<SeatDefinition, BookableSeat> seats = flight
                .getSeats()
                .stream().collect(Collectors.toMap(
                        BookableSeat::getSeat,
                        seat -> seat,
                        (existing, replacement) -> existing,  // TODO: Probably should throw an error
                        HashMap::new
                ));

        this.cabinSections = seatPlan
                .getCabinSections()
                .stream()
                .map(section -> new CabinSectionDto(section, seats, selectedSeats))
                .toList();
    }

    public Long getFlightId() {
        return flightId;
    }

    public List<CabinSectionDto> getCabinSections() {
        return cabinSections;
    }
}
