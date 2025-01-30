package com.example.ticketsbooking.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.models.Layout;

import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Theatreconstruction;
import com.example.ticketsbooking.models.Theatrelists;
import com.example.ticketsbooking.repository.Layoutrepo;

import com.example.ticketsbooking.repository.Seattyperepo;
import com.example.ticketsbooking.repository.Theatrelayoutrepo;
import com.example.ticketsbooking.repository.Theatrelistrepo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin/noofseatscal")
@CrossOrigin
public class Noofseatscaluculator {

	private Seattyperepo seattyperepo;
	
	private Theatrelayoutrepo theatrelayoutrepo;
	
	private Theatrelistrepo theatrelistrepo;
	
	private Layoutrepo layoutrepo;
	
	
	@Autowired
public Noofseatscaluculator( Seattyperepo seattyperepo,
			Theatrelayoutrepo theatrelayoutrepo, Theatrelistrepo theatrelistrepo, Layoutrepo layoutrepo) {
		super();
		
		this.seattyperepo = seattyperepo;
		this.theatrelayoutrepo = theatrelayoutrepo;
		this.theatrelistrepo = theatrelistrepo;
		this.layoutrepo = layoutrepo;
	}







	@PostMapping("/total/{id}")
	public ResponseEntity<?> postMethodName(@PathVariable int id) {
	    // Fetch the layout based on the provided ID
	    Layout lgivenlayout = layoutrepo.findById(id).orElse(null);
	    if (lgivenlayout == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Layout not found");
	    }

	    // Fetch all seat types for the given layout
	    List<Seattypes> totalseatSeattypes = seattyperepo.findByLayout_Id(id);

	    for (Seattypes seattypes : totalseatSeattypes) {
	        System.out.println(seattypes.toString());

	        int noofseatsreg = 0;

	        // Fetch seat information based on the current seat type ID
	        List<Theatreconstruction> totaList = theatrelayoutrepo.findSeatsbytype(seattypes.getId());

	        for (Theatreconstruction each : totaList) {
	            // Calculate total seats for the current seat type
	            noofseatsreg += (each.getColumnend() - each.getColumnstart() + 1);

	            // Deduct gaps from the total seat count
	            String[] arr = each.getGaps().split(",");
	            noofseatsreg -= arr.length;
	        }
          seattypes.setNoofseats(noofseatsreg);
          
          seattyperepo.save(seattypes);
	       
	    }

	    return ResponseEntity.ok("Total seats calculated successfully");
	}




	

	
	
	
	
	   
}
