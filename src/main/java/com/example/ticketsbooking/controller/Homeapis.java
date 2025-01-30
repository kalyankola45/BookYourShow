package com.example.ticketsbooking.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.models.Locations;
import com.example.ticketsbooking.models.Movies;
import com.example.ticketsbooking.models.Seatstypeprice;
import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.Theatrelists;
import com.example.ticketsbooking.repository.Locationsrepo;
import com.example.ticketsbooking.repository.Moviesrepo;
import com.example.ticketsbooking.repository.Seattypepricerepo;
import com.example.ticketsbooking.repository.Showshisrepo;
import com.example.ticketsbooking.repository.Showsrepo;
import com.example.ticketsbooking.repository.Theatrelistrepo;
import com.example.ticketsbooking.service.Locationint;
import com.example.ticketsbooking.wrappers.Seatspricedto;
import com.example.ticketsbooking.wrappers.Theashows;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class Homeapis {
	
	   Theatrelistrepo theatrelistrepo;
	   Showsrepo showsrepo;
	   Moviesrepo moviesrepo;
	   Locationsrepo locationsrepo;
	   Showshisrepo showshisrepo;
	   PasswordEncoder passwordEncoder;
	   Seattypepricerepo seattypepricerepo;
	   	 
	  @Autowired
	 public Homeapis(Theatrelistrepo theatrelistrepo, Showsrepo showsrepo, Moviesrepo moviesrepo,
				Locationsrepo locationsrepo, Showshisrepo showshisrepo, PasswordEncoder passwordEncoder,
				Seattypepricerepo seattypepricerepo, List<Shows> allshows, List<Theatrelists> alltheatres) {
			super();
			this.theatrelistrepo = theatrelistrepo;
			this.showsrepo = showsrepo;
			this.moviesrepo = moviesrepo;
			this.locationsrepo = locationsrepo;
			this.showshisrepo = showshisrepo;
			this.passwordEncoder = passwordEncoder;
			this.seattypepricerepo = seattypepricerepo;
			this.allshows = allshows;
			this.alltheatres = alltheatres;
		}

	  
	  
	  
	  
	  
	  
	List<Shows>  allshows;



	List<Theatrelists> alltheatres;

	@GetMapping("/location/{param}")
	public  Set<Movies> getmoviesbyloc(@PathVariable("param")  String name ){
		allshows = null;
		alltheatres = null;
		System.err.println("process start");
		
		 Set<Movies> moviessSet = new HashSet<>();
		  Locations givenloc = locationsrepo.findByLocation(name);
		alltheatres = theatrelistrepo.findByLocations(givenloc);
		  
		  
		
	
		 allshows = showsrepo.findMoviesByTheatrelists(alltheatres, true);
		
		 for (Shows existedShows :  allshows) {
			 System.err.println(existedShows.getMovies().getName());
		  moviessSet.add(existedShows .getMovies());
			 
			 
			
		}
		
		
		return moviessSet;
		
	}

	@GetMapping("/moviename/{param}")
	public List<Map<String, Object>> getGroupedShowsByTheater(@PathVariable("param") String name) {
	 
		  List<Map<String, Object>> response = new ArrayList<>();
		  
		  if (allshows == null || alltheatres == null) {
		        throw new IllegalStateException("No location selected. Fetch movies by location first.");
		    }
		  
		  
		  for (Theatrelists eachtheater : alltheatres ) {
			  
			  List<Map<String, Object>> showsList = new ArrayList<>();
			  for (Shows eachshow:eachtheater.getShows()) {
				  
				  if(eachshow.getMovies().getName().toLowerCase().equals(name.toLowerCase())) {
				  Map<String, Object> showDetails = new HashMap<>();
				  
				  showDetails.put("Show_id", eachshow.getId());
				  showDetails.put("movieName", eachshow.getMovies().getName());
	                showDetails.put("showStartTime",eachshow.getShowstarttime());
	                showDetails.put("showEndTime",eachshow.getShowendtime());
	                showDetails.put("date", eachshow.getDate());
	                
	                showsList.add(showDetails);
				  }
				  
				
			}
			  if(!showsList.isEmpty()) {
				  Map<String, Object> theaterDetails = new LinkedHashMap<>();
				  theaterDetails.put("thearename", eachtheater.getTheatrename());
				  theaterDetails.put("theatreadress", eachtheater.getAdress());
				  theaterDetails.put("Shows", showsList);
				  response.add(theaterDetails);
			  }
			
		}
		  
		  
		 
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  return response;
	}

	
	@GetMapping("/theatres/{param}")
	public ResponseEntity<List<Theatrelists>> getMethodName(@PathVariable("param") String name) {
		System.out.println("process start");

		  Locations givenloc = locationsrepo.findByLocation(name);
		  System.out.println(givenloc.toString());

			List<Theatrelists> byloca = theatrelistrepo.findByLocations(givenloc);
			List<Theatrelists> filteredlist = new ArrayList<>();
		  
		return  ResponseEntity.ok(byloca);
	}
	
    
	@GetMapping("/locations/getall")
	   private List<Locations> getall(){
		  List<Locations> getalllocations = locationsrepo.findAll();
		 
		
		  
		  return getalllocations;
	   }
	
	

 @GetMapping("/allmovies")
 public List<Movies> allmovies() {
     List<Movies> allMovies = moviesrepo.findAll();
     return allMovies;
 }
 

 @GetMapping("getseatprice/{id}")
 private List<Seatspricedto> Findbypricemethod(@PathVariable int id){
	
   List<Seatstypeprice> getall = seattypepricerepo.findByshowid(id);
   
   List<Seatspricedto> result = new ArrayList<>();
   
   for (Seatstypeprice seatsprice : getall) {
	   Seatspricedto Eachone = new Seatspricedto();
	  Eachone.setPrice(seatsprice.getPrice());
	  Eachone.setSeatid(seatsprice.getSeattypes().getId());
	  Eachone.setShowid(seatsprice.getShows().getId());
	  result.add(Eachone);
	
}
   
   return result;
}

}
