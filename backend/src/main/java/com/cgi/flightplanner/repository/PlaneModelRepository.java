package com.cgi.flightplanner.repository;

import com.cgi.flightplanner.entities.planelayout.PlaneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneModelRepository extends JpaRepository<PlaneModel, Long> {
}
