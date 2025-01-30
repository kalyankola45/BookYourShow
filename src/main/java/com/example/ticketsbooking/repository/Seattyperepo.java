package com.example.ticketsbooking.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Seattypes;

@Repository
public interface Seattyperepo extends JpaRepository<Seattypes, Integer>  {
	
List<Seattypes> findByLayout_Id(int id);


}
