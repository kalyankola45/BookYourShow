package com.example.ticketsbooking.service.imp;

import org.springframework.stereotype.Service;

import com.example.ticketsbooking.models.Layout;

import com.example.ticketsbooking.repository.Layoutrepo;
import com.example.ticketsbooking.service.Layoutint;

@Service
public class Layoutimp implements Layoutint  {
	
	Layoutrepo layoutrepo;
	

	public Layoutimp(Layoutrepo layoutrepo) {
		super();
		this.layoutrepo = layoutrepo;
	}

	@Override
	public void createlayout(Layout loLayoutid) {
		layoutrepo.save(loLayoutid);
		
	}

	@Override
	public void updatelayoutid(Layout layoutid, int id) {
	 
		 Layout existLayoutid = layoutrepo.findById(id).orElseThrow(null);
		 
		 existLayoutid.setTheatrelists(existLayoutid.getTheatrelists());
		 existLayoutid.setTotalcolumns(layoutid.getTotalcolumns());
		 existLayoutid.setTotalrows(layoutid.getTotalrows());
		 layoutrepo.save(existLayoutid); 
	}

	@Override
	public void deletelayout(int n) {
	layoutrepo.deleteById(n);
		
	}

	@Override
	public Layout getbyid(int id) {
		Layout getbyidLayoutid = layoutrepo.findById(id).orElseThrow(null);
		return getbyidLayoutid;
	}

}
