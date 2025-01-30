package com.example.ticketsbooking.service;

import com.example.ticketsbooking.models.Theatreconstruction;

public interface theatrelayoutint {
    
	 boolean addseatrow(Theatreconstruction theatreconstruction);
	 
	 void updateseatrow(Theatreconstruction theatreconstruction, int id);
	 
	 void deleteseatrow(int id);
	 
	 
	 Theatreconstruction getseatrow(int id);
	 
}
