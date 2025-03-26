package com.cgi.flightplanner.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    private Airport origin;
    @ManyToOne(optional = false)
    private Airport destination;

    private Instant departureTime;
    private Instant arrivalTime;

    // private Plane plane;
}
