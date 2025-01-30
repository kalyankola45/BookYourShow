package com.example.ticketsbooking.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticketsbooking.models.Locations;
import com.example.ticketsbooking.models.Theatrelists;
import com.example.ticketsbooking.repository.Theatrelistrepo;
import com.example.ticketsbooking.service.Theatrelistint;

@Service
public class Theatrelistimp implements Theatrelistint{
	
	Theatrelistrepo theatrelistrepo;
	
     @Autowired
	public Theatrelistimp(Theatrelistrepo theatrelistrepo) {
		super();
		this.theatrelistrepo = theatrelistrepo;
	}

	@Override
	public List<Theatrelists> getallTheatrelist() {
		List<Theatrelists>  allTheatrelists = theatrelistrepo.findAll();		
		return allTheatrelists;
	}

	@Override
	public void createTheatreliss(Theatrelists theatrelists) {
	     theatrelistrepo.save(theatrelists);
	}

	@Override
	public Theatrelists getbyidTheatrelists(int id) {
		  Theatrelists resulTheatrelists = theatrelistrepo.findById(id).orElseThrow(null);
		return  resulTheatrelists;
	}

	@Override
	public void updatetheatrelist(Theatrelists theatrelists, int id) {
		 Theatrelists exisTheatrelists =  theatrelistrepo.findById(id).orElseThrow(null);
		 exisTheatrelists.setLocations(theatrelists.getLocations());
	
		 exisTheatrelists.setTheatrename(theatrelists.getTheatrename());
	
		exisTheatrelists.setAdress(theatrelists.getAdress());
		exisTheatrelists.setAccess(theatrelists.isAccess());
		exisTheatrelists.setContact(theatrelists.getContact());
		exisTheatrelists.setManagername(theatrelists.getManagername());
		exisTheatrelists.setPincode(theatrelists.getPincode());
	   exisTheatrelists.setEmail(theatrelists.getEmail());
	
	     theatrelistrepo.save(exisTheatrelists);
	}

	@Override
	public void deletetheatrelist(int id) {
		theatrelistrepo.deleteById(id);
		
	}

	@Override
	public List<Theatrelists> getallbylocations(int id) {
		
 
		return theatrelistrepo.findByLocations_id(id);
	}

}
