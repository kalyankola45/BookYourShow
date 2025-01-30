package com.example.ticketsbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.ShowsHistory;
import java.util.List;




@Repository
public interface Showshisrepo extends JpaRepository<ShowsHistory, Integer> {

	     ShowsHistory  findByShows(Shows shows);
}
