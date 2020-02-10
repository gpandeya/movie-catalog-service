package com.gp.rnd.moviecatalogservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gp.rnd.moviecatalogservice.entity.Rating;
import com.gp.rnd.moviecatalogservice.entity.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserRatingInfo {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserRating",
			commandProperties = {
					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000"),
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="5"),
					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50"),
					@HystrixProperty(name="circuitBreaker.sleepWindowsInMiliseconds",value="5000")
			})
	public UserRating getUserRating(String userId) {
		return restTemplate
				.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
	}
	public UserRating getFallbackUserRating(String userId) {
		return new UserRating(Arrays.asList(new Rating("0",0)));
	}

}
