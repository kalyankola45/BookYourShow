package com.example.ticketsbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketsbooking.models.Transectiondetails;

@Repository
public interface Transactionrepo  extends JpaRepository<Transectiondetails, Integer>{

	  Transectiondetails  findByRzorpayid(String rzorpayid);
}

