package com.cgi.flightplanner.service.planelayout;

import com.cgi.flightplanner.entities.planelayout.SeatPlan;
import com.cgi.flightplanner.repository.planelayout.SeatPlanRepository;
import com.cgi.flightplanner.service.CrudService;
import org.springframework.stereotype.Service;

@Service
public class SeatPlanService extends CrudService<SeatPlan, SeatPlanRepository> {
    public SeatPlanService(SeatPlanRepository repository) {
        super(repository);
    }
}
