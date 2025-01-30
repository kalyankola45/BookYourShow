package com.example.ticketsbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.ticketsbooking.models.Movies;

import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Theatreconstruction;
import com.example.ticketsbooking.service.Seattypeint;

@RestController
@RequestMapping("/admin/seattype")
@CrossOrigin
public class Seattypecontroller {

	
	Seattypeint seattypeint;

	
	@Autowired
	public Seattypecontroller(Seattypeint seattypeint) {
		super();
		this.seattypeint = seattypeint;
	}
	
	@GetMapping("getall/{id}")
	private List<Seattypes> getallMovies(@PathVariable int id){
		return seattypeint.getall(id);
	}

	
	@PostMapping("/create")
	private ResponseEntity<String> createmovie(@RequestBody Seattypes seattypes) {
		
		 seattypeint.createseattype(seattypes);
	   

	    return ResponseEntity.ok("Successfully created");
	}

	@GetMapping("/getbyid/{id}")
	private Seattypes getbyid(@PathVariable int id) {
		return seattypeint.findseattype(id);
	}
	
	@PutMapping("update/{id}")
	public void updateMovies(@PathVariable int id, @RequestBody Seattypes seattypes) {
		seattypeint.Updateseattype(seattypes, id);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public void Deletebyid(@PathVariable int id) {
seattypeint.DeleteSeattype(id);
	}
	
	@PostMapping("/total/{id}")
	public ResponseEntity<?> postMethodName(@PathVariable int id) {
		
		seattypeint.addtotalseats(id);
		
		 return ResponseEntity.ok("hi");
		
		
		
		
		
		
	}

	
	
}
