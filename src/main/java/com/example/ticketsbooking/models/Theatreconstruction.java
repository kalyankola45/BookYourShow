package com.example.ticketsbooking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


@Entity
public class Theatreconstruction {

	
	     @Id
	     @GeneratedValue(strategy = GenerationType.IDENTITY)
	     @Column(name = "row_id")
	     private int id;
	     
	     
	      @ManyToOne
	      @JoinColumn(name = "Layout_id")
	      @JsonIgnore
	      private Layout layoutid;
	      
	      
	      @ManyToOne
	      @JoinColumn(name = "Seattype_id")
	      @JsonIgnore
	      private Seattypes seattypes;
	      
	      
	  	   private String rowname;
	      
	       private String gaps;
	      
	      private int columnstart;
	      
	      private int columnend;
	     
	      
	      public Seattypes getSeattypes() {
			return seattypes;
		}

		public void setSeattypes(Seattypes seattypes) {
			this.seattypes = seattypes;
		}

		public Layout getLayoutid() {
			return layoutid;
		}

		public void setLayoutid(Layout layoutid) {
			this.layoutid = layoutid;
		}

	

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}


		public String getRowname() {
			return rowname;
		}

		public void setRowname(String rowname) {
			this.rowname = rowname;
		}

		public String getGaps() {
			return gaps;
		}

		public void setGaps(String gaps) {
			this.gaps = gaps;
		}

		public int getColumnstart() {
			return columnstart;
		}

		public void setColumnstart(int columnstart) {
			this.columnstart = columnstart;
		}

		public int getColumnend() {
			return columnend;
		}

		public void setColumnend(int columnend) {
			this.columnend = columnend;
		}

		public Theatreconstruction(int id, Layout layoutid, Seattypes seattypes, String rowname, String gaps,
				int columnstart, int columnend) {
			super();
			this.id = id;
			this.layoutid = layoutid;
			this.seattypes = seattypes;
			this.rowname = rowname;
			this.gaps = gaps;
			this.columnstart = columnstart;
			this.columnend = columnend;
		}

		public Theatreconstruction() {
			super();
		}

		@Override
		public String toString() {
			return "Theatreconstruction [id=" + id + ", layoutid=" + layoutid + ", seattypes=" + seattypes
					+ ", rowname=" + rowname + ", gaps=" + gaps + ", columnstart=" + columnstart + ", columnend="
					+ columnend + "]";
		}

	

	     
	      
	      
	           
	
}
