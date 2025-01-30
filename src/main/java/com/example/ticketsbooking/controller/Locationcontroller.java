package com.example.ticketsbooking.controller;

import java.util.List;

import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.models.Locations;
import com.example.ticketsbooking.repository.Locationsrepo;
import com.example.ticketsbooking.service.Locationint;
import com.example.ticketsbooking.service.imp.Locationimp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin/location")
@CrossOrigin
public class Locationcontroller {
	

	   private final Locationint locationservie;
	          Locationsrepo locationsrepo;
	          
	    
	      
	      @Autowired
	  	public Locationcontroller(Locationint locationservie, Locationsrepo locationsrepo) {
				super();
				this.locationservie = locationservie;
				this.locationsrepo = locationsrepo;
			}

 
	      
	      
	@GetMapping("/getall")
	   private List<Locations> getall(){
		   return locationservie.getallLocations();
	   }
	   
	  



	@PostMapping("/create")
	   public ResponseEntity<?> Locationcreate(@RequestBody Locations locations) {
		System.out.println("location controller process start");

		Locations res = locationsrepo.findByLocation(locations.getLocation());
	
		if(res == null) {
			
			locationservie.Createlocation(locations);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}
		else {
			return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
		}
		  
	   }
	   
	   @GetMapping("/getbyid/{id}")
	   private Locations Locationgetbyid(@PathVariable int id)
	   {
		     Locations resultLocations = locationservie.getbyidlocation(id);
		     return resultLocations;
	   }
	
	   @PutMapping("/update/{id}")
	   public void updatelocation( @RequestBody Locations locations,@PathVariable int id) {
	       locationservie.Updatelocation(locations, id);
	   }

	   
	   @DeleteMapping("/delete/{id}")
	   private void locationdelete(@PathVariable int id) {
		   locationservie.DelelteLocation(id);
	   }
	   

}
