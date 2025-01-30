	package com.example.ticketsbooking.controller;
	
	import java.util.ArrayList;
import java.util.List;
	
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;
	
	import com.example.ticketsbooking.models.Locations;
import com.example.ticketsbooking.models.Theatrelists;
	import com.example.ticketsbooking.repository.Locationsrepo;
import com.example.ticketsbooking.repository.Theatrelistrepo;
import com.example.ticketsbooking.service.Theatrelistint;
import com.example.ticketsbooking.service.imp.Locationimp;
	import com.example.ticketsbooking.service.imp.Theatrelistimp;
import com.example.ticketsbooking.wrappers.Theatredto;

import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.PathVariable;
	
	
	@RestController
	@RequestMapping("/admin/theatre")
	@CrossOrigin
	public class Theatrelistcontroller {
	
		Locationsrepo locationsrepo;
		
		   Theatrelistint theatreservice;
		  
		  Locationimp locationimp;
		  
		  Theatrelistrepo theatrelistrepo;
		  
		  PasswordEncoder passwordEncoder;
		  
		  
		  
		 
		  
	  
	     @Autowired
public Theatrelistcontroller(Locationsrepo locationsrepo, Theatrelistint theatreservice,
				Locationimp locationimp, Theatrelistrepo theatrelistrepo, PasswordEncoder passwordEncoder) {
			super();
			this.locationsrepo = locationsrepo;
			this.theatreservice = theatreservice;
			this.locationimp = locationimp;
			this.theatrelistrepo = theatrelistrepo;
			this.passwordEncoder = passwordEncoder;
		}






		@GetMapping("/getall")
		private List<Theatrelists> getallTheatrelists(){
			return theatreservice.getallTheatrelist();
		}
		
	
		@GetMapping("/getall/{location}")
		private List<Theatrelists> getTheatrelists(@PathVariable("location") String locations){
		Locations locationid=	locationsrepo.findByLocation(locations);
			  
			return theatreservice.getallbylocations(locationid.getId());
		}
	
	

		@PostMapping("/create")
		private ResponseEntity<?> createtheatrelist(@RequestBody Theatredto theatredto) {
		    System.err.println("Theatre list adding process start");

		    Theatrelists theatrelists = theatredto.getTheatrelists();
		    Locations locations = theatredto.getLocations();
		    String currentPass = theatrelists.getPassword();
		    System.out.println("Current password: " + currentPass);
		    
		    System.err.println(theatrelists.toString());

		    if (theatrelists.getPassword() == null || theatrelists.getPassword().isEmpty()) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password cannot be null or empty");
		    }

		    Locations existLocations = locationsrepo.findByLocation(locations.getLocation());
		    Theatrelists existTheatrelist = theatrelistrepo.findByEmail(theatrelists.getEmail());

		    if (existTheatrelist != null) {
		        return ResponseEntity.status(HttpStatus.CONFLICT).body("Theatre with this email already exists");
		    }

		    theatrelists.setLocations(existLocations);

		

		    String updatedPass = passwordEncoder.encode(currentPass);
		    theatrelists.setPassword(updatedPass);
		    theatrelists.setRole("ROLE_MANAGER");
		    
		   theatrelists.setLayoutstatus("Pending");

		    theatreservice.createTheatreliss(theatrelists);

		    return ResponseEntity.ok("Successfully created theatre");
		}

		@GetMapping("/getbyid/{id}")
		private Theatrelists getbyiidTheatrelists(@PathVariable int id) {
		        return theatreservice.getbyidTheatrelists(id);
		}
		
		@PutMapping("/update/{id}")
		public void updatetheatrelists(@PathVariable int id, @RequestBody Theatredto theatredto) {

	    Theatrelists theatrelists = theatredto.getTheatrelists();
			
			Locations locations = theatredto.getLocations();
			Locations existLocations = locationsrepo.findByLocation(locations.getLocation());
			
			
			theatrelists.setLocations(existLocations);
			
			theatreservice.updatetheatrelist(theatrelists, id);
		}
		
		@DeleteMapping("/delete/{id}")
		private void deletetheatrelist( @PathVariable int id) {
			theatreservice.deletetheatrelist(id);
		}
		@GetMapping("/successlist")
		private ResponseEntity<?> getalltheatres(){
			
			try {
				
				List<Theatrelists> getalltheatresList = theatrelistrepo.findBylists("Completed");
				 // Check if the list is empty and return appropriate response
		        if (getalltheatresList.isEmpty()) {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No theatres found with status Completed");
		        }
		        return ResponseEntity.ok(getalltheatresList);
			} catch (Exception e) {
				 e.printStackTrace();
			        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching theatres");
			}
			
			 
				
		
			 	 
		}
	}
	
