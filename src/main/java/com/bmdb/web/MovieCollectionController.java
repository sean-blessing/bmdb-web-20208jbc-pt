package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.MovieCollection;
import com.bmdb.db.MovieCollectionRepo;

@CrossOrigin
@RestController
@RequestMapping("/movie-collections")
public class MovieCollectionController {
	/*
	 *  A controller will implement 5 CRUD methods:
	 *  1) GET ALL
	 *  2) GET BY ID
	 *  3) POST - Insert / Create
	 *  4) PUT - Update
	 *  5) DELETE - Delete
	 */
	
	@Autowired
	private MovieCollectionRepo movieCollectionRepo;
	
	//Get all movieColllections
	@GetMapping("/")
	public List<MovieCollection> getAll() {
		return movieCollectionRepo.findAll();
	}
	
	//Get a movieColllection by id
	@GetMapping("/{id}")
	public Optional<MovieCollection> getById(@PathVariable int id) {
		return movieCollectionRepo.findById(id);
	}
	
	//Add a movieColllection
	@PostMapping("/")
	public MovieCollection addMovieCollection(@RequestBody MovieCollection m) {
		m = movieCollectionRepo.save(m);
		return m;
	}
	
	//Update a movieColllection
	@PutMapping("/")
	public MovieCollection updateMovieCollection(@RequestBody MovieCollection m) {
		m = movieCollectionRepo.save(m);
		return m;
	}
	
	//Delete a movieColllection
	@DeleteMapping("/{id}")
	public MovieCollection deleteMovieCollection(@PathVariable int id) {
		//Optional type will wrap a movieColllection
		Optional<MovieCollection> m = movieCollectionRepo.findById(id);
		//isPresent() will return true if a movieColllection was found
		if (m.isPresent()) {
			movieCollectionRepo.deleteById(id);
		}
		else {
			System.out.println("Error - movie collection not found for id "+id);
		}
		return m.get();
	}
	
	
	
	

}
