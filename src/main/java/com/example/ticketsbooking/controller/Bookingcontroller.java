package com.example.ticketsbooking.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.example.ticketsbooking.models.BookingSeats;
import com.example.ticketsbooking.models.Layout;
import com.example.ticketsbooking.models.Seatstypeprice;
import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.Theatreconstruction;
import com.example.ticketsbooking.models.Transectiondetails;
import com.example.ticketsbooking.repository.Bookingseatsrepo;
import com.example.ticketsbooking.repository.Layoutrepo;
import com.example.ticketsbooking.repository.Seattypepricerepo;
import com.example.ticketsbooking.repository.Showsrepo;
import com.example.ticketsbooking.repository.Theatrelayoutrepo;
import com.example.ticketsbooking.repository.Transactionrepo;
import com.example.ticketsbooking.wrappers.Bookingseatdto;
import com.example.ticketsbooking.wrappers.RidCode;
import com.razorpay.RazorpayException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user/booking")
@CrossOrigin(origins = "http://localhost:3000") 
public class Bookingcontroller {

	
	          Bookingseatsrepo bookingseatsrepo;
	          Showsrepo showsrepo;
	          Seattypepricerepo seattypepricerepo;
	          Theatrelayoutrepo theatrelayoutrepo;
	          Layoutrepo layoutrepo;
	          Transectioncontroller transectioncontroller;
	          Transactionrepo transactionrepo;
	          
                     @Autowired
			public Bookingcontroller(Bookingseatsrepo bookingseatsrepo, Showsrepo showsrepo,
					Seattypepricerepo seattypepricerepo, Theatrelayoutrepo theatrelayoutrepo, Layoutrepo layoutrepo,
					Transectioncontroller transectioncontroller, Transactionrepo transactionrepo) {
				super();
				this.bookingseatsrepo = bookingseatsrepo;
				this.showsrepo = showsrepo;
				this.seattypepricerepo = seattypepricerepo;
				this.theatrelayoutrepo = theatrelayoutrepo;
				this.layoutrepo = layoutrepo;
				this.transectioncontroller = transectioncontroller;
				this.transactionrepo = transactionrepo;
			}




			@PostMapping("/seats")
			public ResponseEntity<?> bookSeats(@RequestBody Bookingseatdto bookingseatdto) {
			    
				System.out.println("process start");

			    // Step 1: Retrieve show and booking information
			    Shows bookingShows = bookingseatdto.getBookingSeats().get(0).getShows();
			    List<BookingSeats> bookingSeatsFromUser = bookingseatdto.getBookingSeats();
			    Layout layout = layoutrepo.findByTheatrelists_Id(bookingseatdto.getTheatreid());

			    // Step 2: Validate show status
			    int showId = bookingShows.getId();
			    int totalTickets = bookingseatdto.getTotalseats();
			    Shows showFromDatabase = showsrepo.findById(showId);
			    if (showFromDatabase == null || !showFromDatabase.getBookingstatus()) {
			        return ResponseEntity.ok("Show time over");
			    }

			    // Step 3: Retrieve booked seats and store them in a set
			    List<BookingSeats> bookedSeatsFromShow = bookingseatsrepo.findByShows(showFromDatabase);
			  String codeString=    Validseats(bookedSeatsFromShow,bookingSeatsFromUser,layout,showFromDatabase,bookingShows);
			    
			  if(codeString == null) {
				  return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

			  }
			  else {
				  return ResponseEntity.ok(codeString);
			} 
			}



             
             private String Validseats(List<BookingSeats> bookedSeatsFromShow, List<BookingSeats> bookingSeatsFromUser,
					Layout layout, Shows showFromDatabase, Shows bookingShows) {
            	 Set<String> bookedSeatsSet = new HashSet<>();
 			    for (BookingSeats seat : bookedSeatsFromShow) {
 			        bookedSeatsSet.add(seat.getRowname() + "-" + seat.getSeatcolumn());
 			    }

 			    String[] conformationCode = {
 		        	    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", 
 		        	    "1", "2", "3", "4", "5", "6", "7", "8", "9"
 		        	};
 		        
 		        
 		      final  String returncode=generatecode(conformationCode); ;
 		      
 		  
 		     
 			    // Step 4: Seat booking logic
 			    for (BookingSeats userSeat : bookingSeatsFromUser) {
 			        String seatKey = userSeat.getRowname() + "-" + userSeat.getSeatcolumn();

 			        // Check if the seat is already booked
 			        if (bookedSeatsSet.contains(seatKey)) {
 			            
 			        	throw new RuntimeException("something went wrong");
 			              	
 			        }

 			        // Get seat details from Theatreconstruction
 			        Theatreconstruction seatInfo = theatrelayoutrepo.findByLayoutid_IdAndRowname(layout.getId(), userSeat.getRowname());
 			        int seatColumn = userSeat.getSeatcolumn();

 			        // Validate seat gaps
 			        int[] gaps = Arrays.stream(seatInfo.getGaps().split(","))
 			                           .mapToInt(Integer::parseInt)
 			                           .toArray();
 			        for (int gap : gaps) {
 			            if (gap == seatColumn) {
 			                System.out.println("Seat " + seatColumn + " is in the gaps.");
 			               throw new RuntimeException("Your selected seat is in the gap.");
 			            }
 			        }

 			        // Validate seat column range
 			        if (seatColumn < seatInfo.getColumnstart() || seatColumn > seatInfo.getColumnend()) {
 			            throw new RuntimeException("Seat column " + seatColumn + " is out of range.");
 			        }

 			      

 			         
 			        // Fetch seat price
 			        
 			        int seatTypeId = userSeat.getSeattype();
 			        System.out.println("give seattype id"+seatTypeId);

 			        Seatstypeprice seatPrice = seattypepricerepo.findByShowsAndSeattypesId(bookingShows, seatTypeId);
 			        System.err.println("seatprice"+seatPrice);
 			        if (seatPrice == null) {
 			            throw new RuntimeException("Invalid seat type or price not found.");
 			        }

 			        // Save booking details
 			        BookingSeats newSeat = new BookingSeats();
 			        newSeat.setShows(showFromDatabase);
 			        newSeat.setRowname(userSeat.getRowname());
 			        newSeat.setSeatcolumn(seatColumn);
 			        newSeat.setPrice(seatPrice.getPrice());
 			          newSeat.setSeattype(seatTypeId);
 			          newSeat.setBookingcode(returncode);
 			         
 			        bookingseatsrepo.save(newSeat);
 			        System.out.println("Saved seat with booking code: " + newSeat.getBookingcode());
 			        bookedSeatsSet.add(seatKey);
 			        
 			        
 			      
 			        
 			        
 			    }
              return   returncode;
			}




			@PostMapping("/payment/{code}")
			private ResponseEntity<?> Tocalltransecion(@PathVariable("code")  String returncode) throws RazorpayException {
			   
				List<BookingSeats> nooftickets = bookingseatsrepo.findByBookingcode(returncode);
		    	int showid = nooftickets.get(0).getShows().getId();
		    	Shows givenShows = showsrepo.findById(showid);
		    
		    	int totaltickpric = 0;
		    	
		    	   for(BookingSeats mine:nooftickets) {
		    		   		    		     
		    		     totaltickpric += mine.getPrice();
		    		   
		    		     
		    	   }
		    	   Transectiondetails newtraTransectiondetails =  new Transectiondetails();
		    	   newtraTransectiondetails.setAmount(totaltickpric);
		    	   newtraTransectiondetails.setBookingSeats(nooftickets);
		    	   newtraTransectiondetails.setEmail("Kalyannaidu018@gmail.com");
		    	   
		      ResponseEntity<?> resulted= 	   transectioncontroller.addpayment(newtraTransectiondetails);
		    	   
		    	        return  resulted;
		    			   
		    	   

			}

         
             
             
     		@PostMapping("/failure")
        
     		
     		



             private static String generatecode(String[] conformationCode) {
            	    StringBuilder retrunncode = new StringBuilder();
            	    Random random = new Random();
            	    for (int m = 0; m < 6; m++) {
            	        int index = random.nextInt(conformationCode.length);
            	        retrunncode.append(conformationCode[index]);
            	    }
            	    return retrunncode.toString();
            	}


	          
	          
	          
}
