package com.example.ticketsbooking.service.imp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticketsbooking.models.Locations;
import com.example.ticketsbooking.repository.Locationsrepo;
import com.example.ticketsbooking.service.Locationint;

import jakarta.transaction.Transactional;

@Service
public class Locationimp implements Locationint{

	Locationsrepo locationsrepo;
	
	@Autowired
	public Locationimp(Locationsrepo locationsrepo) {
		super();
		this.locationsrepo = locationsrepo;
	}

	@Override
	public List<Locations> getallLocations() {
	    List<Locations> allLocations = locationsrepo.findAll();
	    
	    return allLocations;
	}

	@Override
	public void Createlocation(Locations locations) {
		locationsrepo.save(locations);
		
	}

	@Override
	public Locations getbyidlocation(int id) {
		Locations singleLocations = locationsrepo.findById(id).orElseThrow(null);
		return singleLocations;
	}

	@Override
	@Transactional
	public void Updatelocation(Locations locations, int id) {
		
		System.out.println("process stART");
		
		 
		Locations exisLocations = locationsrepo.findById(id).orElseThrow(null);
	   
		exisLocations.setLocation(locations.getLocation());
		
		exisLocations.setPincode(locations.getPincode());
		exisLocations.setState(locations.getState());
	
		locationsrepo.save(exisLocations);
		
	}

	@Override
	public void DelelteLocation(int id) {
		locationsrepo.deleteById(id);	
	}
	
		
		

}
