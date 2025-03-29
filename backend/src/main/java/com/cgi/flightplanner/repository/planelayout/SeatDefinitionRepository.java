package com.cgi.flightplanner.repository.planelayout;

import com.cgi.flightplanner.entities.planelayout.SeatDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatDefinitionRepository extends JpaRepository<SeatDefinition, Long> {
}
