package com.example.ticketsbooking.controller;

import java.util.List;

import org.apache.coyote.http11.filters.VoidInputFilter;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.models.Layout;

import com.example.ticketsbooking.models.Locations;
import com.example.ticketsbooking.models.Theatrelists;
import com.example.ticketsbooking.repository.Layoutrepo;
import com.example.ticketsbooking.repository.Theatrelistrepo;
import com.example.ticketsbooking.service.Layoutint;
import com.example.ticketsbooking.service.imp.Layoutimp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@RestController
@RequestMapping("/admin/layout")
@CrossOrigin
public class Layoutcontroller {
	
	Layoutint layoutint;
	
	Theatrelistrepo theatrelistrepo;
	
	Layoutrepo layoutrepo;
	   
	@Autowired
	public Layoutcontroller(Layoutint layoutint, Theatrelistrepo theatrelistrepo, Layoutrepo layoutrepo) {
		super();
		this.layoutint = layoutint;
		this.theatrelistrepo = theatrelistrepo;
		this.layoutrepo = layoutrepo;
	}


	@PostMapping("/create/{id}")
	private void createlocation(@RequestBody Layout layoutid , @PathVariable int id) {
		
		 Theatrelists theatrelist = theatrelistrepo.findById(id)
			        .orElseThrow(() -> new RuntimeException("Theatre not found with id: " + id));

		 
		  layoutid.setTheatrelists(theatrelist);
		layoutint.createlayout(layoutid);
		
		
		
		
	}
	
	
	@PutMapping("/update/{id}")
	private void updateloaction(@RequestBody Layout layoutid,@PathVariable int id) {
		
		layoutint.updatelayoutid(layoutid, id);
	}
	
	@GetMapping("/getbyid/{id}")
	private Layout getbyidLayoutid(@PathVariable int id) {
		
		return layoutint.getbyid(id);
	}
	
	
	@DeleteMapping("/delete/{id}")
	private void deletelyout(@PathVariable int id) {
		layoutint.deletelayout(id);
	}
	
	@GetMapping("/status/{param}")
	private List<Theatrelists> getpendingtheatres(@PathVariable("param") String status){
		 List<Theatrelists> alltheatrresList= theatrelistrepo.findBylists(status);
		 
		 return alltheatrresList;
	}
	
	@GetMapping("/theatreid/{id}")
	private ResponseEntity<?> grytbytid(@PathVariable("id") int id) {
		Layout layout = layoutrepo.findByTheatrelists_Id(id);
		
		if(layout != null) {
			  return ResponseEntity.ok(layout);
		}
		else {
			return ResponseEntity.ok(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/layoutid/{id}")
	private Layout getlayout(@PathVariable("id") int id) {
		
		Layout layout = layoutrepo.findByTheatrelists_Id(id);
		
			return  layout;
			
	
	}
	

}
