package com.cgi.flightplanner.entities.planelayout;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CabinSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    @ManyToOne(optional = false)
    @JoinColumn(name = "SEAT_PLAN_ID", nullable = false)
    private SeatPlan seatPlan;

    @OneToMany(mappedBy = "cabinSection")
    private List<SeatRow> seatRows;

    // --- Computed fields ---

    private Boolean isRowsComputed = false;

    // -----------------------

    protected CabinSection() {}

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<SeatRow> getSeatRows() {
        return seatRows;
    }

    public Boolean getRowsComputed() {
        return isRowsComputed;
    }

    public void setRowsComputed(Boolean rowsComputed) {
        isRowsComputed = rowsComputed;
    }
}
