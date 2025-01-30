package com.example.ticketsbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ticketsbooking.models.Bookedseats;
import com.example.ticketsbooking.models.Theatrelists;

import java.util.List;



@Repository
public interface Bookedseatsrepo extends JpaRepository<Bookedseats, Integer> {
     
	 boolean existsByconfirmationcode(String code);
	 
	 Bookedseats findByConfirmationcode(String confirmationcode);
	 
	 List<Bookedseats> findByShows_id(int id);
	 
 
	 List<Bookedseats> findByTheatrelists_Id(int id);
}
