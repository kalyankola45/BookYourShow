package com.example.ticketsbooking.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Seattypes{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "Seattype_id")
	private int id;
	
	
	private String Seattypename;
	
	@ManyToOne
	@JoinColumn(name = "Layout_id")
	private Layout layout;
	

	
	private int rowstart;
	
	private int rowend;

	@JsonIgnore
	private int noofseats;
	public int getId() {
		return id;
	}

	@OneToMany(mappedBy = "seattypes")
	@JsonIgnore
	private List<Seatstypeprice> seatstypeprice;
	
	public void setId(int id) {
		this.id = id;
	}

	public String getSeattypename() {
		return Seattypename;
	}

	public void setSeattypename(String seattypename) {
		Seattypename = seattypename;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public int getRowstart() {
		return rowstart;
	}

	public void setRowstart(int rowstart) {
		this.rowstart = rowstart;
	}

	public int getRowend() {
		return rowend;
	}

	public void setRowend(int rowend) {
		this.rowend = rowend;
	}

	
	public int getNoofseats() {
		return noofseats;
	}

	public void setNoofseats(int noofseats) {
		this.noofseats = noofseats;
	}

	public Seattypes() {
		super();
	}

	
	 
	public Seattypes(int id, String seattypename, Layout layout, int rowstart, int rowend, int noofseats,
			List<Seatstypeprice> seatstypeprice) {
		super();
		this.id = id;
		Seattypename = seattypename;
		this.layout = layout;
		this.rowstart = rowstart;
		this.rowend = rowend;
		this.noofseats = noofseats;
		this.seatstypeprice = seatstypeprice;
	}

	public List<Seatstypeprice> getSeatstypeprice() {
		return seatstypeprice;
	}

	public void setSeatstypeprice(List<Seatstypeprice> seatstypeprice) {
		this.seatstypeprice = seatstypeprice;
	}

	@Override
	public String toString() {
		return "Seattypes [id=" + id + ", Seattypename=" + Seattypename + ", layout=" + layout + ", rowstart="
				+ rowstart + ", rowend=" + rowend + ", noofseats=" + noofseats + "]";
	}
	
	

	
	
	
	
	

}
