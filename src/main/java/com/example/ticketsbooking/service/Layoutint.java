package com.example.ticketsbooking.service;

import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.data.annotation.CreatedBy;

import com.example.ticketsbooking.models.Layout;


public interface Layoutint {
	
 void createlayout(Layout loLayoutid);
 
 void updatelayoutid(Layout layoutid, int id);
 
 void deletelayout(int n);
 
   Layout getbyid(int id);
 
   

}
