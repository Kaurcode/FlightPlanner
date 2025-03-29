package com.cgi.flightplanner.dto.layout;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;
import com.cgi.flightplanner.entities.planelayout.SeatRow;

import java.util.List;
import java.util.Map;

public class SeatRowDto {
    private final Long seatRowId;
    private final String identifier;
    private final boolean hasMoreLegRoom;
    private final List<SeatBlockDto> seatBlocks;

    public SeatRowDto(SeatRow seatRow, Map<SeatDefinition, BookableSeat> seats) {
        this.seatRowId = seatRow.getId();
        this.identifier = seatRow.getIdentifier();
        this.hasMoreLegRoom = seatRow.isHasMoreLegRoom();
        this.seatBlocks = seatRow
                .getSeatBlocks()
                .stream()
                .map(block -> new SeatBlockDto(block, seats))
                .toList();
    }
}
