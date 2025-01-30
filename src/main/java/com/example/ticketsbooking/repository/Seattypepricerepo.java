package com.example.ticketsbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Seatstypeprice;
import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Shows;

@Repository
public interface Seattypepricerepo extends JpaRepository<Seatstypeprice, Integer> {

	boolean existsBySeattypesAndShows(Seattypes seattypes, Shows shows);
	
	Seatstypeprice findByShowsAndSeattypesId(Shows shows, int id);
	
	@Query("Select t from Seatstypeprice t where t.shows.id = :id")
	List<Seatstypeprice> findByshowid(@Param("id") int id);
	
	@Query("select t.price from Seatstypeprice t where t.shows.id  = :id ")
	List<Integer> findseatprices(@Param("id") int id);
}
