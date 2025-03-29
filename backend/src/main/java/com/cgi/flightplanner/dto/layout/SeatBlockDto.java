package com.cgi.flightplanner.dto.layout;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.planelayout.BlockPosition;
import com.cgi.flightplanner.entities.planelayout.SeatBlock;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;

import java.util.List;
import java.util.Map;

public class SeatBlockDto {
    private final Long seatBlockId;
    private final String identifier;
    private final boolean isNextToWindow;
    private final BlockPosition blockPosition;
    private final List<SeatDto> seats;

    public SeatBlockDto(SeatBlock seatBlock, Map<SeatDefinition, BookableSeat> seatMappings) {
        this.seatBlockId = seatBlock.getId();
        this.identifier = seatBlock.getIdentifier();
        this.isNextToWindow = seatBlock.isNextToWindow();
        this.blockPosition = seatBlock.getBlockPosition();

        this.seats = seatBlock
                .getSeats()
                .stream()
                .map(seat -> new SeatDto(seat, seatMappings.get(seat)))
                .toList();
    }
}
