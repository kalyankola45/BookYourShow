package com.example.ticketsbooking.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
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
import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Theatreconstruction;
import com.example.ticketsbooking.models.Theatrelists;
import com.example.ticketsbooking.repository.Layoutrepo;
import com.example.ticketsbooking.repository.Theatrelayoutrepo;
import com.example.ticketsbooking.repository.Theatrelistrepo;
import com.example.ticketsbooking.service.Seattypeint;
import com.example.ticketsbooking.service.theatrelayoutint;
import com.example.ticketsbooking.wrappers.addseatdto;

@RestController
@RequestMapping("/admin/seatlayout")
@CrossOrigin
public class Theatrelayoutcontroller {
	
	theatrelayoutint theatrelayoutint;

	Theatrelistrepo theatrelistrepo;
	Layoutrepo layoutrepo;
	
	Seattypeint seattypeint;
	
	Theatrelayoutrepo theatrelayoutrepo;
	
	
	
	
	
	  @Autowired
	public Theatrelayoutcontroller(com.example.ticketsbooking.service.theatrelayoutint theatrelayoutint,
			Theatrelistrepo theatrelistrepo, Layoutrepo layoutrepo, Seattypeint seattypeint,
			Theatrelayoutrepo theatrelayoutrepo) {
		super();
		this.theatrelayoutint = theatrelayoutint;
		this.theatrelistrepo = theatrelistrepo;
		this.layoutrepo = layoutrepo;
		this.seattypeint = seattypeint;
		this.theatrelayoutrepo = theatrelayoutrepo;
	}



	


	
	@PostMapping("/create/{id}")
	private ResponseEntity<?> addrow(@RequestBody addseatdto addseatdto,@PathVariable int id)
	{
		System.err.println("process start");
	Theatreconstruction theatreconstruction = addseatdto.getTheatreconstruction();
	
	Seattypes seattypes = addseatdto.getSeattypes();
	    
		Layout layoutid = layoutrepo.findById(id).orElse(null);
		if (layoutid == null) {
	        return ResponseEntity.badRequest().body("Invalid layout ID");
	    }
		theatreconstruction.setLayoutid(layoutid);
		theatreconstruction.setSeattypes(seattypes);
		
		
	   boolean res =    theatrelayoutint.addseatrow(theatreconstruction);
	   if(res) {
		   return  ResponseEntity.ok("success");
	   }
	   else {
		return  ResponseEntity.badRequest().body("Failed to add seat row");
	}
		
	}
	
	@GetMapping("/getbyid/{id}")
	private Theatreconstruction getbyid(@PathVariable int id) {
		return theatrelayoutint.getseatrow(id);
	}

	@PutMapping("/update/{id}")
	private void updatetheareconstruction(@RequestBody addseatdto addseatdto,@PathVariable int id) {
		Theatreconstruction theatreconstruction = addseatdto.getTheatreconstruction();
		theatrelayoutint.updateseatrow(theatreconstruction, id);
	}
	@DeleteMapping("/delete/{id}")
	private void deletebyid(@PathVariable int id) {
		theatrelayoutint.deleteseatrow(id);
	}
	
	@GetMapping("/getallrows/{param}")
	private ResponseEntity<Map<String, List<Theatreconstruction>>> getallseatsdata(@PathVariable("param") int id){
		System.out.println("get all process start");
		
		Theatrelists theatrelists = theatrelistrepo.findById(id).orElseThrow(null);

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
}

