package com.example.ticketsbooking.wrappers;

import java.util.List;

import com.example.ticketsbooking.models.Layout;
import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Theatrelists;

public class Managementwrapper {
	
	private Theatrelists theatrelists;
	
	private Layout layout;
	
	private List<Seattypes> seattypes;

	public Theatrelists getTheatrelists() {
		return theatrelists;
	}

	public void setTheatrelists(Theatrelists theatrelists) {
		this.theatrelists = theatrelists;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public List<Seattypes> getSeattypes() {
		return seattypes;
	}

	public void setSeattypes(List<Seattypes> seattypes) {
		this.seattypes = seattypes;
	}

	public Managementwrapper(Theatrelists theatrelists, Layout layout, List<Seattypes> seattypes) {
		super();
		this.theatrelists = theatrelists;
		this.layout = layout;
		this.seattypes = seattypes;
	}

	public Managementwrapper() {
		super();
	}

	
	
	

}
