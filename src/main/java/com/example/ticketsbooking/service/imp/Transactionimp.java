package com.example.ticketsbooking.service.imp;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.ticketsbooking.models.Transectiondetails;
import com.example.ticketsbooking.repository.Transactionrepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class Transactionimp {

	Transactionrepo transactionrepo;

	
	@Autowired
	public Transactionimp(Transactionrepo transactionrepo) {
		super();
		this.transactionrepo = transactionrepo;
	}
	
	@Value("${razor.pay.key}")
	private String razoepaykey;
	
	@Value("${razor.pay.secretkey}")
	private String secrentkey;
	
	
	private RazorpayClient client;
	
	public Transectiondetails addtransection(Transectiondetails transectiondetails) throws RazorpayException {
	
		this.client = new RazorpayClient(razoepaykey, secrentkey);
		
		JSONObject transreq = new JSONObject();
		
		transreq.put("amount", transectiondetails.getAmount() * 100);
		transreq.put("currency", "INR");
		transreq.put("receipt", transectiondetails.getEmail());
		
		
		
	    Order payload	= client.orders.create(transreq);
	    
	    transectiondetails.setRzorpayid(payload.get("id"));
	    
	    transectiondetails.setPaymentstatus(payload.get("status"));
	    
	    transactionrepo.save(transectiondetails);
	    
	    return transectiondetails;
	}
	
	
	
	
}
