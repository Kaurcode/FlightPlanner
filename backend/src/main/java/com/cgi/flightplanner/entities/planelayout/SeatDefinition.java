package com.cgi.flightplanner.entities.planelayout;

import com.cgi.flightplanner.entities.BookableSeat;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class SeatDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String identifier;

    @ManyToOne(optional = false)
    @JoinColumn(name = "SEAT_BLOCK_ID", nullable = false)
    private SeatBlock seatBlock;

    @OneToMany(mappedBy = "seat")
    private List<BookableSeat> bookableSeats;

    public SeatDefinition(String identifier, SeatBlock seatBlock) {
        this.identifier = identifier;
        this.seatBlock = seatBlock;
    }

    // --- Computed fields ---

    private Boolean computedHasMoreLegRoom = false;
    private Boolean computedIsNextToWindow = false;

    // -----------------------

    protected SeatDefinition() {}

    public long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isComputedHasMoreLegRoom() {
        return computedHasMoreLegRoom;
    }

    public void setComputedHasMoreLegRoom(boolean computedHasMoreLegRoom) {
        this.computedHasMoreLegRoom = computedHasMoreLegRoom;
    }

    public boolean isComputedIsNextToWindow() {
        return computedIsNextToWindow;
    }

    public void setComputedIsNextToWindow(boolean computedIsNextToWindow) {
        this.computedIsNextToWindow = computedIsNextToWindow;
    }

    // TODO: Better errors

    @Override
    public boolean equals(Object obj) {
        if (this.getId() == 0) {
            throw new IllegalStateException("SeatDefinition wasn't persisted before equality check");
        }
        if (this == obj) return true;
        if (!(obj instanceof SeatDefinition otherSeat)) { return false; }
        if (otherSeat.getId() == 0) {
            throw new IllegalStateException("SeatDefinition wasn't persisted before equality check");
        }

        return Objects.equals(this.getId(), otherSeat.getId());
    }

    @Override
    public int hashCode() {
        if (getId() == 0) {
            throw new IllegalStateException("SeatDefinition wasn't persisted before hashing");
        }
        return Long.hashCode(getId());
    }
}
