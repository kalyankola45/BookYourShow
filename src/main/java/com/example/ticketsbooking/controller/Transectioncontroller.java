package com.example.ticketsbooking.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketsbooking.models.Transectiondetails;
import com.example.ticketsbooking.service.imp.Transactionimp;
import com.razorpay.RazorpayException;

@Service
public class Transectioncontroller {

	  
	  Transactionimp transervice;

	public Transectioncontroller(Transactionimp transervice) {
		super();
		this.transervice = transervice;
	}

	 
	  public ResponseEntity<?> addpayment(@RequestBody Transectiondetails transectiondetails) throws RazorpayException{
		  Transectiondetails createorder = transervice.addtransection(transectiondetails);
		  
		  String razorpayid = createorder.getRzorpayid();
		  
		  return new ResponseEntity<>(Map.of(
				  "transactionDetails", createorder,
				  "Paymentid",razorpayid
				  )
				  , HttpStatus.CREATED);
	  }
}
