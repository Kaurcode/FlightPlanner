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

    protected SeatDefinition() {}

    public long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
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
