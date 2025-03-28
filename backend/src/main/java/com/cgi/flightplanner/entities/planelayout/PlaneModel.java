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

    // TODO: Optionality is temporary, change later
    @ManyToOne(optional = true)
    @JoinColumn(name = "SEAT_PLAN_ID", nullable = true)
    private SeatPlan seatPlan;

    protected PlaneModel() {}

    public PlaneModel(String identifier, String company) {
        this.identifier = identifier;
        this.company = company;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getCompany() {
        return company;
    }
}
