package com.cgi.flightplanner.entities.planelayout;

import com.cgi.flightplanner.entities.Plane;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class PlaneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;
    private String company;

    @OneToMany(mappedBy = "planeModel")
    private List<Plane> planes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "SEAT_PLAN_ID", nullable = false)
    private SeatPlan seatPlan;

    protected PlaneModel() {}
}
