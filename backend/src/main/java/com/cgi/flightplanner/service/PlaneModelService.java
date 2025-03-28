package com.cgi.flightplanner.service;

import com.cgi.flightplanner.entities.planelayout.PlaneModel;
import com.cgi.flightplanner.repository.PlaneModelRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaneModelService extends CrudService<PlaneModel, PlaneModelRepository> {
    PlaneModelService(PlaneModelRepository repository) {
        super(repository);
    }
}
