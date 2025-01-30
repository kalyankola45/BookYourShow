package com.example.ticketsbooking.controller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.models.Bookedseats;
import com.example.ticketsbooking.models.BookingSeats;
import com.example.ticketsbooking.models.Layout;
import com.example.ticketsbooking.models.Seatstypeprice;
import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.ShowsHistory;
import com.example.ticketsbooking.models.Theatreconstruction;
import com.example.ticketsbooking.models.Theatrelists;
import com.example.ticketsbooking.models.Transectiondetails;
import com.example.ticketsbooking.repository.Bookedseatsrepo;
import com.example.ticketsbooking.repository.Bookingseatsrepo;
import com.example.ticketsbooking.repository.Layoutrepo;
import com.example.ticketsbooking.repository.Seattypepricerepo;
import com.example.ticketsbooking.repository.Seattyperepo;
import com.example.ticketsbooking.repository.Showshisrepo;
import com.example.ticketsbooking.repository.Showsrepo;
import com.example.ticketsbooking.repository.Theatrelayoutrepo;
import com.example.ticketsbooking.repository.Theatrelistrepo;
import com.example.ticketsbooking.security.JwtProvider;
import com.example.ticketsbooking.security.Jwtconstant;
import com.example.ticketsbooking.security.Jwtfilter;
import com.example.ticketsbooking.service.Layoutint;
import com.example.ticketsbooking.service.Seattypeint;
import com.example.ticketsbooking.service.Theatrelistint;
import com.example.ticketsbooking.wrappers.BdataWrapper;
import com.example.ticketsbooking.wrappers.Bookingseatdto;
import com.example.ticketsbooking.wrappers.Managementwrapper;
import com.example.ticketsbooking.wrappers.Showwiththedto;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/management")
@CrossOrigin
public class Managerapis {
                     
	Theatrelistint theatrelistint;
	  Seattyperepo seattyperepo;
	  Layoutrepo layoutrepo;
	   JwtProvider jwtProvider;
	   Showsrepo showsrepo;
       Bookingcontroller bookingcontroller;
	   Theatrelistrepo theatrelistrepo;
	   Showshisrepo showshisrepo;
	   Seattypepricerepo seattypepricerepo;
	   	Theatrelayoutrepo theatrelayoutrepo;
	   	Bookingseatsrepo bookingseatsrepo;
	   	Bookedseatsrepo bookedseatsrepo;
	   	Showshistorycontroller showshistorycontroller;
	   	
	   
     @Autowired
	public Managerapis(Theatrelistint theatrelistint, Seattyperepo seattyperepo, Layoutrepo layoutrepo,
			JwtProvider jwtProvider, Showsrepo showsrepo, Bookingcontroller bookingcontroller,
			Theatrelistrepo theatrelistrepo, Showshisrepo showshisrepo, Seattypepricerepo seattypepricerepo,
			Theatrelayoutrepo theatrelayoutrepo, Bookingseatsrepo bookingseatsrepo, Bookedseatsrepo bookedseatsrepo,
			Showshistorycontroller showshistorycontroller) {
		super();
		this.theatrelistint = theatrelistint;
		this.seattyperepo = seattyperepo;
		this.layoutrepo = layoutrepo;
		this.jwtProvider = jwtProvider;
		this.showsrepo = showsrepo;
		this.bookingcontroller = bookingcontroller;
		this.theatrelistrepo = theatrelistrepo;
		this.showshisrepo = showshisrepo;
		this.seattypepricerepo = seattypepricerepo;
		this.theatrelayoutrepo = theatrelayoutrepo;
		this.bookingseatsrepo = bookingseatsrepo;
		this.bookedseatsrepo = bookedseatsrepo;
		this.showshistorycontroller = showshistorycontroller;
	}


	@PostMapping("/getseattypes")
	   private List<Seattypes> gettheatre(@RequestBody String token) {
		
		System.err.println("get seattype  process start");
		String email = jwtProvider.getemailfromtoken(token);
		
		Theatrelists theatrelists = theatrelistrepo.findByEmail(email);
		Layout layout = layoutrepo.findByTheatrelists(theatrelists);
		 List<Seattypes> givenseattype=seattyperepo.findByLayout_Id(layout.getId());
		
		 
		 return givenseattype;
		
		
		   
	   }
	   
	 






	@GetMapping("/layoutdata")
	private ResponseEntity<Map<String, List<Theatreconstruction>>> getallseatsdata(HttpServletRequest request){
		 String jwtString = request.getHeader(Jwtconstant.JWTHEADER);
		 String tookenString = jwtString.substring(6);
		    	 
		    	 String emailString  = jwtProvider.getemailfromtoken(tookenString);
		    	 
		    	 Theatrelists theatrelists = theatrelistrepo.findByEmail(emailString);
		    	 
		
		

		Layout giveLayout = layoutrepo.findByTheatrelists(theatrelists);
		
		if(giveLayout == null) {
			 return  (ResponseEntity<Map<String, List<Theatreconstruction>>>) ResponseEntity.notFound();
			
		}
		List<Seattypes> givenSeattypes = theatrelayoutrepo.findseattypesbylayout(giveLayout);
		
		Map<String, List<Theatreconstruction>> seatsdata = new  LinkedHashMap<>();
		
		for (Seattypes seattypes : givenSeattypes) {
			System.out.println(seattypes.toString());

			List<Theatreconstruction> fetseats = theatrelayoutrepo.findSeatsbytype(seattypes.getId());
			seatsdata.put(seattypes.getSeattypename(), fetseats);
		}
		
		
		if(seatsdata != null) {
			return ResponseEntity.ok(seatsdata);
		}
		else {
			  return  (ResponseEntity<Map<String, List<Theatreconstruction>>>) ResponseEntity.notFound();
		
	}
   

	}
	@GetMapping("getlayout")
	private Layout FindLayout(HttpServletRequest request) {
		 String jwtString = request.getHeader(Jwtconstant.JWTHEADER);
 String tookenString = jwtString.substring(6);
    	 
    	 String emailString  = jwtProvider.getemailfromtoken(tookenString);
    	 
    	 Theatrelists theatrelists = theatrelistrepo.findByEmail(emailString);
    	 
    	 Layout Givenlayoout = layoutrepo.findByTheatrelists(theatrelists);
    	 
    	 return Givenlayoout;
    	 
		
	}
	LocalDate totdayDate = LocalDate.now();
	@GetMapping("/getallshows")
	private  Map<String, List<Showwiththedto>> getallshows(HttpServletRequest request){
		 String jwtString = request.getHeader(Jwtconstant.JWTHEADER);
    	 
    	 String tookenString = jwtString.substring(6);
    	 
    	 String emailString  = jwtProvider.getemailfromtoken(tookenString);
    	 
    	 Theatrelists theatrelists = theatrelistrepo.findByEmail(emailString);
    	 List<Shows> getallShows = showsrepo.findByTheatrelists(theatrelists);
    	 
    	 Map<String, List<Showwiththedto>> filter = new LinkedHashMap<>();
    	 
    	 for (Shows eachshow : getallShows) {
    		
    		 String showdate = eachshow.getDate();
    		Showwiththedto resultone = new Showwiththedto();
    		
    		resultone.setDate(showdate);
    		
    		resultone.setMovieName(eachshow.getMovies().getName());
    		
    		List<Integer> seatprices = seattypepricerepo.findseatprices(eachshow.getId());
    		
    		ShowsHistory giveShowsHistory = showshisrepo.findByShows(eachshow);
    		
    		resultone.setSeatprices(seatprices);
    		
    		resultone.setShowEndTime(eachshow.getShowendtime());
    		
    		resultone.setShowId(eachshow.getId());
    		
    		resultone.setBookingstatus(eachshow.getBookingstatus());
    		
    		resultone.setShowsHistory(giveShowsHistory);
    		resultone.setShowStartTime(eachshow.getShowstarttime());
    		
    		
    		if(filter.containsKey(showdate)) {
    			filter.get(showdate).add(resultone);
    		}
    		else {
				List<Showwiththedto> newshowList = new ArrayList<>();
				newshowList.add(resultone);
				filter.put(showdate, newshowList);
			}
    		
    		 
    		 
    		 
		}
		
		
    	 
    	 return filter;
		
	}
	   
	   @GetMapping("/bookeddata/{id}")
	private List<BdataWrapper> getallbookddata(@PathVariable("id") int id){
		   
		   Shows giveshoowShows = showsrepo.findById(id);
		   List<BookingSeats> Bookedtickets =  bookingseatsrepo.findByShows(giveshoowShows);
		   
		   List<BdataWrapper> result = new ArrayList<>();
		   Map<String, String> Strucutre = new HashMap<>();
		   
		   for (BookingSeats eachticket : Bookedtickets) {
			   String rowname = eachticket.getRowname();
			   String Seatnumber = String.valueOf(eachticket.getSeatcolumn());
			   

			   if(Strucutre.containsKey(rowname)) {
				 
				  String previous = Strucutre.get(rowname);
				  
				  Strucutre.put(rowname, previous + "," +Seatnumber);
				   
			   }
			   else {
				Strucutre.put(rowname, Seatnumber);
			}
			
		}
		   for (String bdataWrap : Strucutre.keySet()) {
			   
			    BdataWrapper allinone = new BdataWrapper();
			    allinone.setRowname(bdataWrap);
			    allinone.setSeatcolumn(Strucutre.get(bdataWrap));
			    
			    result.add(allinone);
			
		}
		   
		   
		   
		   
		   return result;
		
	}
	   
	 
	   @PostMapping("bookingseats/{id}")
	   private boolean Bookedseats(HttpServletRequest request,@RequestBody List<BookingSeats> bookingSeatsFromUser,@PathVariable("id")int showId) {
		   String jwtString = request.getHeader(Jwtconstant.JWTHEADER);
	    	 
	    	 String tookenString = jwtString.substring(6);
	    	 
	    	 String emailString  = jwtProvider.getemailfromtoken(tookenString);
	    	 
	    	 Theatrelists theatrelists = theatrelistrepo.findByEmail(emailString);
	    	
	
	    	 Layout givelayt = layoutrepo.findByTheatrelists(theatrelists);
	    	
	    	 
	    	 Shows Bookingshow = showsrepo.findtrueshows(theatrelists, true, showId);
	    	 
	    	 if(Bookingshow == null) {
	    		 return false;
	    	 }
	    	 
	    	 List<BookingSeats> BookedseatsfromShow = bookingseatsrepo.findByShows(Bookingshow);
	    	
	    	 Set<String> bookedseats = new HashSet<>();
	    	 
		 Boolean resultBoolean=    ValidSeats(BookedseatsfromShow,bookingSeatsFromUser,Bookingshow,givelayt);
	    	 
	    if(resultBoolean) {
	      
	    	
	    	int totaltickpric = 0;
	    	String  totaltuc = " ";
	    	for (BookingSeats mine : bookingSeatsFromUser) {
	    		System.out.println(bookedseats.toString());
	    		String rowname ="";
		    	String columns = "";
	    		 rowname += mine.getRowname();
	    		   
    		     columns += String.valueOf(mine.getSeatcolumn());
    		     
    		     totaltickpric += mine.getPrice();
    		     totaltuc += rowname+columns +",";
	    		
    		     
			}
	    

	    	   LocalDate currentDate = LocalDate.now();
	    	   LocalTime now = LocalTime.now();
	           LocalTime currentTime = LocalTime.of(now.getHour(), now.getMinute(), now.getSecond());

	    	   DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	    	   Bookedseats settingBookedseats = new Bookedseats();
	    	   settingBookedseats.setTheatrelists(theatrelists);
	    	   settingBookedseats.setLocalDate(String.valueOf(currentDate));
	    	   settingBookedseats.setLocalTime(currentTime);
	    	   settingBookedseats.setTotalprice(totaltickpric);
	    	   settingBookedseats.setTotaltickets(bookingSeatsFromUser.size());
	    	   settingBookedseats.setTicket(totaltuc);
	    	   settingBookedseats.setShows(Bookingshow);
	    	   bookedseatsrepo.save(settingBookedseats);
	    	   showshistorycontroller.calculateshow(showId);
	    	   
	    	   return true;
	    	   
	    }
	    	
	    else {
			return false;
		}
	    	
	   }


	private boolean ValidSeats(List<BookingSeats> bookedSeatsFromShow, List<BookingSeats> bookingSeatsFromUser,
			Shows bookingshow, Layout givelayt) {
		
		 Set<String> bookedSeatsSet = new HashSet<>();
		    for (BookingSeats seat : bookedSeatsFromShow) {
		        bookedSeatsSet.add(seat.getRowname() + "-" + seat.getSeatcolumn());
		    }
		
		    for (BookingSeats userSeat : bookingSeatsFromUser) {
			        String seatKey = userSeat.getRowname() + "-" + userSeat.getSeatcolumn();

			        // Check if the seat is already booked
			        if (bookedSeatsSet.contains(seatKey)) {
			            
			        	return false;
			        }

			        // Get seat details from Theatreconstruction
			        Theatreconstruction seatInfo = theatrelayoutrepo.findByLayoutid_IdAndRowname(givelayt.getId(), userSeat.getRowname());
			        int seatColumn = userSeat.getSeatcolumn();

			      
			        if (seatColumn < seatInfo.getColumnstart() || seatColumn > seatInfo.getColumnend()) {
			           return false;
			        }

			      

			         
			        // Fetch seat price
			        
			        Seattypes given = theatrelayoutrepo.findseattypebyrow(userSeat.getRowname(), givelayt);
			       
			        int seatTypeId = given.getId();

			        Seatstypeprice seatPrice = seattypepricerepo.findByShowsAndSeattypesId(bookingshow, seatTypeId);
			        System.err.println("seatprice"+seatPrice);
			        if (seatPrice == null) {
			            return false;
			        }

			        Shows existrepoShows = showsrepo.findById(bookingshow.getId());
			        // Save booking details
			        BookingSeats newSeat = new BookingSeats();
			        newSeat.setShows(existrepoShows);
			        newSeat.setRowname(userSeat.getRowname());
			        newSeat.setSeatcolumn(seatColumn);
			        newSeat.setPrice(seatPrice.getPrice());
			          newSeat.setSeattype(seatTypeId);
			         
			        
			         
			        bookingseatsrepo.save(newSeat);
			        System.out.println("Saved seat with booking code: " + newSeat.getBookingcode());
			       
			        
			        
			      
			        
			        
			    }
		return true;
		
	}
	   
	
	@GetMapping("orders")
	private List<Bookedseats> getallSeats(HttpServletRequest request){
 String jwtString = request.getHeader(Jwtconstant.JWTHEADER);
    	 
    	 String tookenString = jwtString.substring(6);
    	 
    	 String emailString  = jwtProvider.getemailfromtoken(tookenString);
    	 
    	 Theatrelists theatrelists = theatrelistrepo.findByEmail(emailString);
		List<Bookedseats> resultBookingSeats = bookedseatsrepo.findByTheatrelists_Id(theatrelists.getId());
		
		return resultBookingSeats;
		
	}
	      
	   
}
