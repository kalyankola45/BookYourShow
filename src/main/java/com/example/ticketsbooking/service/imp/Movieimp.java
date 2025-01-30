package com.example.ticketsbooking.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.ticketsbooking.models.Movies;
import com.example.ticketsbooking.repository.Moviesrepo;
import com.example.ticketsbooking.service.Moviesint;


@Service
public class Movieimp  implements Moviesint{
	
	Moviesrepo moviesrepo;
	
	

	@Autowired
	public Movieimp(Moviesrepo moviesrepo) {
		super();
		this.moviesrepo = moviesrepo;
	}

	@Override
	public void Createmovie(Movies movies) {
		System.out.println(movies.toString());

		moviesrepo.save(movies);
	
	}

	@Override
	public Movies findMovies(int ids) {
		Movies resultMovies= moviesrepo.findById(ids).orElseThrow(null);
		return resultMovies;
	}

	@Override
	public void Updatemovies(@RequestBody Movies movies,int id) {
		System.out.println("update process start");

           Movies existmovie = moviesrepo.findById(id).orElseThrow(null);
           
          existmovie.setImage(movies.getImage());
          existmovie.setName(movies.getName());
          existmovie.setRuntime(movies.getRuntime());
          existmovie.setDescription(movies.getDescription());
          existmovie.setTrailerurl(movies.getTrailerurl());
          existmovie.setReleasedate(movies.getReleasedate());
          
          moviesrepo.save(existmovie);
          
  
	}

	@Override
	public void Deletemovies(int id) {
		moviesrepo.deleteById(id);
		
	}

	@Override
	public List<Movies> getall() {
	    List<Movies> allmovies = moviesrepo.findAll();
	    
	    return allmovies;
	}

}
