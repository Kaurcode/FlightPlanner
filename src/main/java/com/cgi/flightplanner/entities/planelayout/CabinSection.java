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

    protected CabinSection() {}
}
