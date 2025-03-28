package com.cgi.flightplanner.entities.planelayout;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SeatPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    @OneToMany(mappedBy = "seatPlan")
    private List<PlaneModel> planeModels;

    @OneToMany(mappedBy = "seatPlan")
    private List<CabinSection> cabinSections;

    protected SeatPlan() {}
}
