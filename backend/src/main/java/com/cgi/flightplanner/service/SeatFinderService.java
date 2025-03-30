package com.cgi.flightplanner.service;

import com.cgi.flightplanner.entities.Airport;
import com.cgi.flightplanner.entities.Flight;
import com.cgi.flightplanner.entities.helpers.seatfinder.*;
import com.cgi.flightplanner.entities.planelayout.BlockPosition;
import com.cgi.flightplanner.entities.planelayout.SeatDefinition;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class SeatFinderService {
    public Set<SeatDefinition> findSeats(
            Flight flight,
            int nrOfSeats,
            boolean areSeatsClose,
            Set<String> cabinSections,
            boolean needsMoreLegRoom,
            boolean needsCloseToExit,
            boolean isCloseToWindow
    ) {
        throw new UnsupportedOperationException();

        // TODO
//        FlightSeatPlanHelper seatPlan = new FlightSeatPlanHelper(flight);
//        filterByCabinSection(seatPlan, cabinSections);
//        filterByLegRoom(seatPlan, needsMoreLegRoom);
//        sortByClosenessToExit(seatPlan, needsCloseToExit);
//
//        if (!areSeatsClose || nrOfSeats == 1) {
//            filterByConsecutiveSeats(seatPlan, 1, isCloseToWindow);
//
//            List<SeatBlockHelper> blocks = seatPlan.getCabinSections()
//                    .stream()
//                    .map(CabinSectionHelper::getRows)
//                    .flatMap(List::stream)
//                    .map(SeatRowHelper::getBlocks)
//                    .flatMap(List::stream)
//                    .toList();
//
//            return IntStream.range(0, nrOfSeats)
//                    .mapToObj(i -> getSeatsFromBlock(blocks.get(i), nrOfSeats, isCloseToWindow))
//                    .flatMap(List::stream)
//                    .toList();
//        }
//
//        // TODO: Currently greedy, should change it to create even groups
//        TreeMap<Integer, Integer> availableSeats;
//        if (isCloseToWindow) {
//            availableSeats = getNumbersOfConsecutiveSeatsNearWindow(seatPlan);
//            Map.Entry<Integer,Integer> highestNrOfSeatsGrouped = availableSeats.ceilingEntry(nrOfSeats);
//
//            if (highestNrOfSeatsGrouped != null) {
//                filterByConsecutiveSeats(seatPlan, highestNrOfSeatsGrouped.getKey(), true);
//
//                List<SeatBlockHelper> blocks = seatPlan.getCabinSections()
//                        .stream()
//                        .map(CabinSectionHelper::getRows)
//                        .flatMap(List::stream)
//                        .map(SeatRowHelper::getBlocks)
//                        .flatMap(List::stream)
//                        .toList();
//
//                return getSeatsFromBlock(blocks.getFirst(), highestNrOfSeatsGrouped.getKey(), true);
//            }
//
//            List<SeatHelper> selectedSeats;
//
//            int temporaryNrOfSeats = nrOfSeats;
//            int lowestNrOfSeats = 0;
//            int iterations = 0;
//
//            for (Map.Entry<Integer,Integer> entry : availableSeats.descendingMap().entrySet()) {
//                temporaryNrOfSeats -= entry.getKey() * entry.getValue();
//                if (temporaryNrOfSeats <= 0) {
//                    lowestNrOfSeats = entry.getKey();
//                    iterations += entry.getValue() - (lowestNrOfSeats / entry.getKey());
//                }
//                iterations += entry.getValue();
//            }
//
//            if (lowestNrOfSeats == 0) {
//                throw new NoSuchElementException("Not enough seats found");
//            }
//
//            for (int i = 0; i < iterations; i++) {
//
//            }
//        }
    }

    private List<SeatHelper> filterByConsecutiveSeatGroups(
            FlightSeatPlanHelper seatPlan,
            TreeMap<Integer, Integer> availableSeatGroups,
            int nrOfSeats,
            boolean isCloseToWindow
    ) {
        throw new UnsupportedOperationException();
        // Map.Entry<Integer, Integer> currentGroup = availableSeatGroups.floorEntry(nrOfSeats);
        // if (currentGroup == null) throw new NoSuchElementException("Not enough seats found");

        // if (nrOfSeats <= currentGroup.getKey() * currentGroup.getValue()) {
            // filterByConsecutiveSeats(seatPlan, currentGroup.getKey(), isCloseToWindow);

            // List<SeatHelper> selectedSeats;
        // }
    }

    private TreeMap<Integer, Integer> getNumbersOfConsecutiveSeatsNearWindow(FlightSeatPlanHelper seatPlan) {
        List<SeatBlockHelper> blocks = seatPlan.getCabinSections()
                .stream()
                .map(CabinSectionHelper::getRows)
                .flatMap(List::stream)
                .map(SeatRowHelper::getBlocks)
                .flatMap(List::stream)
                .filter(SeatBlockHelper::isNextToWindow)
                .toList();

        TreeMap<Integer, Integer> availableSeats = new TreeMap<>();

        for (SeatBlockHelper block : blocks) {
            List<SeatHelper> seats = block.getSeats();
            if (block.getBlockPosition() == BlockPosition.RIGHT) {
                seats = seats.reversed();
            }

            int nrOfConsecutiveSeats = 0;
            for (SeatHelper seat : seats) {
                if (!seat.isAvailable()) {
                    break;
                }
                nrOfConsecutiveSeats++;
            }

            if (nrOfConsecutiveSeats > 0) {
                availableSeats.put(
                        nrOfConsecutiveSeats,
                        availableSeats.getOrDefault(nrOfConsecutiveSeats, 0) + 1
                );
            }
        }

        return availableSeats;
    }

    private TreeMap<Integer, Integer> getNumbersOfConsecutiveSeats(FlightSeatPlanHelper seatPlan) {
        List<SeatBlockHelper> blocks = seatPlan.getCabinSections()
                .stream()
                .map(CabinSectionHelper::getRows)
                .flatMap(List::stream)
                .map(SeatRowHelper::getBlocks)
                .flatMap(List::stream)
                .toList();

        TreeMap<Integer, Integer> availableSeats = new TreeMap<>();

        for (SeatBlockHelper block : blocks) {
            int nrOfConsecutiveSeats = 0;
            for (SeatHelper seat : block.getSeats()) {
                if (seat.isAvailable()) {
                    nrOfConsecutiveSeats++;
                } else if (0 < nrOfConsecutiveSeats) {
                    availableSeats.put(
                            nrOfConsecutiveSeats,
                            availableSeats.getOrDefault(nrOfConsecutiveSeats, 0) + 1
                    );
                    nrOfConsecutiveSeats = 0;
                }
            }

            if (nrOfConsecutiveSeats > 0) {
                availableSeats.put(
                        nrOfConsecutiveSeats,
                        availableSeats.getOrDefault(nrOfConsecutiveSeats, 0) + 1
                );
            }
        }

        return availableSeats;
    }

    private static List<SeatHelper> getSeatsFromBlock(SeatBlockHelper block, int nrOfSeats, boolean isCloseToWindow) {
        List<SeatHelper> seats = block.getSeats();
        if (isCloseToWindow && block.getBlockPosition() == BlockPosition.RIGHT ||
                !isCloseToWindow && block.getBlockPosition() == BlockPosition.LEFT) {

            seats.reversed();
        }
        List<SeatHelper> chosenSeats;
        if (isCloseToWindow) {
            chosenSeats = seats.subList(0, nrOfSeats);
        } else {
            int startIndex = findStartIndexOfConsecutiveSeats(seats, nrOfSeats);
            chosenSeats = seats.subList(startIndex, startIndex + nrOfSeats);
        }

        if (chosenSeats.stream().anyMatch(seat -> !seat.isAvailable())) {
            throw new IllegalStateException("Seats were wrongly filtered");
        }
        chosenSeats.forEach(seat -> seat.setAvailable(false));
        return chosenSeats;
    }

    private static int findStartIndexOfConsecutiveSeats(List<SeatHelper> seats, int nrOfConsecutiveSeats) {
        int startIndex = 0;
        int currentNrOfConsecutives = 0;

        for (SeatHelper seat : seats) {
            if (!seat.isAvailable()) {
                startIndex = startIndex + nrOfConsecutiveSeats + 1;
                continue;
            }
            currentNrOfConsecutives++;
            if (currentNrOfConsecutives == nrOfConsecutiveSeats) {
                return startIndex;
            }
        }
        throw new IllegalStateException("There are not enough consecutive seats available");
    }

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

        seatPlan.setCabinSections(
                seatPlan.getCabinSections()
                        .stream()
                        .filter(section -> !section.getRows().isEmpty())
                        .toList()
        );
    }

    private static void filterByConsecutiveSeats(
            CabinSectionHelper cabinSection,
            int numberOfSeats,
            boolean isCloseToWindow
    ) {
        cabinSection.getRows()
                .forEach(row -> filterByConsecutiveSeats(row, numberOfSeats, isCloseToWindow));

        cabinSection.setRows(
                cabinSection.getRows()
                        .stream()
                        .filter(row -> !row.getBlocks().isEmpty())
                        .toList()
        );
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

    private void calculateConsecutiveSeats(FlightSeatPlanHelper seatPlan) {
    }
}
