package com.cgi.flightplanner.repository.planelayout;

import com.cgi.flightplanner.entities.planelayout.CabinSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinSectionRepository extends JpaRepository<CabinSection, Long> {
}
