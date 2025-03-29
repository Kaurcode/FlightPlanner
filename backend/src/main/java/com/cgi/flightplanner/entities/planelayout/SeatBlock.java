package com.cgi.flightplanner.entities.planelayout;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("SEAT_BLOCK")
public class SeatBlock extends Block {
    private String identifier;

    private boolean isNextToWindow;

    @OneToMany(mappedBy = "seatBlock")
    private List<SeatDefinition> seats;

    // --- Computed fields ---

    private Boolean isSeatsComputed = false;
    private Boolean computedHasMoreLegRoom = false;

    // -----------------------

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

    public boolean isSeatsComputed() {
        return isSeatsComputed;
    }

    public void setSeatsComputed(boolean seatsComputed) {
        isSeatsComputed = seatsComputed;
    }

    public boolean isComputedHasMoreLegRoom() {
        return computedHasMoreLegRoom;
    }

    public void setComputedHasMoreLegRoom(boolean computedHasMoreLegRoom) {
        this.computedHasMoreLegRoom = computedHasMoreLegRoom;
    }
}
