package com.example.ticketsbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Layout;

import com.example.ticketsbooking.models.Theatrelists;

@Repository
public interface Layoutrepo extends JpaRepository<Layout, Integer>  {

	Layout findByTheatrelists(Theatrelists theatrelists);
	 Layout findByTheatrelists_Id(int id); 
}
