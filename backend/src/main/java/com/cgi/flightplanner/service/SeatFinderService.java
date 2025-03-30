package com.cgi.flightplanner.service;

import com.cgi.flightplanner.entities.helpers.seatfinder.*;
import com.cgi.flightplanner.entities.planelayout.BlockPosition;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class SeatFinderService {
    private static void filterByCabinSection(FlightSeatPlanHelper seatPlan, Set<String> sectionIdentifiers) {
        seatPlan.setCabinSections(
                seatPlan.getCabinSections()
                        .stream()
                        .filter(section -> sectionIdentifiers.contains(section.getIdentifier()))
                        .toList()
        );
    }

    private static void filterByLegRoom(FlightSeatPlanHelper seatPlan, boolean needsMoreLegRoom) {
        seatPlan.getCabinSections()
                .forEach(section -> filterByLegRoom(section, needsMoreLegRoom));

        seatPlan.setCabinSections(
                seatPlan.getCabinSections()
                        .stream()
                        .filter(section -> !section.getRows().isEmpty())
                        .toList()
        );
    }

    private static void filterByLegRoom(CabinSectionHelper cabinSection, boolean needsMoreLegRoom) {
        cabinSection.setRows(
                cabinSection.getRows()
                        .stream()
                        .filter(row -> row.isHasMoreLegRoom() == needsMoreLegRoom)
                        .toList()
        );

        cabinSection.getRows()
                .forEach(row -> filterByLegRoom(row, needsMoreLegRoom));

        cabinSection.setRows(
                cabinSection.getRows()
                        .stream()
                        .filter(row -> !row.getBlocks().isEmpty())
                        .toList()
        );
    }

    private static void filterByLegRoom(SeatRowHelper row, boolean needsMoreLegRoom) {
        row.setBlocks(
                row.getBlocks()
                        .stream()
                        .filter(block -> block.isHasMoreLegRoom() == needsMoreLegRoom)
                        .toList()
        );
    }

    private static void filterByWindow(FlightSeatPlanHelper seatPlan) {
        seatPlan.getCabinSections()
                .forEach(SeatFinderService::filterByWindow);

        seatPlan.setCabinSections(
                seatPlan.getCabinSections()
                        .stream()
                        .filter(section -> !section.getRows().isEmpty())
                        .toList()
        );
    }

    private static void filterByWindow(CabinSectionHelper cabinSection) {
        cabinSection.getRows()
                .forEach(SeatFinderService::filterByWindow);

        cabinSection.setRows(
                cabinSection.getRows()
                        .stream()
                        .filter(row -> !row.getBlocks().isEmpty())
                        .toList()
        );
    }

    private static void filterByWindow(SeatRowHelper row) {
        row.setBlocks(
                row.getBlocks()
                        .stream()
                        .filter(SeatBlockHelper::isNextToWindow)
                        .toList()
        );
    }

    private static void sortByClosenessToExit(FlightSeatPlanHelper seatPlan, boolean needsCloseToExit) {
        seatPlan.getCabinSections()
                .forEach(section -> sortByClosenessToExit(section, needsCloseToExit));
    }

    private static void sortByClosenessToExit(CabinSectionHelper cabinSection, boolean needsCloseToExit) {
        Comparator<SeatRowHelper> comparator = Comparator.comparing(SeatRowHelper::getDistanceFromExit);

        if (!needsCloseToExit) {
            comparator = comparator.reversed();
        }

        cabinSection.setRows(
                cabinSection.getRows()
                        .stream()
                        .sorted(comparator)
                        .toList()
        );
    }

    private static void filterByConsecutiveSeats(
            FlightSeatPlanHelper seatPlan,
            int numberOfSeats,
            boolean isCloseToWindow
    ) {
        if (isCloseToWindow) {
            filterByWindow(seatPlan);
        }

        seatPlan.getCabinSections()
                .forEach(section -> filterByConsecutiveSeats(section, numberOfSeats, isCloseToWindow));
    }

    private static void filterByConsecutiveSeats(
            CabinSectionHelper cabinSection,
            int numberOfSeats,
            boolean isCloseToWindow
    ) {
        cabinSection.getRows()
                .forEach(row -> filterByConsecutiveSeats(row, numberOfSeats, isCloseToWindow));
    }

    private static void filterByConsecutiveSeats(
            SeatRowHelper row,
            int numberOfSeats,
            boolean isCloseToWindow
    ) {
        row.setBlocks(
                row.getBlocks()
                        .stream()
                        .filter(block -> isEnoughConsecutiveSeats(
                                block,
                                numberOfSeats,
                                isCloseToWindow
                        ))
                        .toList()
        );
    }

    private static boolean isEnoughConsecutiveSeats(
            SeatBlockHelper seatBlock,
            int nrOfNeededSeats,
            boolean isCloseToWindow
    ) {
        // Isn't probably needed because the blocks should already be filtered
        if (isCloseToWindow && !seatBlock.isNextToWindow()) {
            return false;
        }
        if (seatBlock.getSeats().size() < nrOfNeededSeats) {
            return false;
        }
        if (seatBlock.isNextToWindow()) {
            return isEnoughConsecutiveSeatsInWindowBlock(
                    seatBlock,
                    nrOfNeededSeats,
                    isCloseToWindow
            );
        }
        return isEnoughConsecutiveSeats(seatBlock.getSeats(), nrOfNeededSeats);
    }

    private static boolean isEnoughConsecutiveSeatsInWindowBlock(
            SeatBlockHelper seatBlock,
            int numberOfNeededSeats,
            boolean isCloseToWindow
    ) {
        int seatCount = seatBlock.getSeats().size();
        if (!isCloseToWindow && seatCount - 1 < numberOfNeededSeats) {
            return false;
        }

        if (!isCloseToWindow) {
            List<SeatHelper> seats;

            if (seatBlock.getBlockPosition() == BlockPosition.LEFT) {
                seats = seatBlock.getSeats().subList(1, seatCount);
            } else {
                seats = seatBlock.getSeats().subList(0, seatCount - 1);
            }

            return isEnoughConsecutiveSeats(seats, numberOfNeededSeats);
        }

        int nrOfConsecutiveSeats = 0;

        List<SeatHelper> seats = seatBlock.getSeats();

        if (seatBlock.getBlockPosition() == BlockPosition.RIGHT) {
            seats = seats.reversed();
        }

        for (SeatHelper seat : seats) {
            if (!seat.isAvailable()) {
                return numberOfNeededSeats <= nrOfConsecutiveSeats;
            }
            nrOfConsecutiveSeats++;
        }

        return true;
    }

    private static boolean isEnoughConsecutiveSeats(List<SeatHelper> seats, int nrOfNeededSeats) {
        int highestNrOfConsecutiveSeats = 0;
        int currentNrOfConsecutiveSeats = 0;

        for (SeatHelper seat : seats) {
            if (seat.isAvailable()) {
                currentNrOfConsecutiveSeats++;
            } else {
                highestNrOfConsecutiveSeats = Math.max(highestNrOfConsecutiveSeats, currentNrOfConsecutiveSeats);
                currentNrOfConsecutiveSeats = 0;
            }
        }

        return nrOfNeededSeats <= highestNrOfConsecutiveSeats;
    }
}
