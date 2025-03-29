package com.cgi.flightplanner.repository.planelayout;

import com.cgi.flightplanner.entities.planelayout.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
}
