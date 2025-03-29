package com.cgi.flightplanner.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ORIGIN_AIRPORT_ID", nullable = false)
    private Airport origin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DESTINATION_AIRPORT_ID", nullable = false)
    private Airport destination;

    private Instant departureTime;
    private Instant arrivalTime;

    private String identifier;
    private String company;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PLANE_ID", nullable = false)
    private Plane plane;

    @OneToMany(mappedBy = "flight")
    private List<BookableSeat> seats;

    protected Flight() {}

    public Flight(
            Airport origin,
            Airport destination,
            Instant departureTime,
            Instant arrivalTime,
            String identifier,
            String company,
            Plane plane
    ) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.identifier = identifier;
        this.company = company;
        this.plane = plane;
    }

    public long getId() {
        return id;
    }

    public Airport getOrigin() {
        return origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getCompany() {
        return company;
    }

    public Plane getPlane() {
        return plane;
    }

    public List<BookableSeat> getSeats() {
        return seats;
    }
}
