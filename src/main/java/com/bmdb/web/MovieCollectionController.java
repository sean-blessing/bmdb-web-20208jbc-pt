package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.MovieCollection;
import com.bmdb.business.User;
import com.bmdb.db.MovieCollectionRepo;
import com.bmdb.db.UserRepo;

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
	@Autowired
	private UserRepo userRepo;
	
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
	//Also recalculate the collection value in User
	@PostMapping("/")
	public MovieCollection addMovieCollection(@RequestBody MovieCollection m) {
		m = movieCollectionRepo.save(m);
		recalculateCollectionValue(m);
		return m;
	}

	private void recalculateCollectionValue(MovieCollection m) {
		// get all movie collections for this user
		// loop through them and sum a new total
		double newTotal = 0.0;
		List<MovieCollection> mcs = movieCollectionRepo.findByUserId(m.getUser().getId());
		for (MovieCollection mc: mcs) {
			newTotal += mc.getPurchasePrice();
		}
		User u = m.getUser();
		u.setCollectionValue(newTotal);
		userRepo.save(u);
	}
	
	//Update a movieColllection
	//Also recalculate the collection value in User
	@PutMapping("/")
	public MovieCollection updateMovieCollection(@RequestBody MovieCollection m) {
		m = movieCollectionRepo.save(m);
		recalculateCollectionValue(m);
		return m;
	}
	
	//Delete a movieColllection
	//Also recalculate the collection value in User
	@DeleteMapping("/{id}")
	public MovieCollection deleteMovieCollection(@PathVariable int id) {
		//Optional type will wrap a movieColllection
		Optional<MovieCollection> m = movieCollectionRepo.findById(id);
		//isPresent() will return true if a movieColllection was found
		if (m.isPresent()) {
			movieCollectionRepo.deleteById(id);
			recalculateCollectionValue(m.get());
		}
		else {
			System.out.println("Error - movie collection not found for id "+id);
		}
		return m.get();
	}
	
	
	
	

}
