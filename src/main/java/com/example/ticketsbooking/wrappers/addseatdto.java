package com.example.ticketsbooking.wrappers;

import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Theatreconstruction;
import com.example.ticketsbooking.models.Theatrelists;

public class addseatdto {

	
	private Theatreconstruction theatreconstruction;
	
	private Seattypes seattypes;

	public Theatreconstruction getTheatreconstruction() {
		return theatreconstruction;
	}

	public void setTheatreconstruction(Theatreconstruction theatreconstruction) {
		this.theatreconstruction = theatreconstruction;
	}

	public Seattypes getSeattypes() {
		return seattypes;
	}

	public void setSeattypes(Seattypes seattypes) {
		this.seattypes = seattypes;
	}

	public addseatdto(Theatreconstruction theatreconstruction, Seattypes seattypes) {
		super();
		this.theatreconstruction = theatreconstruction;
		this.seattypes = seattypes;
	}

	
}
