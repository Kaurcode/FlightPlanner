package com.cgi.flightplanner.entities.helpers.seatfinder;

import com.cgi.flightplanner.dto.layout.CabinSectionDto;
import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.Flight;
import com.cgi.flightplanner.entities.Plane;
import com.cgi.flightplanner.entities.planelayout.PlaneModel;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;
import com.cgi.flightplanner.entities.planelayout.SeatPlan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightSeatPlanHelper {
    private List<CabinSectionHelper> cabinSections;

    public FlightSeatPlanHelper(Flight flight) {
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
                .map(section -> new CabinSectionHelper(section, seats))
                .toList();
    }

    public List<CabinSectionHelper> getCabinSections() {
        return cabinSections;
    }

    public void setCabinSections(List<CabinSectionHelper> cabinSections) {
        this.cabinSections = cabinSections;
    }
}