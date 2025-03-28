package com.cgi.flightplanner.entities;

import com.cgi.flightplanner.entities.planelayout.SeatDefinition;
import jakarta.persistence.*;

@Entity
public class BookableSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isBooked;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FLIGHT_ID", nullable = false)
    private Flight flight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "SEAT_ID", nullable = false)
    private SeatDefinition seat;

    protected BookableSeat() {}
}
