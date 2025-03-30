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

    public BookableSeat(boolean isBooked, Flight flight, SeatDefinition seat) {
        this.isBooked = isBooked;
        this.flight = flight;
        this.seat = seat;
    }

    public Long getId() {
        return id;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public SeatDefinition getSeat() {
        return seat;
    }
}
