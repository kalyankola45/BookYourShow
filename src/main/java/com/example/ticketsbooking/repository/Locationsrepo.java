package com.example.ticketsbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Locations;

@Repository
public interface Locationsrepo extends JpaRepository<Locations, Integer> {

	

	Locations findByLocation(String Location);
	
	
}
