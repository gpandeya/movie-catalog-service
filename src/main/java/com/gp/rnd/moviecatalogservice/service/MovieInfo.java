package com.gp.rnd.moviecatalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gp.rnd.moviecatalogservice.entity.CatalogItem;
import com.gp.rnd.moviecatalogservice.entity.Movie;
import com.gp.rnd.moviecatalogservice.entity.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfo {
	
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie =
			restTemplate
			.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(),movie.getDescription(),rating.getRating());
	}
	
	
	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("No Movie", "No Movie", rating.getRating());
	}
}
