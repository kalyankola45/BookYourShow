package com.example.ticketsbooking.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticketsbooking.models.Seatstypeprice;
import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.repository.Seattypepricerepo;
import com.example.ticketsbooking.repository.Showsrepo;
import com.example.ticketsbooking.service.Showsint;

import jakarta.transaction.Transactional;

@Service
public class Showsimpli implements Showsint {
	Showsrepo showsrepo;
  Seattypepricerepo seattypepricerepo;
       
  @Autowired
      public Showsimpli(Showsrepo showsrepo, Seattypepricerepo seattypepricerepo) {
	super();
	this.showsrepo = showsrepo;
	this.seattypepricerepo = seattypepricerepo;
}

	
	

      @Override
      public int Createshow(Shows shows) {
         
       
         
         showsrepo.save(shows);
       
         return shows.getId();

        
         
         
      }
  

	@Override
	public List<Shows> getallshows() {
		List<Shows> allShows = showsrepo.findAll();
		return allShows;
	}

	@Override
	public Shows getbyidShows(int id) {
		Shows singleahowShows = showsrepo.findById(id);
		
		
		return singleahowShows;
       
	}
	

	
	@Override
	public void updateshow(Shows shows, int id) {
	    // Retrieve the existing show by ID
	    Shows existshow = showsrepo.findById(id);
	    // Update show details
	    existshow.setShowendtime(shows.getShowendtime());
	    existshow.setDate(shows.getDate());
	    existshow.setMovies(shows.getMovies());
	    existshow.setBookingstatus(shows.getBookingstatus());
      
	   
	    showsrepo.save(existshow);
	}


	@Override
	public void Deleteshow(int id) {
		
		showsrepo.deleteById(id);
	}

}
