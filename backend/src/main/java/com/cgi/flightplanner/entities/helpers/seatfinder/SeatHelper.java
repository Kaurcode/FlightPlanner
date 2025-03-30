package com.cgi.flightplanner.entities.helpers.seatfinder;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;

public class SeatHelper {
    private final SeatDefinition seatDefinition;
    private final BookableSeat bookableSeat;

    private boolean isAvailable;

    public SeatHelper(SeatDefinition seatDefinition, BookableSeat bookableSeat) {
        this.seatDefinition = seatDefinition;
        this.bookableSeat = bookableSeat;

        this.isAvailable = !bookableSeat.isBooked();
    }

    public SeatDefinition getSeatDefinition() {
        return seatDefinition;
    }

    public BookableSeat getBookableSeat() {
        return bookableSeat;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
