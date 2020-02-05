package com.gp.rnd.moviecatalogservice.controller;

import java.util.Arrays;
import java.util.Collections;
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
		
		List<Rating> ratings =  Arrays.asList(
				new Rating("110",1),
				new Rating("120",2),
				new Rating("130",3));
		
		return ratings
				.stream()
				.map(rating -> {
						Movie movie = 
							restTemplate
							.getForObject("http://localhost:7082/movies/"+rating.getMovieId(), Movie.class);
						return new CatalogItem(movie.getName(),"test",rating.getRating());
				})
				.collect(Collectors.toList());
		
		
	}
}
