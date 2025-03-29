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

    // --- Computed fields ---

    private Boolean isBlocksComputed = false;
    private long distanceFromExit;

    // -----------------------

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

    public void setHasMoreLegRoom(boolean hasMoreLegRoom) {
        this.hasMoreLegRoom = hasMoreLegRoom;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public boolean isBlocksComputed() {
        return isBlocksComputed;
    }

    public void setBlocksComputed(boolean blocksComputed) {
        isBlocksComputed = blocksComputed;
    }

    public long getDistanceFromExit() {
        return distanceFromExit;
    }

    public void setDistanceFromExit(long lengthFromExit) {
        this.distanceFromExit = lengthFromExit;
    }

}
