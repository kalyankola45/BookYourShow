package com.example.ticketsbooking.wrappers;

import java.util.List;

import com.example.ticketsbooking.models.Movies;
import com.example.ticketsbooking.models.Seatstypeprice;
import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.Theatrelists;

public class ShowsDto {

	
	
	

	private Shows shows;
	

	private  List< Seatstypeprice > seatstypeprice;


	public Shows getShows() {
		return shows;
	}


	public void setShows(Shows shows) {
		this.shows = shows;
	}


	
	public ShowsDto(Shows shows, List<Seatstypeprice> seatstypeprice) {
		super();
		this.shows = shows;
		this.seatstypeprice = seatstypeprice;
	}


	public List<Seatstypeprice> getSeatstypeprice() {
		return seatstypeprice;
	}


	public void setSeatstypeprice(List<Seatstypeprice> seatstypeprice) {
		this.seatstypeprice = seatstypeprice;
	}


	public ShowsDto() {
		super();
	}
	
	
	
	
}
