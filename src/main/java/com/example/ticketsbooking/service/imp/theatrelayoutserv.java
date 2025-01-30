package com.example.ticketsbooking.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticketsbooking.models.Theatreconstruction;
import com.example.ticketsbooking.repository.Theatrelayoutrepo;
import com.example.ticketsbooking.service.theatrelayoutint;

@Service
public class theatrelayoutserv implements theatrelayoutint {
	
	Theatrelayoutrepo theatrelayoutrepo;
	

	@Autowired
	public theatrelayoutserv(Theatrelayoutrepo theatrelayoutrepo) {
		super();
		this.theatrelayoutrepo = theatrelayoutrepo;
	}

	@Override
	public boolean addseatrow(Theatreconstruction theatreconstruction) {
		try {
			theatrelayoutrepo.save(theatreconstruction);
			
			return true;
			
		} catch (Exception e) {
			return false;
		}
	
		
	}

	@Override
	public void updateseatrow(Theatreconstruction theatreconstruction, int id) {
	    Theatreconstruction exisTheatreconstruction = theatrelayoutrepo.findById(id).orElseThrow(null);
	    
	    System.out.println("process start before");
        
	    System.out.println(exisTheatreconstruction.toString());

	    
	    exisTheatreconstruction.setColumnend(theatreconstruction.getColumnend());
	    exisTheatreconstruction.setColumnstart(theatreconstruction.getColumnstart());
	    exisTheatreconstruction.setGaps(theatreconstruction.getGaps());
	    exisTheatreconstruction.setRowname(theatreconstruction.getRowname());

	    
	    theatrelayoutrepo.save(exisTheatreconstruction);
	   
	    System.out.println(exisTheatreconstruction.toString());

	}

	@Override
	public void deleteseatrow(int id) {
		theatrelayoutrepo.deleteById(id);
	}

	@Override
	public Theatreconstruction getseatrow(int id) {
	
	Theatreconstruction	resuly  = theatrelayoutrepo.findById(id).orElseThrow(null);
		
		return resuly;
	}

}
