package com.cgi.flightplanner.dto.layout;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;

public class SeatDto {
    private final Long seatDefinitionId;
    private final Long bookableSeatId;

    private final String identifier;

    public SeatDto(SeatDefinition seat, BookableSeat booking) {
        this.seatDefinitionId = seat.getId();
        this.bookableSeatId = booking.getId();

        this.identifier = seat.getIdentifier();
    }
}
