package com.cgi.flightplanner.dto;

import com.cgi.flightplanner.entities.Airport;
import com.cgi.flightplanner.entities.Flight;
import com.cgi.flightplanner.entities.Plane;
import com.cgi.flightplanner.entities.planelayout.PlaneModel;

import java.time.Instant;

public class FlightDto {
    private final Long id;

    private final String originCity;
    private final String originAirportCode;
    private final String originCountry;

    private final String destinationCity;
    private final String destinationAirportCode;
    private final String destinationCountry;

    private final Instant departureTime;
    private final Instant arrivalTime;

    private final String flightIdentifier;
    private final String flightCompany;

    private final String planeIdentifier;

    private final String planeModelIdentifier;
    private final String planeModelCompany;

    public FlightDto(Flight flight) {
        this.id = flight.getId();

        Airport origin = flight.getOrigin();
        this.originCity = origin.getCity();
        this.originAirportCode = origin.getAirportCode();
        this.originCountry = origin.getCountry();

        Airport destination = flight.getDestination();
        this.destinationCity = destination.getCity();
        this.destinationAirportCode = destination.getAirportCode();
        this.destinationCountry = destination.getCountry();

        this.departureTime = flight.getDepartureTime();
        this.arrivalTime = flight.getArrivalTime();

        this.flightIdentifier = flight.getIdentifier();
        this.flightCompany = flight.getCompany();

        Plane plane = flight.getPlane();
        this.planeIdentifier = plane.getIdentifier();

        PlaneModel planeModel = plane.getPlaneModel();
        this.planeModelIdentifier = planeModel.getIdentifier();
        this.planeModelCompany = planeModel.getCompany();
    }

    public Long getId() {
        return id;
    }

    public String getOriginCity() {
        return originCity;
    }

    public String getOriginAirportCode() {
        return originAirportCode;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public String getFlightIdentifier() {
        return flightIdentifier;
    }

    public String getFlightCompany() {
        return flightCompany;
    }

    public String getPlaneIdentifier() {
        return planeIdentifier;
    }

    public String getPlaneModelIdentifier() {
        return planeModelIdentifier;
    }

    public String getPlaneModelCompany() {
        return planeModelCompany;
    }
}
