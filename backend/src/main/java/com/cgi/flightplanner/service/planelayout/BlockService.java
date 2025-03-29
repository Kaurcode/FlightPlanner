package com.cgi.flightplanner.service.planelayout;

import com.cgi.flightplanner.entities.planelayout.*;
import com.cgi.flightplanner.repository.planelayout.BlockRepository;
import com.cgi.flightplanner.service.CrudService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

@Service
public class BlockService extends CrudService<Block, BlockRepository> {
    private final SeatRowService seatRowService;

    public BlockService(BlockRepository repository, SeatRowService seatRowService) {
        super(repository);
        this.seatRowService = seatRowService;
    }

    private void resetBlockFlags(List<SeatRow> seatRows) {
        seatRows.stream()
                .map(SeatRow::getBlocks)
                .flatMap(List::stream)
                .filter(block -> block instanceof SeatBlock)
                .map(block -> (SeatBlock) block)
                .forEach(seatBlock -> seatBlock.setComputedHasMoreLegRoom(false));
    }

    private void computeSideLegRoom(
            List<SeatRow> seatRows,
            Function<SeatRow, Block> blockSelector,
            BlockPosition position
    ) {
        List<Block> blocks = seatRows.stream()
                .map(blockSelector)
                .toList();

        IntStream.range(1, seatRows.size())
                .filter(i -> !seatRows.get(i).isHasMoreLegRoom())
                .filter(i -> blocks.get(i) != null)
                .filter(i -> blocks.get(i) instanceof SeatBlock)
                .filter(i -> blocks.get(i).getBlockPosition() == position)
                .filter(i -> blocks.get(i - 1) == null ||
                        blocks.get(i - 1) instanceof Exit ||
                        blocks.get(i - 1).getBlockPosition() != position)
                .mapToObj(i -> (SeatBlock) blocks.get(i))
                .forEach(seatBlock -> seatBlock.setComputedHasMoreLegRoom(true));
    }

    @Transactional
    public void computeFields(List<SeatRow> seatRows) {
        if (seatRows.stream().allMatch(SeatRow::isBlocksComputed)) {
            return;
        }

        resetBlockFlags(seatRows);

        seatRows.stream()
                .filter(SeatRow::isHasMoreLegRoom)
                .map(SeatRow::getBlocks)
                .flatMap(List::stream)
                .filter(block -> block instanceof SeatBlock)
                .map(block -> (SeatBlock) block)
                .forEach(seatBlock -> seatBlock.setComputedHasMoreLegRoom(true));

        computeSideLegRoom(seatRows, row ->
                row.getBlocks().isEmpty() ?
                        null :
                        row.getBlocks().getFirst(), BlockPosition.LEFT);
        computeSideLegRoom(seatRows, row ->
                row.getBlocks().isEmpty() ?
                        null :
                        row.getBlocks().getLast(), BlockPosition.RIGHT);

        this.saveAll(seatRows.stream().map(SeatRow::getBlocks).flatMap(List::stream).toList());

        seatRows.forEach(row -> row.setBlocksComputed(true));
        seatRowService.saveAll(seatRows);
    }
}
