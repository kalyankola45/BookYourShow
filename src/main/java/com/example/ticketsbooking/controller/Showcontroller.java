package com.example.ticketsbooking.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.coyote.http11.filters.VoidInputFilter;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.models.Layout;
import com.example.ticketsbooking.models.Movies;
import com.example.ticketsbooking.models.Seatstypeprice;
import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.Theatrelists;
import com.example.ticketsbooking.repository.Layoutrepo;
import com.example.ticketsbooking.repository.Seattypepricerepo;
import com.example.ticketsbooking.repository.Seattyperepo;
import com.example.ticketsbooking.repository.Showshisrepo;
import com.example.ticketsbooking.repository.Showsrepo;
import com.example.ticketsbooking.repository.Theatrelistrepo;
import com.example.ticketsbooking.security.JwtProvider;
import com.example.ticketsbooking.security.Jwtconstant;
import com.example.ticketsbooking.service.Showsint;
import com.example.ticketsbooking.wrappers.ShowsDto;
import com.razorpay.Item;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/management/show")
@CrossOrigin
public class Showcontroller {
 
	
	Showsint showservice;
	Showsrepo showsrepo;
	Theatrelistrepo theatrelistrepo;
	Seattypepricerepo seattypepricerepo;
	Layoutrepo layoutrepo;
	Seattyperepo seattyperepo;
Showshistorycontroller showshistorycontroller;	
  Showstatusscheduler showstatusscheduler;
  JwtProvider jwtProvider;
  Showshisrepo showshisrepo;
  
       
       @Autowired
       public Showcontroller(Showsint showservice, Showsrepo showsrepo, Theatrelistrepo theatrelistrepo,
   			Seattypepricerepo seattypepricerepo, Layoutrepo layoutrepo, Seattyperepo seattyperepo,
   			Showshistorycontroller showshistorycontroller, Showstatusscheduler showstatusscheduler,
   			JwtProvider jwtProvider) {
   		super();
   		this.showservice = showservice;
   		this.showsrepo = showsrepo;
   		this.theatrelistrepo = theatrelistrepo;
   		this.seattypepricerepo = seattypepricerepo;
   		this.layoutrepo = layoutrepo;
   		this.seattyperepo = seattyperepo;
   		this.showshistorycontroller = showshistorycontroller;
   		this.showstatusscheduler = showstatusscheduler;
   		this.jwtProvider = jwtProvider;
   	}



		    @PostMapping("/create")
		    public ResponseEntity<?> createShow(HttpServletRequest request, @RequestBody ShowsDto showsDto) throws SchedulerException {
		        System.out.println("Process start of show");

		        // Extract email from JWT token
		        String jwtString = request.getHeader(Jwtconstant.JWTHEADER);
		        String tokenString = jwtString.substring(6);
		        String emailString = jwtProvider.getemailfromtoken(tokenString);

		        // Get theater and associated data
		        Theatrelists theatrelists = theatrelistrepo.findByEmail(emailString);
		        Shows newShow = showsDto.getShows();
		        List<Seatstypeprice> Givenseattypes = showsDto.getSeatstypeprice();
		        newShow.setTheatrelists(theatrelists);

		        // Parse and validate date and time
		        LocalDate newShowDate = LocalDate.parse(newShow.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		        LocalTime newShowStartTime = LocalTime.parse(newShow.getShowstarttime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
		        LocalDate currentDate = LocalDate.now();
		        LocalTime currentTime = LocalTime.now().withNano(0);

		        if (newShowDate.isBefore(currentDate) || (newShowDate.isEqual(currentDate) && currentTime.isAfter(newShowStartTime))) {
		            return ResponseEntity.ok("Incorrect date or time");
		        }

		        List<Shows> showsOnTheDate = showsrepo.findByTheatrelistsAndDate(theatrelists, newShow.getDate());
		         validateShowTiming(newShow, showsOnTheDate);

		        try {
		            // Save the show and process seat prices
		            int showId = showservice.Createshow(newShow);
		            
		            pricessaddedprocess(showsDto.getSeatstypeprice(), theatrelists, showId);

		            // Additional tasks
		            showshistorycontroller.calculateshow(showId);
		            showstatusscheduler.scheduleShowTasks(newShow);

		            return ResponseEntity.ok("Successfully added the show!");
		        } catch (DataIntegrityViolationException e) {
		            throw new RuntimeException("Duplicate entry detected for the given seat type and show.", e);
		        }
		    }


		    private void validateShowTiming(Shows newShow, List<Shows> existingShows) {
		        LocalDate newShowDate = LocalDate.parse(newShow.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		        LocalTime newShowStartTime = LocalTime.parse(newShow.getShowstarttime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
		        LocalTime newShowEndTime = LocalTime.parse(newShow.getShowendtime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
		        LocalDate currentDate = LocalDate.now();
		        LocalTime currentTime = LocalTime.now().withNano(0);
		        
		       

		        // Check if the show date is in the past
		        if (newShowDate.isBefore(currentDate)) {
		            throw new RuntimeException("Show date cannot be in the past.");
		        }

		        // Check if the show start time is in the past on the same day
		        if (newShowDate.isEqual(currentDate) && currentTime.isAfter(newShowStartTime)) {
		            throw new RuntimeException("Show start time cannot be in the past.");
		        }
		        
		            	
		         	      
		       for (Shows existShow : existingShows) {
		    	   if(newShow.getId() != existShow.getId()) {
		           LocalTime existingEndTime = LocalTime.parse(existShow.getShowendtime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                   
		           LocalTime existingStartTime = LocalTime.parse(existShow.getShowstarttime(),DateTimeFormatter.ofPattern("HH:mm:ss"));
		           
		           boolean overlap = 
		                   (newShowStartTime.isBefore(existingEndTime) && newShowEndTime.isAfter(existingStartTime));
		               
		               if (overlap) {
		                   throw new RuntimeException("New show timings overlap with existing show timings.");
		               }
		    	   }
		       }
		        


		        
		    }

		    
				
				
		    private void validateShowAttributes(Shows newShow) {
		        if (newShow.getMovies() == null) {
		            throw new RuntimeException("Movie must be selected for the show.");
		        }

		        if (newShow.getShowstarttime() == null || newShow.getShowendtime() == null) {
		            throw new RuntimeException("Show start time and end time must be provided.");
		        }

		        if (newShow.getSeatstypeprices().isEmpty()) {
		            throw new RuntimeException("At least one seat type price must be provided.");
		        }

		        for (Seatstypeprice seatstypeprice : newShow.getSeatstypeprices()) {
		            if (seatstypeprice.getPrice() <= 0) {
		                throw new RuntimeException("Seat price must be greater than zero.");
		            }
		        }
		    }
		    
		    @Transactional
		    private void pricessaddedprocess(List<Seatstypeprice> seatstypepricefromuser, Theatrelists theatrelists, int Showid) {
		        
		    	  try {
		    	     Layout existLayout = layoutrepo.findByTheatrelists(theatrelists);
		    	     List<Seattypes> existSeattypes = seattyperepo.findByLayout_Id(existLayout.getId());
		    	     	
		    	     Shows exisShow = showsrepo.findById(Showid);
		    	     
		    	     for (Seatstypeprice givenSeatTypePrice : seatstypepricefromuser) {
		    	    	 
		    	    	 Seattypes result = existSeattypes.stream()
		    	    			                          .filter(t ->  t.getId() == givenSeatTypePrice.getId() )
		    	    			                          .findFirst()
		    	    			                          .orElse(null);
		    	    	 Seatstypeprice newSeatTypePrice = new Seatstypeprice();
		                 newSeatTypePrice.setPrice(givenSeatTypePrice.getPrice());
		                 newSeatTypePrice.setSeattypes(result);
		                 newSeatTypePrice.setShows(exisShow);
		                 
		                 // Save the new Seatstypeprice object
		                 seattypepricerepo.save(newSeatTypePrice);
		    	    			         
						
					}

		              	                
		            } catch (Exception e) {
		                System.err.println("Error saving Seatstypeprice: " + e.getMessage());
		                throw e;
		            }
		        }

		       
		    

			@GetMapping("/getbyid/{id}")
            public Shows getbyidShows(@PathVariable int id) {
            	return showservice.getbyidShows(id);
            }
            
            
            @PutMapping("/update/{id}")
            public void updateshow(HttpServletRequest request,@RequestBody ShowsDto showsDto , @PathVariable("id") int ids) {
            	
            	
            	 String jwtString = request.getHeader(Jwtconstant.JWTHEADER);
    		     String tokenString = jwtString.substring(6);
    		     String emailString = jwtProvider.getemailfromtoken(tokenString);
    		     
            	 Shows UpdatetingShow = showsDto.getShows();
            	 List<Seatstypeprice> currentseatypeprices = showsDto.getSeatstypeprice();
            	
                       
            	 Shows existShow = showsrepo.findById(ids);
            	 
            	 existShow.setBookingstatus(UpdatetingShow.getBookingstatus());
            	 
            	 showsrepo.save(existShow);
  		        
            	 List<Seatstypeprice> existseattypeprices = seattypepricerepo.findByshowid(ids);
            	 if (existseattypeprices == null || existseattypeprices.isEmpty()) {
            	        System.out.println("No Seatstypeprice records found for this show.");
            	 }
            	 int index = 0;
               for (Seatstypeprice currentt : currentseatypeprices) {
            	   
            	   existseattypeprices.get(index++).setPrice(currentt.getPrice());
            	   
            	 
            	
					}
               seattypepricerepo.saveAll(existseattypeprices);
            	
            	 
            	
            	  
            }
            
            @DeleteMapping("/delete/{id}")
            public void deleteshow(@PathVariable("id") int idd) {
            	List<Seatstypeprice> Existseattypeprices = seattypepricerepo.findByshowid(idd);
            	Shows existShows = showsrepo.findById(idd);
            	for (Seatstypeprice seatstypeprice : Existseattypeprices) {
					seattypepricerepo.deleteById(seatstypeprice.getId());
				}
            
            	showservice.Deleteshow(idd);
            }
       
     
//          @GetMapping("/getallshows")
//          public List<Shows> getallShows (){
//        	  
//          }
	
	
	
	 
}
