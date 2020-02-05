package com.gp.rnd.moviecatalogservice.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gp.rnd.moviecatalogservice.entity.CatalogItem;
import com.gp.rnd.moviecatalogservice.entity.Movie;
import com.gp.rnd.moviecatalogservice.entity.Rating;
import com.gp.rnd.moviecatalogservice.entity.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	@Autowired
	private RestTemplate restTemplate;
	
	// get all rated movie Ids
	// for each movie id, call movie info service to get movie details
	//put them all together
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		UserRating ratings = restTemplate
				.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
		
		return ratings.getRatings()
				.stream()
				.map(rating -> {
						Movie movie =
							restTemplate
							.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
						return new CatalogItem(movie.getName(),"test",rating.getRating());
				})
				.collect(Collectors.toList());
		
		
	}
}
