package com.cgi.flightplanner.entities.planelayout;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SeatBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private boolean isNextToWindow;

    @Enumerated(EnumType.STRING)
    private BlockPosition blockPosition;

    @ManyToOne(optional = false)
    @JoinColumn(name = "SEAT_ROW_ID", nullable = false)
    private SeatRow seatRow;

    @OneToMany(mappedBy = "seatBlock")
    private List<SeatDefinition> seats;

    protected SeatBlock() {}

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isNextToWindow() {
        return isNextToWindow;
    }

    public BlockPosition getBlockPosition() {
        return blockPosition;
    }

    public List<SeatDefinition> getSeats() {
        return seats;
    }
}
