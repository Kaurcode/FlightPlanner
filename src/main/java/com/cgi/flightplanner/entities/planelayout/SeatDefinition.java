package com.cgi.flightplanner.entities.planelayout;

import com.cgi.flightplanner.entities.BookableSeat;
import jakarta.persistence.*;

import java.util.List;

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
}
