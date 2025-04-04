package com.cgi.flightplanner.dto.layout;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.planelayout.Block;
import com.cgi.flightplanner.entities.planelayout.BlockPosition;
import com.cgi.flightplanner.entities.planelayout.SeatBlock;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SeatBlockDto {
    private final Long blockId;
    private final String identifier;
    private final boolean isNextToWindow;
    private final BlockPosition blockPosition;
    private final List<SeatDto> seats;
    private final boolean isExit;

    public SeatBlockDto(Block block, Map<SeatDefinition, BookableSeat> seatMappings, Set<SeatDefinition> selectedSeats) {
        this.blockId = block.getId();
        this.blockPosition = block.getBlockPosition();

        if (!(block instanceof SeatBlock seatBlock)) {
            this.isExit = true;

            this.identifier = null;
            this.isNextToWindow = false;
            this.seats = null;

            return;
        }
        this.isExit = false;

        this.identifier = seatBlock.getIdentifier();
        this.isNextToWindow = seatBlock.isNextToWindow();

        this.seats = seatBlock
                .getSeats()
                .stream()
                .map(seat -> new SeatDto(seat, seatMappings.get(seat), selectedSeats))
                .toList();
    }

    public Long getBlockId() {
        return blockId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isNextToWindow() {
        return isNextToWindow;
    }

    public BlockPosition getBlockPosition() {
        return blockPosition;
    }

    public List<SeatDto> getSeats() {
        return seats;
    }

    public boolean isExit() {
        return isExit;
    }
}
