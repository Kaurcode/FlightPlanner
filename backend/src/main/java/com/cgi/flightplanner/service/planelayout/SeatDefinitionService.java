package com.cgi.flightplanner.service.planelayout;

import com.cgi.flightplanner.entities.planelayout.Block;
import com.cgi.flightplanner.entities.planelayout.BlockPosition;
import com.cgi.flightplanner.entities.planelayout.SeatBlock;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;
import com.cgi.flightplanner.repository.planelayout.SeatDefinitionRepository;
import com.cgi.flightplanner.service.CrudService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatDefinitionService extends CrudService<SeatDefinition, SeatDefinitionRepository> {
    private final BlockService blockService;

    public SeatDefinitionService(SeatDefinitionRepository repository, BlockService blockService) {
        super(repository);
        this.blockService = blockService;
    }

    private static void resetSeatFlags(List<SeatBlock> seatBlockList) {
        seatBlockList
                .stream()
                .map(SeatBlock::getSeats)
                .flatMap(List::stream)
                .forEach(seat -> {
                    seat.setComputedHasMoreLegRoom(false);
                    seat.setComputedIsNextToWindow(false);
                });
    }

    private static void markSeatsWithMoreLegRoom(List<SeatBlock> seatBlocks) {
        seatBlocks
                .stream()
                .filter(SeatBlock::isComputedHasMoreLegRoom)
                .map(SeatBlock::getSeats)
                .flatMap(List::stream)
                .forEach(seat -> seat.setComputedHasMoreLegRoom(true));
    }

    private static void markWindowSeats(List<SeatBlock> seatBlocks) {
        List<SeatBlock> windowSeatBlockList = seatBlocks.stream().filter(SeatBlock::isNextToWindow).toList();

        windowSeatBlockList
                .stream()
                .filter(block -> block.getBlockPosition() == BlockPosition.LEFT)
                .map(SeatBlock::getSeats)
                .forEach(seatList -> seatList.getFirst().setComputedIsNextToWindow(true));

        windowSeatBlockList
                .stream()
                .filter(block -> block.getBlockPosition() == BlockPosition.RIGHT)
                .map(SeatBlock::getSeats)
                .forEach(seatList -> seatList.getLast().setComputedIsNextToWindow(true));
    }

    @Transactional
    public void computeFields(List<Block> blocks) {
        List<SeatBlock> seatBlockList = blocks.stream()
                .filter(block -> block instanceof SeatBlock)
                .map(block -> (SeatBlock) block)
                .filter(block -> block.getSeats() != null)
                .toList();

        if (seatBlockList.stream().allMatch(SeatBlock::isSeatsComputed)) {
            return;
        }

        resetSeatFlags(seatBlockList);
        markSeatsWithMoreLegRoom(seatBlockList);
        markWindowSeats(seatBlockList);

        this.saveAll(seatBlockList.stream().map(SeatBlock::getSeats).flatMap(List::stream).toList());

        seatBlockList.forEach(block -> block.setSeatsComputed(true));
        blockService.saveAll(blocks);
    }
}
