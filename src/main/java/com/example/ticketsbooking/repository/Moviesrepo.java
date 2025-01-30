package com.example.ticketsbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Movies;

@Repository
public interface Moviesrepo extends JpaRepository<Movies, Integer> {
   
	Movies findByName(String name);
}
