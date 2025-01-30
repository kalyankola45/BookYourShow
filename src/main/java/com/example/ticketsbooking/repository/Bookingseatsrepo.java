package com.example.ticketsbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.BookingSeats;
import com.example.ticketsbooking.models.Shows;

import java.util.List;



@Repository
public interface Bookingseatsrepo  extends JpaRepository<BookingSeats, Integer>{
	  
	List<BookingSeats> findByShows(Shows shows);
	
	boolean existsByShowsIdAndSeatcolumnAndRowname(int showid, Integer integer, String string);
	  List<BookingSeats> findByBookingcode(String bookingcode);
	  
	
	}

