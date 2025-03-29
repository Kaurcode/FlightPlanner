package com.cgi.flightplanner.entities.planelayout;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SeatBlock extends Block {
    private String identifier;

    private boolean isNextToWindow;

    @OneToMany(mappedBy = "seatBlock")
    private List<SeatDefinition> seats;

    protected SeatBlock() {}

    public String getIdentifier() {
        return identifier;
    }

    public boolean isNextToWindow() {
        return isNextToWindow;
    }

    public List<SeatDefinition> getSeats() {
        return seats;
    }
}
