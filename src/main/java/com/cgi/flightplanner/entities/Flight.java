package com.cgi.flightplanner.entities;

import jakarta.persistence.*;

import java.time.Instant;

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

    // private Plane plane;
}
