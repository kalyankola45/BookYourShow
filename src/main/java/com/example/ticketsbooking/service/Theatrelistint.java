package com.example.ticketsbooking.service;

import java.util.List;

import com.example.ticketsbooking.models.Locations;
import com.example.ticketsbooking.models.Theatrelists;

public interface Theatrelistint {
	
     List<Theatrelists> getallTheatrelist();
     
     void createTheatreliss(Theatrelists theatrelists);
     
     Theatrelists getbyidTheatrelists(int id);
     
     void updatetheatrelist(Theatrelists theatrelists , int id);
     
     void deletetheatrelist(int id);

     List<Theatrelists> getallbylocations(int id);
}
