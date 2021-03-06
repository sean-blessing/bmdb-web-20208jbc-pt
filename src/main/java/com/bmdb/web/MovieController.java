package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.Movie;
import com.bmdb.db.MovieRepo;

@CrossOrigin
@RestController
@RequestMapping("/movies")
public class MovieController {
	/*
	 *  A controller will implement 5 CRUD methods:
	 *  1) GET ALL
	 *  2) GET BY ID
	 *  3) POST - Insert / Create
	 *  4) PUT - Update
	 *  5) DELETE - Delete
	 */
	
	@Autowired
	private MovieRepo movieRepo;
	
	//Get all movies
	@GetMapping("/")
	public List<Movie> getAll() {
		return movieRepo.findAll();
	}
	
	//Get a movie by id
	@GetMapping("/{id}")
	public Optional<Movie> getById(@PathVariable int id) {
		return movieRepo.findById(id);
	}
	
	//Add a movie
	@PostMapping("/")
	public Movie addMovie(@RequestBody Movie m) {
		m = movieRepo.save(m);
		return m;
	}
	
	//Update a movie
	@PutMapping("/")
	public Movie updateMovie(@RequestBody Movie m) {
		m = movieRepo.save(m);
		return m;
	}
	
	//Delete a movie
	@DeleteMapping("/{id}")
	public Movie deleteMovie(@PathVariable int id) {
		//Optional type will wrap a movie
		Optional<Movie> m = movieRepo.findById(id);
		//isPresent() will return true if a movie was found
		if (m.isPresent()) {
			// send msg back to front end
			try {
				movieRepo.deleteById(id);
			}
			catch (DataIntegrityViolationException dive) {
				// catch dive when movie exists as fk on another table
				System.err.println(dive.getRootCause().getMessage());
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Foreign Key Constraint Issue - Movie id " + id + " is refered to elsewhere.");
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception caught during Movie delete.");				
			}
		}
		else {
			System.out.println("Error - movie not found for id "+id);
		}
		return m.get();
	}
	
	
	
	

}
