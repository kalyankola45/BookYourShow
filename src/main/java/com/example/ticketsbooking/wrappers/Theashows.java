package com.example.ticketsbooking.wrappers;

import java.util.List;

import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.Theatrelists;

public class Theashows {
	
	private List<Theatrelists> theatresList;
	
	
	private List<Shows> shows;


	public List<Theatrelists> getTheatresList() {
		return theatresList;
	}


	public void setTheatresList(List<Theatrelists> theatresList) {
		this.theatresList = theatresList;
	}


	public List<Shows> getShows() {
		return shows;
	}


	public void setShows(List<Shows> shows) {
		this.shows = shows;
	}


	public Theashows(List<Theatrelists> theatresList, List<Shows> shows) {
		super();
		this.theatresList = theatresList;
		this.shows = shows;
	}


	public Theashows() {
		super();
	}
	
	
	

}
