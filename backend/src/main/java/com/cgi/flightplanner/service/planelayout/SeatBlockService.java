package com.cgi.flightplanner.service.planelayout;

import com.cgi.flightplanner.entities.planelayout.SeatBlock;
import com.cgi.flightplanner.repository.planelayout.SeatBlockRepository;
import com.cgi.flightplanner.service.CrudService;
import org.springframework.stereotype.Service;

@Service
public class SeatBlockService extends CrudService<SeatBlock, SeatBlockRepository> {
    public SeatBlockService(SeatBlockRepository repository) {
        super(repository);
    }
}
