package com.cgi.flightplanner.entities;

import com.cgi.flightplanner.entities.planelayout.PlaneModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    @OneToMany(mappedBy = "plane")
    private List<Flight> flights;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PLANE_MODEL_ID", nullable = false)
    private PlaneModel planeModel;

    protected Plane() {}

    public String getIdentifier() {
        return identifier;
    }

    public PlaneModel getPlaneModel() {
        return planeModel;
    }
}
