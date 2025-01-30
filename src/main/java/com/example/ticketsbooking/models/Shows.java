package com.example.ticketsbooking.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
public class Shows {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "show_id")
	private int id;
	
	
	@ManyToOne
	@JoinColumn(name = "theatre_id")
	private Theatrelists theatrelists;

	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movies movies;

	@OneToMany(mappedBy = "shows",cascade = CascadeType.ALL)
	private List<Seatstypeprice> seatstypeprices =new ArrayList<>();
	@OneToOne(mappedBy = "shows",cascade = CascadeType.ALL)
	@JsonIgnore
	private ShowsHistory showsHistory;
	
	@OneToMany(mappedBy = "shows")
	@JsonIgnore
	private List<BookingSeats> allseats;
	
	private String showstarttime;
	
	
	private String showendtime;
	

		private String date;
		

      private Boolean bookingstatus;
      
      

		


		public Boolean getBookingstatus() {
		return bookingstatus;
	}


	


		public void setBookingstatus(Boolean bookingstatus) {
			this.bookingstatus = bookingstatus;
		}





		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public Theatrelists getTheatrelists() {
			return theatrelists;
		}


		public void setTheatrelists(Theatrelists theatrelists) {
			this.theatrelists = theatrelists;
		}


		public Movies getMovies() {
			return movies;
		}


		public void setMovies(Movies movies) {
			this.movies = movies;
		}


		public String getShowstarttime() {
			return showstarttime;
		}


		public void setShowstarttime(String showtarttime) {
			this.showstarttime = showtarttime;
		}


		public String getShowendtime() {
			return showendtime;
		}


		public void setShowendtime(String showendtime) {
			this.showendtime = showendtime;
		}


		public String getDate() {
			return date;
		}


		public void setDate(String date) {
			this.date = date;
		}




		public List<Seatstypeprice> getSeatstypeprices() {
			return seatstypeprices;
		}


		public void setSeatstypeprices(List<Seatstypeprice> seatstypeprices) {
			this.seatstypeprices = seatstypeprices;
		}


		




		public Shows(int id, Theatrelists theatrelists, Movies movies, List<Seatstypeprice> seatstypeprices,
				String showstarttime, String showendtime, String date, Boolean bookingstatus) {
			super();
			this.id = id;
			this.theatrelists = theatrelists;
			this.movies = movies;
			this.seatstypeprices = seatstypeprices;
			this.showstarttime = showstarttime;
			this.showendtime = showendtime;
			this.date = date;
			this.bookingstatus = bookingstatus;
		}





		public Shows() {
			super();
		}





		@Override
		public String toString() {
			return "Shows [id=" + id + ", theatrelists=" + theatrelists + ", movies=" + movies + ", seatstypeprices="
					+ seatstypeprices + ", showstarttime=" + showstarttime + ", showendtime=" + showendtime + ", date="
					+ date + ", bookingstatus=" + bookingstatus + "]";
		}


		

		



     
		





		
		
	 
	
	
	
	

}
