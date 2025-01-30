package com.example.ticketsbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Movies;
import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.Theatrelists;
import java.util.List;
import java.util.Set;


@Repository
public interface Showsrepo extends JpaRepository<Shows, Integer>{

	boolean existsBydate(String date);
	
	List<Shows> findByTheatrelists(Theatrelists theatrelists);
	
	
	Shows findById(int id);
	
	List<Shows> findByTheatrelistsAndDate(Theatrelists theatrelists, String date);
	
	@Query("SELECT s FROM Shows s WHERE s.theatrelists IN :theatrelists AND s.bookingstatus = :status")
	List<Shows> findMoviesByTheatrelists(@Param("theatrelists") List<Theatrelists> theatrelists, @Param("status") boolean status);
   
	@Query("select s from Shows s where s.bookingstatus = :satus AND s.theatrelists = :thhTheatrelists AND s.id = :showid")
	Shows findtrueshows(Theatrelists thhTheatrelists,Boolean satus,int showid);

	

}
