package com.example.ticketsbooking.models;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private int id;

    private String name;
    private String Genres;
    
    private String runtime;
    private String releasedate;
  
    private String image;
   private String trailerurl;
   
   private String description;
 
    // Owning side of the relationship
    
    @OneToMany(mappedBy = "movies")
    @JsonIgnore
    private List<Shows> shows;
    

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Shows> getShows() {
		return shows;
	}

	public void setShows(List<Shows> shows) {
		this.shows = shows;
	}

	
	public String getTrailerurl() {
		return trailerurl;
	}

	public void setTrailerurl(String trailerurl) {
		this.trailerurl = trailerurl;
	}

	


	public String getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(String releasedate) {
		this.releasedate = releasedate;
	}

	public String getGenres() {
		return Genres;
	}

	
	public Movies(int id, String name, String runtime, String description, String image, String trailerurl,
			String genres, String releasedate, List<Shows> shows) {
		super();
		this.id = id;
		this.name = name;
		this.runtime = runtime;
		this.description = description;
		this.image = image;
		this.trailerurl = trailerurl;
		Genres = genres;
		this.releasedate = releasedate;
		this.shows = shows;
	}

	public void setGenres(String genres) {
		Genres = genres;
	}

	public Movies() {
		super();
	}

	@Override
	public String toString() {
		return "Movies [id=" + id + ", name=" + name + ", Genres=" + Genres + ", runtime=" + runtime + ", releasedate="
				+ releasedate + ", image=" + image + ", trailerurl=" + trailerurl + ", description=" + description
				+ ", shows=" + shows + "]";
	}

	


	
	


	
}

