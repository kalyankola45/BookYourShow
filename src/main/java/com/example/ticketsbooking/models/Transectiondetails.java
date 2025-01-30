package com.example.ticketsbooking.models;

import java.util.List;

import org.springframework.boot.context.properties.bind.Name;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;



@Entity
public class Transectiondetails {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Transid") // Ensure this matches the DB column name
	    private int Id;
	 
	  @OneToMany(mappedBy = "transectiondetails")
	  @JsonIgnore
	  private List<BookingSeats> bookingSeats;
	  
	 
	  
	  private String Paymentstatus;

	  private String email;
		
		private String no;
		
		private int amount;
		
		private String rzorpayid;

		public int getId() {
			return Id;
		}

		public void setId(int id) {
			Id = id;
		}

		public List<BookingSeats> getBookingSeats() {
			return bookingSeats;
		}

		public void setBookingSeats(List<BookingSeats> bookingSeats) {
			this.bookingSeats = bookingSeats;
		}

		public String getPaymentstatus() {
			return Paymentstatus;
		}

		public void setPaymentstatus(String paymentstatus) {
			Paymentstatus = paymentstatus;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getNo() {
			return no;
		}

		public void setNo(String no) {
			this.no = no;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public String getRzorpayid() {
			return rzorpayid;
		}

		public void setRzorpayid(String rzorpayid) {
			this.rzorpayid = rzorpayid;
		}

		public Transectiondetails(int id, List<BookingSeats> bookingSeats, String paymentstatus, String email,
				String no, int amount, String rzorpayid) {
			super();
			Id = id;
			this.bookingSeats = bookingSeats;
			Paymentstatus = paymentstatus;
			this.email = email;
			this.no = no;
			this.amount = amount;
			this.rzorpayid = rzorpayid;
		}

		public Transectiondetails() {
			super();
		}

		

		
		
		
	  
	  
	  
}
