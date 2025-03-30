package com.cgi.flightplanner.entities.helpers.seatfinder;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.planelayout.BlockPosition;
import com.cgi.flightplanner.entities.planelayout.SeatBlock;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;

import java.util.List;
import java.util.Map;

public class SeatBlockHelper {
    private final BlockPosition blockPosition;
    private final boolean isNextToWindow;
    private final boolean hasMoreLegRoom;
    private List<SeatHelper> seats;

    public SeatBlockHelper(SeatBlock seatBlock, Map<SeatDefinition, BookableSeat> seatMappings) {
        this.blockPosition = seatBlock.getBlockPosition();
        this.isNextToWindow = seatBlock.isNextToWindow();
        this.hasMoreLegRoom = seatBlock.isComputedHasMoreLegRoom();
        this.seats = seatBlock
                .getSeats()
                .stream()
                .map(seat -> new SeatHelper(seat, seatMappings.get(seat)))
                .toList();
    }

    public BlockPosition getBlockPosition() {
        return blockPosition;
    }

    public boolean isNextToWindow() {
        return isNextToWindow;
    }

    public boolean isHasMoreLegRoom() {
        return hasMoreLegRoom;
    }

    public List<SeatHelper> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatHelper> seats) {
        this.seats = seats;
    }
}
