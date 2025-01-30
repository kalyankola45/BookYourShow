package com.example.ticketsbooking.models;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Bookedseats {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Booked_id")
	private int id;
	

    
   
    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonIgnore
    private Shows shows;

    private String confirmationcode;
    
    private boolean ticketstatus;
    private int totaltickets;
    private LocalTime localTime;
    
    private String localDate;
    
    private String ticket;
    
    
    private int totalprice;
    
    
  	@ManyToOne
  	@JoinColumn(name = "Theatre_id")
  	@JsonIgnore
  	private Theatrelists theatrelists;
  	
  	
    

  	@OneToOne
  	@JoinColumn(name = "Id")
  	@JsonIgnore
  	private Transectiondetails transectiondetails;




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




	public String getConfirmationcode() {
		return confirmationcode;
	}




	public void setConfirmationcode(String confirmationcode) {
		this.confirmationcode = confirmationcode;
	}




	public boolean isTicketstatus() {
		return ticketstatus;
	}




	public void setTicketstatus(boolean ticketstatus) {
		this.ticketstatus = ticketstatus;
	}




	public int getTotaltickets() {
		return totaltickets;
	}




	public void setTotaltickets(int totaltickets) {
		this.totaltickets = totaltickets;
	}




	public LocalTime getLocalTime() {
		return localTime;
	}




	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}




	public String getLocalDate() {
		return localDate;
	}




	public void setLocalDate(String localDate) {
		this.localDate = localDate;
	}




	public String getTicket() {
		return ticket;
	}




	public void setTicket(String ticket) {
		this.ticket = ticket;
	}








	public int getTotalprice() {
		return totalprice;
	}




	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}




	public Theatrelists getTheatrelists() {
		return theatrelists;
	}




	public void setTheatrelists(Theatrelists theatrelists) {
		this.theatrelists = theatrelists;
	}




	public Transectiondetails getTransectiondetails() {
		return transectiondetails;
	}




	public void setTransectiondetails(Transectiondetails transectiondetails) {
		this.transectiondetails = transectiondetails;
	}




	public Bookedseats(int id, Shows shows, String confirmationcode, boolean ticketstatus, int totaltickets,
			LocalTime localTime, String localDate, String ticket, int totalprice, Theatrelists theatrelists,
			Transectiondetails transectiondetails) {
		super();
		this.id = id;
		this.shows = shows;
		this.confirmationcode = confirmationcode;
		this.ticketstatus = ticketstatus;
		this.totaltickets = totaltickets;
		this.localTime = localTime;
		this.localDate = localDate;
		this.ticket = ticket;
		this.totalprice = totalprice;
		this.theatrelists = theatrelists;
		this.transectiondetails = transectiondetails;
	}




	public Bookedseats() {
		super();
	}





	
	

	
	
	
    
    
    
    
}
