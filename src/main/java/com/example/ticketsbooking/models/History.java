package com.example.ticketsbooking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity
public class History {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Historyid")
	private int id;
	
	
	
	@OneToOne
	@JoinColumn(name = "show_id")
	private Shows shows;
	
	
	
	
	
	
	
	
	
	

}
