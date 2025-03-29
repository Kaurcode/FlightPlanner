package com.cgi.flightplanner.entities.planelayout;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EXIT")
public class Exit extends Block {
    protected Exit() {}
}
