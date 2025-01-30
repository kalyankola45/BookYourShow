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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;



@Entity
public class Layout {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Layout_id")
	private int id;
	
	
	@OneToOne
	@JoinColumn(name = "Theatre_id")
	@JsonIgnore
	private Theatrelists theatrelists;
	
	private int totalrows;
	
	private int totalcolumns;

	@OneToMany(mappedBy = "layout",cascade = CascadeType.PERSIST)
	@JsonIgnore
     private List<Seattypes> seattypes;
	
	
	public List<Seattypes> getSeattypes() {
		return seattypes;
	}

	public void setSeattypes(List<Seattypes> seattypes) {
		this.seattypes = seattypes;
	}

	public Theatrelists getTheatrelists() {
		return theatrelists;
	}

	public void setTheatrelists(Theatrelists theatrelists) {
		this.theatrelists = theatrelists;
	}

	public int getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
	}

	public int getTotalcolumns() {
		return totalcolumns;
	}

	public void setTotalcolumns(int totalcolumns) {
		this.totalcolumns = totalcolumns;
	}

	public Layout(int id, Theatrelists theatrelists, int totalrows, int totalcolumns) {
		super();
		this.id = id;
		this.theatrelists = theatrelists;
		this.totalrows = totalrows;
		this.totalcolumns = totalcolumns;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Layout() {
		super();
	}

	public Layout(int id, Theatrelists theatrelists, int totalrows, int totalcolumns, List<Seattypes> seattypes) {
		super();
		this.id = id;
		this.theatrelists = theatrelists;
		this.totalrows = totalrows;
		this.totalcolumns = totalcolumns;
		this.seattypes = seattypes;
	}

	
	
	
	
     
	
	
	
	
	
	
	
	
	
	


}
