package com.cgi.flightplanner.entities.planelayout;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "BLOCK_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "SEAT_ROW_ID", nullable = false)
    private SeatRow seatRow;

    @Enumerated(EnumType.STRING)
    private BlockPosition blockPosition;

    protected Block() {}

    public Block(BlockPosition blockPosition, SeatRow seatRow) {
        this.blockPosition = blockPosition;
        this.seatRow = seatRow;
    }

    public Long getId() {
        return id;
    }

    public BlockPosition getBlockPosition() {
        return blockPosition;
    }
}
