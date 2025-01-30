package com.example.ticketsbooking.service;

import java.util.List;

import com.example.ticketsbooking.models.Movies;

public interface Moviesint {
	
	
	List<Movies> getall();
	void Createmovie(Movies movies);
	
	Movies findMovies(int id);
	
	void Updatemovies(Movies movies,int id);
	
	void Deletemovies(int id);

}
