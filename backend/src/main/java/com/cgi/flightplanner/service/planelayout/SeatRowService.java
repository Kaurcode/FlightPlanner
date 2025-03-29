package com.cgi.flightplanner.service.planelayout;

import com.cgi.flightplanner.entities.planelayout.CabinSection;
import com.cgi.flightplanner.entities.planelayout.Exit;
import com.cgi.flightplanner.entities.planelayout.SeatRow;
import com.cgi.flightplanner.repository.planelayout.SeatRowRepository;
import com.cgi.flightplanner.service.CrudService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

@Service
public class SeatRowService extends CrudService<SeatRow, SeatRowRepository> {
    private final CabinSectionService cabinSectionService;

    public SeatRowService(SeatRowRepository repository, CabinSectionService cabinSectionService) {
        super(repository);
        this.cabinSectionService = cabinSectionService;
    }

    private static void updateRows(
            List<SeatRow> rows,
            int startIndex,
            int endIndex,
            IntUnaryOperator distanceFunction
    ) {
        IntStream.range(startIndex, endIndex)
                .forEachOrdered(i -> {
                    rows.get(i).setDistanceFromExit(distanceFunction.applyAsInt(i));
                });
    }

    @Transactional
    public void computeFields(List<CabinSection> sections) {
        if (sections.stream().allMatch(CabinSection::getRowsComputed)) {
            return;
        }

        List<SeatRow> rows = sections.stream()
                .map(CabinSection::getSeatRows)
                .flatMap(List::stream)
                .toList();

        IntStream.range(1, rows.size())
                .filter(i -> rows.get(i - 1).getBlocks().isEmpty())
                .forEachOrdered(i -> rows.get(i).setHasMoreLegRoom(true));

        List<Integer> exitRowIndexes = IntStream.range(0, rows.size())
                .filter(i -> rows.get(i)
                        .getBlocks()
                        .stream()
                        .anyMatch(block -> block instanceof Exit))
                .boxed()
                .toList();

        if (exitRowIndexes.isEmpty()) {
            return;
        }

        int firstExitIndex = exitRowIndexes.getFirst();
        updateRows(rows, 0, firstExitIndex, i -> firstExitIndex - i);

        IntStream.range(0, exitRowIndexes.size() - 1).forEachOrdered(i -> {
            int startIndex = exitRowIndexes.get(i);
            int midPointIndex = (startIndex + exitRowIndexes.get(i + 1)) / 2;
            int endIndex = exitRowIndexes.get(i + 1);

            updateRows(rows, startIndex, midPointIndex + 1, j -> j - startIndex);
            updateRows(rows, midPointIndex, endIndex, j -> endIndex - j);
        });

        int lastExitIndex = exitRowIndexes.getLast();
        updateRows(rows, lastExitIndex, rows.size(), i -> i - lastExitIndex);

        this.saveAll(rows);

        sections.forEach(section -> section.setRowsComputed(true));
        cabinSectionService.saveAll(sections);
    }
}
