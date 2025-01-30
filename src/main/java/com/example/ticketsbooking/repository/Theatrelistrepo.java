package com.example.ticketsbooking.repository;

import java.util.List;

import org.springframework.beans.factory.parsing.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Locations;
import com.example.ticketsbooking.models.Theatrelists;

@Repository
public interface Theatrelistrepo extends JpaRepository<Theatrelists, Integer> {

	List<Theatrelists> findByLocations(Locations givenloc);
	
Theatrelists findByTheatrename(String theatrename);

@Query("select t from  Theatrelists t where t.locations.id = :id")
List<Theatrelists> findByLocations_id(int id);

	Theatrelists  findByEmail(String email);

	@Query("Select t from Theatrelists t where t.Layoutstatus = :status ")
	List<Theatrelists> findBylists(String status);
	
	
}
