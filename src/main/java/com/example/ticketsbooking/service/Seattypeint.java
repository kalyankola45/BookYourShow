package com.example.ticketsbooking.service;

import java.util.List;

import com.example.ticketsbooking.models.Movies;
import com.example.ticketsbooking.models.Seattypes;

public interface Seattypeint  {
	
	void createseattype(Seattypes seattypes);
	
	List<Seattypes> getall(int id);
	
	Seattypes findseattype(int id);
	
	void Updateseattype(Seattypes seattypes,int id);
	
	void DeleteSeattype(int id);
	
	void addtotalseats(int id);

}
