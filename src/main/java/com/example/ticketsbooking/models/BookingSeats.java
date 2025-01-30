package com.example.ticketsbooking.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;



@Entity
public class BookingSeats {
	
	
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "Booking_id")
	private int id;
	
	
	
	@ManyToOne
	@JoinColumn(name = "show_id")
	private Shows shows;
	
	@OneToOne
	@JoinColumn(name = "Transid")
	@JsonIgnore
	private Transectiondetails transectiondetails;
	
	
	private String rowname;
	
	private int seatcolumn;
	
	
	private  int seattype;
	
	private int price;
	
	
	private String bookingcode;
	
	

	public String getBookingcode() {
		return bookingcode;
	}

	public void setBookingcode(String bookingcode) {
		this.bookingcode = bookingcode;
	}

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

	public String getRowname() {
		return rowname;
	}

	public void setRowname(String rowname) {
		this.rowname = rowname;
	}

	public int getSeatcolumn() {
		return seatcolumn;
	}

	public void setSeatcolumn(int seatcolumn) {
		this.seatcolumn = seatcolumn;
	}

	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSeattype() {
		return seattype;
	}

	public void setSeattype(int seattype) {
		this.seattype = seattype;
	}

	public Transectiondetails getTransectiondetails() {
		return transectiondetails;
	}

	public void setTransectiondetails(Transectiondetails transectiondetails) {
		this.transectiondetails = transectiondetails;
	}

	public BookingSeats(int id, Shows shows, Transectiondetails transectiondetails, String rowname, int seatcolumn,
			int seattype, int price, String bookingcode) {
		super();
		this.id = id;
		this.shows = shows;
		this.transectiondetails = transectiondetails;
		this.rowname = rowname;
		this.seatcolumn = seatcolumn;
		this.seattype = seattype;
		this.price = price;
		this.bookingcode = bookingcode;
	}

	public BookingSeats() {
		super();
	}

	
	

	
	


	
	
	
	
	

}
