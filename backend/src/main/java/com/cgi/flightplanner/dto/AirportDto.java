package com.cgi.flightplanner.dto;

import com.cgi.flightplanner.entities.Airport;

public class AirportDto {
    private final long id;

    private final String city;
    private final String airportCode;
    private final String country;

    public AirportDto(Airport airport) {
        this.id = airport.getId();
        this.city = airport.getCity();
        this.airportCode = airport.getAirportCode();
        this.country = airport.getCountry();
    }

    public long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public String getCountry() {
        return country;
    }
}
