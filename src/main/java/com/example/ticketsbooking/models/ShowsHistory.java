package com.example.ticketsbooking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ShowsHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Showshis_id")
	private int id;
	
	@OneToOne
	@JoinColumn(name ="show_id" )
	@JsonIgnore
	private Shows shows;
	
	private float occupancy;
	
	private int noofticketssold;
	
	private int totaltickets;
	
	private int expectedgross;
	
	private int currentgross;
	
	private String Showstatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Shows getShows() {
		return shows;
	}

	public void setShows(Shows shows) {
		this.shows = shows;
	}

	public float getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(float occupancy) {
		this.occupancy = occupancy;
	}

	public int getNoofticketssold() {
		return noofticketssold;
	}

	public void setNoofticketssold(int noofticketssold) {
		this.noofticketssold = noofticketssold;
	}

	public int getTotaltickets() {
		return totaltickets;
	}

	public void setTotaltickets(int totaltickets) {
		this.totaltickets = totaltickets;
	}

	public int getExpectedgross() {
		return expectedgross;
	}

	public void setExpectedgross(int expectedgross) {
		this.expectedgross = expectedgross;
	}

	public int getCurrentgross() {
		return currentgross;
	}

	public void setCurrentgross(int currentgross) {
		this.currentgross = currentgross;
	}

	public String getShowstatus() {
		return Showstatus;
	}

	public void setShowstatus(String showstatus) {
		Showstatus = showstatus;
	}

	public ShowsHistory(int id, Shows shows, float occupancy, int noofticketssold, int totaltickets, int expectedgross,
			int currentgross, String showstatus) {
		super();
		this.id = id;
		this.shows = shows;
		this.occupancy = occupancy;
		this.noofticketssold = noofticketssold;
		this.totaltickets = totaltickets;
		this.expectedgross = expectedgross;
		this.currentgross = currentgross;
		Showstatus = showstatus;
	}

	public ShowsHistory() {
		super();
	}


	
	
	
	
	
	
	

}
