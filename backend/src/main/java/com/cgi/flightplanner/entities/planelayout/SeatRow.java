package com.cgi.flightplanner.entities.planelayout;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SeatRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private boolean hasMoreLegRoom;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CABIN_SECTION_ID", nullable = false)
    private CabinSection cabinSection;

    @OneToMany(mappedBy = "seatRow")
    private List<Block> blocks;

    protected SeatRow() {}

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isHasMoreLegRoom() {
        return hasMoreLegRoom;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
