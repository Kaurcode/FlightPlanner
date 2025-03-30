package com.cgi.flightplanner.repository.planelayout;

import com.cgi.flightplanner.entities.planelayout.SeatPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatPlanRepository extends JpaRepository<SeatPlan, Long> {
}
