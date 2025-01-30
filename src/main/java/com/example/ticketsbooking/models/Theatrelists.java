		package com.example.ticketsbooking.models;
		
		import java.util.List;
		import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
		
		import jakarta.persistence.Column;
		import jakarta.persistence.Entity;
		import jakarta.persistence.FetchType;
		import jakarta.persistence.GeneratedValue;
		import jakarta.persistence.GenerationType;
		import jakarta.persistence.Id;
		import jakarta.persistence.JoinColumn;
		import jakarta.persistence.ManyToOne;
		import jakarta.persistence.OneToMany;
		import jakarta.persistence.OneToOne;
@Entity
public class Theatrelists {
		
		    @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    @Column(name = "Theatre_id")
		    private int id;
		
		    private String theatrename;
		
		    @ManyToOne
		    @JoinColumn(name = "Location_id")
		    private Locations locations;
		
		    
		    private String managername;
		    
		    private String contact;
		    
		    private String email;
		    
		    private String password;
		    
		    private String role;
		    
		    private boolean access;
		    
		    @JsonIgnore
		    private String Layoutstatus;
		  
		    
		    @OneToMany(fetch = FetchType.EAGER,mappedBy = "theatrelists")
		    private List<Shows> shows;
		    
		    private String adress;
		    
		   
		    
		    private int pincode;



			public int getId() {
				return id;
			}



			public void setId(int id) {
				this.id = id;
			}



			public String getTheatrename() {
				return theatrename;
			}



			public void setTheatrename(String theatrename) {
				this.theatrename = theatrename;
			}



			public Locations getLocations() {
				return locations;
			}



			public void setLocations(Locations locations) {
				this.locations = locations;
			}



			public String getManagername() {
				return managername;
			}



			public void setManagername(String managername) {
				this.managername = managername;
			}



			public String getContact() {
				return contact;
			}



			public void setContact(String contact) {
				this.contact = contact;
			}



			public String getEmail() {
				return email;
			}



			public void setEmail(String email) {
				this.email = email;
			}



			public String getPassword() {
				return password;
			}



			public void setPassword(String password) {
				this.password = password;
			}



			public String getRole() {
				return role;
			}



			public void setRole(String role) {
				this.role = role;
			}



			public boolean isAccess() {
				return access;
			}



			public void setAccess(boolean access) {
				this.access = access;
			}



			public List<Shows> getShows() {
				return shows;
			}



			public void setShows(List<Shows> shows) {
				this.shows = shows;
			}



			public String getAdress() {
				return adress;
			}



			public void setAdress(String adress) {
				this.adress = adress;
			}



			public int getPincode() {
				return pincode;
			}



			public void setPincode(int pincode) {
				this.pincode = pincode;
			}



		
			public String getLayoutstatus() {
				return Layoutstatus;
			}



			public void setLayoutstatus(String layoutstatus) {
				Layoutstatus = layoutstatus;
			}



			public Theatrelists() {
				super();
			}


			
			

			public Theatrelists(int id, String theatrename, Locations locations, String managername, String contact,
					String email, String password, String role, boolean access, String layoutstatus, List<Shows> shows,
					String adress, int pincode) {
				super();
				this.id = id;
				this.theatrename = theatrename;
				this.locations = locations;
				this.managername = managername;
				this.contact = contact;
				this.email = email;
				this.password = password;
				this.role = role;
				this.access = access;
				Layoutstatus = layoutstatus;
				this.shows = shows;
				this.adress = adress;
				this.pincode = pincode;
			}



			@Override
			public String toString() {
				return "Theatrelists [id=" + id + ", theatrename=" + theatrename + ", locations=" + locations
						+ ", managername=" + managername + ", contact=" + contact + ", email=" + email + ", password="
						+ password + ", role=" + role + ", access=" + access + ", shows=" + shows + ", adress=" + adress
						+ ", pincode=" + pincode + "]";
			}
		    
		
			
		
			
		    
		    
		    
		  
		
		
		}