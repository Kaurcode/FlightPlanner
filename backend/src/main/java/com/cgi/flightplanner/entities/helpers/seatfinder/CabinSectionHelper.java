package com.cgi.flightplanner.entities.helpers.seatfinder;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.planelayout.CabinSection;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;

import java.util.List;
import java.util.Map;

public class CabinSectionHelper {
    private final String identifier;
    private List<SeatRowHelper> rows;

    public CabinSectionHelper(CabinSection cabinSection, Map<SeatDefinition, BookableSeat> seatMappings) {
        this.identifier = cabinSection.getIdentifier();
        this.rows = cabinSection
                .getSeatRows()
                .stream()
                .map(row -> new SeatRowHelper(row, seatMappings))
                .toList();
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<SeatRowHelper> getRows() {
        return rows;
    }

    public void setRows(List<SeatRowHelper> rows) {
        this.rows = rows;
    }
}
