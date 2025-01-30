package com.example.ticketsbooking.wrappers;

public class Seatspricedto {

	private int seatid;
	
	private int price;
	
	private int Showid;

	public int getSeatid() {
		return seatid;
	}

	public void setSeatid(int seatid) {
		this.seatid = seatid;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getShowid() {
		return Showid;
	}

	public void setShowid(int showid) {
		Showid = showid;
	}

	public Seatspricedto(int seatid, int price, int showid) {
		super();
		this.seatid = seatid;
		this.price = price;
		Showid = showid;
	}

	public Seatspricedto() {
		super();
	}
	
	
}
