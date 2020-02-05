package com.gp.rnd.moviecatalogservice.entity;

import java.util.List;

public class UserRating {

	private List<Rating> ratings;
	
	public UserRating() {
		
	}

	public UserRating(List<Rating> ratings) {
		super();
		this.ratings = ratings;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	@Override
	public String toString() {
		return "UserRating [ratings=" + ratings + "]";
	}
	
	
	
	
}
