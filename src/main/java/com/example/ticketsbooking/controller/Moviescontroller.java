package com.example.ticketsbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.models.Movies;
import com.example.ticketsbooking.service.imp.Movieimp;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("admin/movies")
@CrossOrigin
public class Moviescontroller {
	
	  Movieimp movieimp;
	  
	@Autowired
	public Moviescontroller(Movieimp movieimp) {
		super();
		this.movieimp = movieimp;
	};

	@GetMapping("/getall")
	
	private List<Movies> getallMovies(){
		return movieimp.getall();
	}

	@PostMapping("/create")
	private ResponseEntity<String>  createmovie(@RequestBody Movies movies) {
		
		movieimp.Createmovie(movies);
		return   ResponseEntity.ok("successfully created");
		
	}
	
	@GetMapping("/getbyid/{id}")
	private Movies getbyid(@PathVariable int id) {
		System.err.println("movie get by id process start");
		return movieimp.findMovies(id);
	}
	
	@PutMapping("/update/{id}")
	public void updateMovies(@PathVariable int id, @RequestBody Movies movies) {
		movieimp.Updatemovies(movies, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public void Deletebyid(@PathVariable int id) {
		movieimp.Deletemovies(id);
	}
}
