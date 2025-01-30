package com.example.ticketsbooking.service;


import java.util.List;

import com.example.ticketsbooking.models.Locations;

public interface Locationint {
	
	List<Locations> getallLocations();
	
	void Createlocation(Locations locations);
	
	Locations getbyidlocation(int id);
	
	void Updatelocation(Locations locations , int id);
	
	void DelelteLocation(int id);

}
