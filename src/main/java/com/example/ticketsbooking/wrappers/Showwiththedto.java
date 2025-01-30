package com.example.ticketsbooking.wrappers;

import java.util.List;

import com.example.ticketsbooking.models.ShowsHistory;

public class Showwiththedto {
	
	 private int showId;
	    private String movieName;
	    private String showStartTime;
	    private String showEndTime;
	    private String date;
	
	  
	  
	  private List<Integer> seatprices;
	  
	  private ShowsHistory showsHistory;
	  
	   private  Boolean bookingstatus;

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getShowStartTime() {
		return showStartTime;
	}

	public void setShowStartTime(String showStartTime) {
		this.showStartTime = showStartTime;
	}

	public String getShowEndTime() {
		return showEndTime;
	}

	public void setShowEndTime(String showEndTime) {
		this.showEndTime = showEndTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Integer> getSeatprices() {
		return seatprices;
	}

	public void setSeatprices(List<Integer> seatprices) {
		this.seatprices = seatprices;
	}

	public ShowsHistory getShowsHistory() {
		return showsHistory;
	}

	public void setShowsHistory(ShowsHistory showsHistory) {
		this.showsHistory = showsHistory;
	}

	public Boolean getBookingstatus() {
		return bookingstatus;
	}

	public void setBookingstatus(Boolean bookingstatus) {
		this.bookingstatus = bookingstatus;
	}

	

	

	  
	  
	  
	  
	  
	    
	    
}
