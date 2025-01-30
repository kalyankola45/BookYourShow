package com.example.ticketsbooking.service;

import java.util.List;

import com.example.ticketsbooking.models.Shows;

public interface Showsint {
	
	int Createshow(Shows shows);
	
	List<Shows> getallshows();
	
	Shows getbyidShows(int id);
	
	void updateshow(Shows shows,int id);
	
	void Deleteshow(int id);

}
