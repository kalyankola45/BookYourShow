package com.example.ticketsbooking.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.enumclasses.Showstatustypes;
import com.example.ticketsbooking.models.Bookedseats;
import com.example.ticketsbooking.models.Layout;

import com.example.ticketsbooking.models.Seatstypeprice;
import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.ShowsHistory;
import com.example.ticketsbooking.repository.Bookedseatsrepo;
import com.example.ticketsbooking.repository.Layoutrepo;

import com.example.ticketsbooking.repository.Seattypepricerepo;
import com.example.ticketsbooking.repository.Seattyperepo;
import com.example.ticketsbooking.repository.Showshisrepo;
import com.example.ticketsbooking.repository.Showsrepo;


@Service
public class Showshistorycontroller {

	private Showshisrepo showshisrepo;

	private Bookedseatsrepo bookedseatsrepo;
	private Showsrepo showsrepo;
	private Layoutrepo layoutrepo;
  private	Seattyperepo seattyperepo;
  private Seattypepricerepo seattypepricerepo;
  
 
	
	
	
	public Showshistorycontroller() {
		super();
	}


     @Autowired	
     public Showshistorycontroller(Showshisrepo showshisrepo, Bookedseatsrepo bookedseatsrepo, Showsrepo showsrepo,
			Layoutrepo layoutrepo, Seattyperepo seattyperepo, Seattypepricerepo seattypepricerepo) {
		super();
		this.showshisrepo = showshisrepo;
		this.bookedseatsrepo = bookedseatsrepo;
		this.showsrepo = showsrepo;
		this.layoutrepo = layoutrepo;
		this.seattyperepo = seattyperepo;
		this.seattypepricerepo = seattypepricerepo;
		
	}


	public void calculateshow(int id) {
    	    // Fetch all booked seats for the given show
    	    List<Bookedseats> totalticketsBookedseats = bookedseatsrepo.findByShows_id(id);

    	    int totalcollection = 0;
    	    int totalbookedtickets = 0; // Corrected initialization to 0

    	    // Fetch the show
    	    Shows existShows = showsrepo.findById(id);

    	    // Fetch layout and seat types for the theater
    	    Layout existLayout = layoutrepo.findByTheatrelists(existShows.getTheatrelists());
    	    System.out.println(existLayout.toString());

    	    List<Seattypes> seattypes = seattyperepo.findByLayout_Id(existLayout.getId());

    	    // Fetch seat prices for the given show
    	    List<Seatstypeprice> seatsprice = seattypepricerepo.findByshowid(id);

    	
    	    // Calculate total tickets available
    	    int totaltickets = seattypes.stream()
    	                                .mapToInt(Seattypes::getNoofseats)
    	                                .sum();

    	    // Calculate expected gross revenue
    	    int expectedgross = seattypes.stream()
    	                                 .flatMapToInt(stype -> seatsprice.stream()
    	                                                                  .filter(sprice -> stype.getSeattypename().equals(sprice.getSeattypes().getSeattypename()))
    	                                                                  .mapToInt(sprice -> sprice.getPrice() * stype.getNoofseats()))
    	                                 .sum();

    	    System.err.println("Expected gross: " + expectedgross);

    	    // Calculate total collection and tickets sold
    	    for (Bookedseats bookedseats : totalticketsBookedseats) {
    	        totalcollection +=  bookedseats.getTotalprice();
    	        totalbookedtickets += bookedseats.getTotaltickets();
    	    }

    	    // Calculate occupancy
    	    double accancy = (double) totalbookedtickets / totaltickets * 100;
    	    int occupancy = (int) accancy;

    	    // Fetch or create show history
    	    ShowsHistory existHistory = showshisrepo.findByShows(existShows);

    	    if (existHistory == null) {
    	        ShowsHistory showhis = new ShowsHistory();
    	        showhis.setCurrentgross(totalcollection);
    	        showhis.setExpectedgross(expectedgross);
    	        showhis.setNoofticketssold(totalbookedtickets);
    	        showhis.setOccupancy(occupancy);
    	        showhis.setShows(existShows);
    	        showhis.setTotaltickets(totaltickets);
    	        showhis.setShowstatus(Showstatustypes.Notyet.name());
    	        showshisrepo.save(showhis);
    	    } else {
    	        existHistory.setCurrentgross(totalcollection);
    	        existHistory.setExpectedgross(expectedgross);
    	        existHistory.setNoofticketssold(totalbookedtickets);
    	        existHistory.setOccupancy(occupancy);
    	        existHistory.setTotaltickets(totaltickets);
    	        existHistory.setShows(existShows);
    	        showshisrepo.save(existHistory);
    	    }
    	}



	public static void sampleVoid () {
		
		

	}

	



	
    
	
}
