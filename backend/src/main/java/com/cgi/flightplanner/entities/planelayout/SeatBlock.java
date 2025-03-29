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

    private boolean isComputed;
    private boolean computedHasMoreLegRoom;

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

    public boolean isComputed() {
        return isComputed;
    }

    public void setComputed(boolean computed) {
        isComputed = computed;
    }

    public boolean isComputedHasMoreLegRoom() {
        return computedHasMoreLegRoom;
    }

    public void setComputedHasMoreLegRoom(boolean computedHasMoreLegRoom) {
        this.computedHasMoreLegRoom = computedHasMoreLegRoom;
    }
}
