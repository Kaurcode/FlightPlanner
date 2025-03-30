package com.cgi.flightplanner.repository.planelayout;

import com.cgi.flightplanner.entities.planelayout.SeatBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatBlockRepository extends JpaRepository<SeatBlock, Long> {
}
