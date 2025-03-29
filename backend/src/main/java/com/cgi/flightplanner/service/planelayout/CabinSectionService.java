package com.cgi.flightplanner.service.planelayout;

import com.cgi.flightplanner.entities.planelayout.CabinSection;
import com.cgi.flightplanner.repository.planelayout.CabinSectionRepository;
import com.cgi.flightplanner.service.CrudService;
import org.springframework.stereotype.Service;

@Service
public class CabinSectionService extends CrudService<CabinSection, CabinSectionRepository> {
    public CabinSectionService(CabinSectionRepository repository) {
        super(repository);
    }
}
