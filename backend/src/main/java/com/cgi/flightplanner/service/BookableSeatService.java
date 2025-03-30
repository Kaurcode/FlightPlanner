package com.cgi.flightplanner.service;

import com.cgi.flightplanner.entities.BookableSeat;
import com.cgi.flightplanner.entities.Flight;
import com.cgi.flightplanner.entities.planelayout.CabinSection;
import com.cgi.flightplanner.entities.planelayout.SeatBlock;
import com.cgi.flightplanner.entities.planelayout.SeatRow;
import com.cgi.flightplanner.repository.BookableSeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookableSeatService extends CrudService<BookableSeat, BookableSeatRepository> {
    public BookableSeatService(BookableSeatRepository repository) {
        super(repository);
    }

    public List<BookableSeat> generateBookableSeatsForFlight(Flight flight) {
        List<BookableSeat> seats = flight.getPlane()
                .getPlaneModel()
                .getSeatPlan()
                .getCabinSections()
                .stream()
                .map(CabinSection::getSeatRows)
                .flatMap(List::stream)
                .map(SeatRow::getBlocks)
                .flatMap(List::stream)
                .filter(block -> block instanceof SeatBlock)
                .map(SeatBlock.class::cast)
                .map(SeatBlock::getSeats)
                .flatMap(List::stream)
                .map(seat -> new BookableSeat(false, flight, seat))
                .toList();

        return this.repository.saveAll(seats);
    }
}
