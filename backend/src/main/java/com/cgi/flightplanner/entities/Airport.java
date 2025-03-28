package com.cgi.flightplanner.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String city;
    private String airportCode;
    private String country;

    @OneToMany(mappedBy = "origin")
    private List<Flight> departures;

    @OneToMany(mappedBy = "destination")
    private List<Flight> arrivals;

    protected Airport() {}

    public Airport(String city, String airportCode, String country) {
        this.city = city;
        this.airportCode = airportCode;
        this.country = country;
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
