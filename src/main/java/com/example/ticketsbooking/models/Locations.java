package com.example.ticketsbooking.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Locations {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Location_id")
	private int id;

	private String location;
	
	private String State;
	
	private int pincode;
	
	
	
	@OneToMany(mappedBy = "locations")
	@JsonIgnore
	private Set<Theatrelists> theatrelistssSet = new HashSet<>();



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getState() {
		return State;
	}



	public void setState(String state) {
		State = state;
	}



	public int getPincode() {
		return pincode;
	}



	public void setPincode(int pincode) {
		this.pincode = pincode;
	}



	public Set<Theatrelists> getTheatrelistssSet() {
		return theatrelistssSet;
	}



	public void setTheatrelistssSet(Set<Theatrelists> theatrelistssSet) {
		this.theatrelistssSet = theatrelistssSet;
	}



	public Locations(int id, String location, String state, int pincode, Set<Theatrelists> theatrelistssSet) {
		super();
		this.id = id;
		this.location = location;
		State = state;
		this.pincode = pincode;
		this.theatrelistssSet = theatrelistssSet;
	}



	public Locations() {
		super();
	}



	




       
	
	
	
}
