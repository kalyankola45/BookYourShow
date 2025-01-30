package com.example.ticketsbooking.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.models.Bookedseats;
import com.example.ticketsbooking.models.BookingSeats;
import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.ShowsHistory;
import com.example.ticketsbooking.models.Theatrelists;
import com.example.ticketsbooking.models.Transectiondetails;
import com.example.ticketsbooking.repository.Bookedseatsrepo;
import com.example.ticketsbooking.repository.Bookingseatsrepo;
import com.example.ticketsbooking.repository.Showsrepo;
import com.example.ticketsbooking.repository.Transactionrepo;
import com.example.ticketsbooking.wrappers.Bookingseatdto;
import com.example.ticketsbooking.wrappers.RidCode;

@RestController
@RequestMapping("/user/booked")
@CrossOrigin
public class Bookedcontroller {

	
	Bookingseatsrepo bookingseatsrepo;
	Bookedseatsrepo bookedseatsrepo;
	Showsrepo showsrepo;
	Showshistorycontroller showshistorycontroller;
	Transactionrepo transactionrepo;
	           
            @Autowired
  		public Bookedcontroller(Bookingseatsrepo bookingseatsrepo, Bookedseatsrepo bookedseatsrepo,
					Showsrepo showsrepo, Showshistorycontroller showshistorycontroller,
					Transactionrepo transactionrepo) {
				super();
				this.bookingseatsrepo = bookingseatsrepo;
				this.bookedseatsrepo = bookedseatsrepo;
				this.showsrepo = showsrepo;
				this.showshistorycontroller = showshistorycontroller;
				this.transactionrepo = transactionrepo;
			}

		@PostMapping("/confirm/{code}")
     private ResponseEntity<?> codeconfirm(@PathVariable("code") String code){
        	   
			

        	 Bookedseats existBookedseats = bookedseatsrepo.findByConfirmationcode(code);
        	 
        	 if(existBookedseats == null) {
        		   return ResponseEntity.badRequest().body("code doesn't exist");
        	 }
        	 
        	 if(!existBookedseats.isTicketstatus()) {
        		 
        		 return ResponseEntity.status(400).body("invalid code");
        		 
        	 }
        	 else {
				 existBookedseats.setTicketstatus(false);
				 bookedseatsrepo.save(existBookedseats);
				 return ResponseEntity.status(200).body("valid code");
			}
         };
   


	



		@PostMapping("/success")
	  private ResponseEntity<String> addbooking(@RequestBody RidCode rid){
	    	
			System.err.println("success procces start");
			String code = rid.getCode();
			String paymd = rid.getRid();
			
			Transectiondetails existtransection = transactionrepo.findByRzorpayid(paymd);
 			
 			existtransection.setPaymentstatus("SUCCESS");
 			
 			transactionrepo.save(existtransection);
 			
			
 	    	boolean result = bookedseatsrepo.existsByconfirmationcode(code);
	    	if(!result) {
	    	List<BookingSeats> nooftickets = bookingseatsrepo.findByBookingcode(code);
	    	int showid = nooftickets.get(0).getShows().getId();
	    	Shows givenShows = showsrepo.findById(showid);
	    	String rowname = "";
	    	String columns = " ";
	    	int totaltickpric = 0;
	    	String  totaltuc = " ";
	    	   for(BookingSeats mine:nooftickets) {
	    		   
	    		     rowname += mine.getRowname();
	    		   
	    		     columns +=  String.valueOf(mine.getSeatcolumn());
	    		     
	    		     totaltickpric += mine.getPrice();
	    		     totaltuc += rowname+columns +",";
	    		     System.err.println(totaltuc);
	    		      rowname = "";
	    		    	 columns = " ";
	    		     
	    	   }
	    	   LocalDate currentDate = LocalDate.now();
	    	   LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);;
	    		System.err.println(currentTime);
	    	   System.out.println("rowname"+rowname);
	    	   System.err.println("columns" + columns);
	    	   Bookedseats settingBookedseats = new Bookedseats();
	    	   
	    	   settingBookedseats.setConfirmationcode(code);
	    	   Theatrelists givetheate = givenShows.getTheatrelists();
	    	   if (givetheate == null) {
	    		    return ResponseEntity.badRequest().body("Theatre information is missing.");
	    		}
	    	   settingBookedseats.setTheatrelists(givetheate);
	    	   
	    	   settingBookedseats.setLocalDate(String.valueOf(currentDate));
	    	    settingBookedseats.setTicket(totaltuc);
	    	   settingBookedseats.setShows(nooftickets.get(0).getShows());
	    	   settingBookedseats.setTicketstatus(true);
	    	  	    	   settingBookedseats.setTotalprice(totaltickpric);
	    	   settingBookedseats.setTotaltickets(nooftickets.size());
	    	   settingBookedseats.setLocalTime(currentTime);
	    	   
	    	  settingBookedseats.setTransectiondetails(existtransection);
	    	   bookedseatsrepo.save(settingBookedseats);
	    	   showshistorycontroller.calculateshow(showid);
	    	   
	    	   
	    	   
	    			return ResponseEntity.ok("successfully added");
	    			
	    	}
	    	else {
				 return ResponseEntity.ok("already exist");
			}
	    	 	
	    	
	 
	    	
	    }


		 @PostMapping("/failure")
	    private void failure(@RequestBody RidCode ridCode) {
 			
 			System.err.println("failure process start");
 			
 			String code = ridCode.getCode();
 			
 			String paymenid = ridCode.getRid();
 			
 			Transectiondetails existtransection = transactionrepo.findByRzorpayid(paymenid);
 			
 			existtransection.setPaymentstatus("FAILED");
 			
 			transactionrepo.save(existtransection);
 			
 			List<BookingSeats> existBookingSeats = bookingseatsrepo.findByBookingcode(code);
 			
 			for (BookingSeats bookingSeats : existBookingSeats) {
 				
 				bookingseatsrepo.delete(bookingSeats);
				
			}
 			
 			
 			
     	   
        }

}
