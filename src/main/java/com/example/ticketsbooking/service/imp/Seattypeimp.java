package com.example.ticketsbooking.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ticketsbooking.models.Layout;
import com.example.ticketsbooking.models.Movies;
import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Theatreconstruction;
import com.example.ticketsbooking.repository.Layoutrepo;
import com.example.ticketsbooking.repository.Seattyperepo;
import com.example.ticketsbooking.repository.Theatrelayoutrepo;
import com.example.ticketsbooking.service.Seattypeint;

@Service
public class Seattypeimp implements Seattypeint{
	
	Seattyperepo seattyperepo;
	Layoutrepo lauLayoutrepo;
	Theatrelayoutrepo theatrelayoutrepo;
	
	
	
	@Autowired
public Seattypeimp(Seattyperepo seattyperepo, Layoutrepo lauLayoutrepo, Theatrelayoutrepo theatrelayoutrepo) {
		super();
		this.seattyperepo = seattyperepo;
		this.lauLayoutrepo = lauLayoutrepo;
		this.theatrelayoutrepo = theatrelayoutrepo;
	}






	@Override
	public void createseattype(Seattypes seattypes) {
	    // Get the Layout ID from the provided Seattypes object
	    int layoutId = seattypes.getLayout().getId();  

	    // Fetch the existing Layout or throw an exception if it doesn't exist
	    Layout existLayout = lauLayoutrepo.findById(layoutId)
	        .orElseThrow(() -> new IllegalArgumentException("Layout with ID " + layoutId + " does not exist"));

	    // Set the existing Layout to the Seattypes to ensure proper linkage
	    seattypes.setLayout(existLayout);

	    // Save the Seattypes
	   seattyperepo.save(seattypes);
	}
                                                                                                                      
		
	

	@Override
	public List<Seattypes> getall(int id) {
	      List<Seattypes> byLayout_Id = (List<Seattypes>) seattyperepo.findByLayout_Id(id);
		List<Seattypes> allseattypes = byLayout_Id;
		return allseattypes;
		
		
	}



	@Override
	public Seattypes findseattype(int id) {
	    Seattypes seattypes = seattyperepo.findById(id).orElseThrow(null);
	    return seattypes;
	}

	@Override
	public void Updateseattype(Seattypes seattypes, int id) {
		  Seattypes existSeattypes = seattyperepo.findById(id).orElseThrow(null);
		  
		  existSeattypes.setRowstart(seattypes.getRowstart());
		  existSeattypes.setRowend(seattypes.getRowend());
		  existSeattypes.setSeattypename(seattypes.getSeattypename());
		  
		  seattyperepo.save(existSeattypes);

		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeleteSeattype(int id) {
		seattyperepo.deleteById(id);
	}






	@Override
	public void addtotalseats(int id) {
		
			List<Seattypes> totalseatSeattypes = seattyperepo.findByLayout_Id(id);
			
			System.out.println("hell all"+ totalseatSeattypes.toString());

			

			for (Seattypes seattypes : totalseatSeattypes) {
				int noofseatsreg = 0;
				
				int idd = seattypes.getId();
			

				
				List<Theatreconstruction> totaList = theatrelayoutrepo.findSeatsbytype(id);
				
				   for (Theatreconstruction each : totaList) {
					
				

					       noofseatsreg += (each.getColumnend() - each.getColumnstart()+1);
					       
					      String[] arr = each.getGaps().split(" ,");
					      
					      noofseatsreg -= arr.length;
				
				   }
				   
				   
				   seattypes.setNoofseats(noofseatsreg);
				   
				   seattyperepo.save(seattypes);
				   
					
					      
				   }
			
		
			
			
			
			
			
			
		}

		
	}

