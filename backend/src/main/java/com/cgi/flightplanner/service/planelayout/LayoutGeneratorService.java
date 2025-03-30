package com.cgi.flightplanner.service.planelayout;

import com.cgi.flightplanner.entities.planelayout.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class LayoutGeneratorService {
    public final SeatRowService seatRowService;
    public final SeatBlockService seatBlockService;
    public final SeatDefinitionService seatDefinitionService;

    public LayoutGeneratorService(SeatRowService seatRowService, SeatBlockService seatBlockService, SeatDefinitionService seatDefinitionService) {
        this.seatRowService = seatRowService;
        this.seatBlockService = seatBlockService;
        this.seatDefinitionService = seatDefinitionService;
    }

    public List<SeatRow> generateRowLayout(
            CabinSection section,
            int startingRowIndex,
            int nrOfRows,
            boolean isExtraLegRoom,
            int windowFrequency,
            List<Integer> blockLayout
    ) {
        List<SeatRow> rows = new ArrayList<>();

        for (int i = 0; i < nrOfRows; i++) {
            rows.add(new SeatRow(String.valueOf(i + startingRowIndex), isExtraLegRoom, section));
        }

        for (int i = 0; i < nrOfRows; i++) {
            SeatRow row = rows.get(i);
            List<SeatBlock> blocks = generateBlockLayout(row, (i + 1) % windowFrequency == 0, blockLayout);
            row.setBlocks(new ArrayList<>(blocks));
        }

        return seatRowService.saveAll(rows);
    }

    public List<SeatBlock> generateBlockLayout(SeatRow row, boolean isNextToWindow, List<Integer> blockLayout) {
        List<SeatBlock> blocks = new ArrayList<>();
        blocks.add(new SeatBlock(BlockPosition.LEFT, row, isNextToWindow));

        for (int i = 1; i < blockLayout.size() - 1; i++) {
            blocks.add(new SeatBlock(BlockPosition.MIDDLE, row, isNextToWindow));
        }

        if (1 < blockLayout.size()) {
            blocks.add(new SeatBlock(BlockPosition.RIGHT, row, isNextToWindow));
        }

        for (int i = 0; i < blockLayout.size(); i++) {
            SeatBlock block = blocks.get(i);
            block.setSeats(generateSeatLayout(blockLayout.get(i), block));
        }

        return seatBlockService.saveAll(blocks);
    }

    public List<SeatDefinition> generateSeatLayout(int nrOfSeats, SeatBlock seatBlock) {
        List<SeatDefinition> seats = IntStream
                .range(0, nrOfSeats)
                .mapToObj(i -> new SeatDefinition(String.valueOf((char) ('A' + i)), seatBlock))
                .toList();

        return seatDefinitionService.saveAll(seats);
    }
}
