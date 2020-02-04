package com.gp.rnd.moviecatalogservice.controller;

import java.util.Collections;
import java.util.List;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gp.rnd.moviecatalogservice.entity.CatalogItem;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	// get all rated movie Ids
	// for each movie id, call movie info service to get movie details
	//put them all together
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		return Collections.singletonList(new CatalogItem("DDLJ","movie desc",5));
	}
}
