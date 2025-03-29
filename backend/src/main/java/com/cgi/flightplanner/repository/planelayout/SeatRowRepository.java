package com.cgi.flightplanner.repository.planelayout;

import com.cgi.flightplanner.entities.planelayout.SeatRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRowRepository extends JpaRepository<SeatRow, Long> {
}
