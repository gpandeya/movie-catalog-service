package com.gp.rnd.moviecatalogservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gp.rnd.moviecatalogservice.entity.CatalogItem;
import com.gp.rnd.moviecatalogservice.entity.Movie;
import com.gp.rnd.moviecatalogservice.entity.Rating;
import com.gp.rnd.moviecatalogservice.entity.UserRating;
import com.gp.rnd.moviecatalogservice.service.MovieInfo;
import com.gp.rnd.moviecatalogservice.service.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
	
	@Autowired
	private MovieInfo movieInfo;
	
	@Autowired
	private UserRatingInfo userRatingInfo;
	
	
	// get all rated movie Ids
	// for each movie id, call movie info service to get movie details
	//put them all together
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		UserRating ratings = userRatingInfo.getUserRating(userId);
		
		return ratings.getRatings()
				.stream()
				.map(rating -> {
						return movieInfo.getCatalogItem(rating);
				})
				.collect(Collectors.toList());
	}

	
}
