package com.cgi.flightplanner.dto.layout;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.helpers.seatfinder.SeatHelper;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;

import java.util.Set;

public class SeatDto {
    private final Long seatDefinitionId;
    private final Long bookableSeatId;

    private final String identifier;
    private final SeatAvailability availability;

    public SeatDto(SeatDefinition seat, BookableSeat booking, Set<SeatDefinition> selectedSeats) {
        this.seatDefinitionId = seat.getId();
        this.bookableSeatId = booking.getId();

        this.identifier = seat.getIdentifier();

        boolean isSelected = selectedSeats.contains(seat);
        if (isSelected && booking.isBooked()) throw new IllegalStateException("Selection is already booked");

        if (isSelected) {
            this.availability = SeatAvailability.SELECTED;
        } else if (booking.isBooked()) {
            this.availability = SeatAvailability.BOOKED;
        } else {
            this.availability = SeatAvailability.AVAILABLE;
        }
    }

    public Long getSeatDefinitionId() {
        return seatDefinitionId;
    }

    public Long getBookableSeatId() {
        return bookableSeatId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public SeatAvailability getAvailability() {
        return availability;
    }
}
