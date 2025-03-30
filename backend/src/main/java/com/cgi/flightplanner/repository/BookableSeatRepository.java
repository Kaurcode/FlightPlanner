package com.cgi.flightplanner.repository;

import com.cgi.flightplanner.entities.BookableSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookableSeatRepository extends JpaRepository<BookableSeat, Long> {
}
