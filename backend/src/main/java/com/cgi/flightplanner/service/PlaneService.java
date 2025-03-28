package com.cgi.flightplanner.service;

import com.cgi.flightplanner.entities.Plane;
import com.cgi.flightplanner.repository.PlaneRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaneService extends CrudService<Plane, PlaneRepository> {
    PlaneService(PlaneRepository repository) {
        super(repository);
    }
}
