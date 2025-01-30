package com.example.ticketsbooking.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Seatstypeprice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	
	
	
	
	
	private int price;
	
	@ManyToOne
	@JoinColumn(name = "Seattype_id")
	private Seattypes seattypes;

	@ManyToOne
	@JoinColumn(name = "show_id")
	private Shows shows;



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}



	public Seattypes getSeattypes() {
		return seattypes;
	}



	public void setSeattypes(Seattypes seattypes) {
		this.seattypes = seattypes;
	}



	public Shows getShows() {
		return shows;
	}



	public void setShows(Shows shows) {
		this.shows = shows;
	}



	public Seatstypeprice(int id, int price, Seattypes seattypes, Shows shows) {
		super();
		this.id = id;
		this.price = price;
		this.seattypes = seattypes;
		this.shows = shows;
	}



	public Seatstypeprice() {
		super();
	}



	
	

	
	
}


