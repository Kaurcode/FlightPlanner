package com.cgi.flightplanner.entities.helpers.seatfinder;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.planelayout.SeatBlock;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;
import com.cgi.flightplanner.entities.planelayout.SeatRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeatRowHelper {
    private final long distanceFromExit;
    private final List<SeatBlockHelper> blocks;

    public SeatRowHelper(SeatRow seatRow, Map<SeatDefinition, BookableSeat> seatMappings) {
        this.distanceFromExit = seatRow.getDistanceFromExit();
        this.blocks = seatRow
                .getBlocks()
                .stream()
                .filter(block -> block instanceof SeatBlock)
                .map(block -> (SeatBlock) block)
                .map(block -> new SeatBlockHelper(block, seatMappings))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public long getDistanceFromExit() {
        return distanceFromExit;
    }

    public List<SeatBlockHelper> getBlocks() {
        return blocks;
    }
}
