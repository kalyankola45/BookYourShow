package com.example.ticketsbooking.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Layout;
import com.example.ticketsbooking.models.Seattypes;
import com.example.ticketsbooking.models.Theatreconstruction;

@Repository
public interface Theatrelayoutrepo extends JpaRepository<Theatreconstruction,Integer>{

	   
	     Theatreconstruction findByLayoutid_IdAndRowname(int layoutId, String rowname);
	     
	     @Query("SELECT t FROM Theatreconstruction t WHERE t.seattypes.id = :id")
	     List<Theatreconstruction> findSeatsbytype(@Param("id") int id);

	  
	     List<Theatreconstruction> findByLayoutid(Layout layoutid);
	     
	     @Query("select t.seattypes from Theatreconstruction t where t.layoutid = :layout ")
	     List<Seattypes> findseattypesbylayout( @Param("layout") Layout layout);
	     
	     @Query("select t.seattypes from Theatreconstruction t where t.layoutid = :layout AND t.rowname = :rowname ")
	     Seattypes findseattypebyrow(@Param("rowname")String rowname,@Param("layout")Layout layout);
}
