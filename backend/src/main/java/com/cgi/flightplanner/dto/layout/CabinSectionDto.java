package com.cgi.flightplanner.dto.layout;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.planelayout.CabinSection;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CabinSectionDto {
    private final Long cabinSectionId;
    private final String identifier;

    private final List<SeatRowDto> seatRows;

    public CabinSectionDto(
            CabinSection cabinSection,
            Map<SeatDefinition, BookableSeat> seats,
            Set<SeatDefinition> selectedSeats
    ) {
        this.cabinSectionId = cabinSection.getId();
        this.identifier = cabinSection.getIdentifier();
        this.seatRows = cabinSection
                .getSeatRows()
                .stream()
                .map(row -> new SeatRowDto(row, seats, selectedSeats))
                .toList();
    }

    public Long getCabinSectionId() {
        return cabinSectionId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<SeatRowDto> getSeatRows() {
        return seatRows;
    }
}
