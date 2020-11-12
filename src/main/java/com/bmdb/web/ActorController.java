package com.bmdb.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.Actor;
import com.bmdb.db.ActorRepo;

@CrossOrigin
@RestController
@RequestMapping("/actors")
public class ActorController {
	
	@Autowired
	private ActorRepo actorRepo;
	
	// list all actors
	@GetMapping("")
	public List<Actor> getAllActors() {
		return actorRepo.findAll();
	}
	
	// get actor by id
	@GetMapping("/{id}")
	public Optional<Actor> getActor(@PathVariable int id) {
		Optional<Actor> a = actorRepo.findById(id);
		if (a.isPresent()) {
			return a;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found");
		}
	}
	
	// add a actor
	@PostMapping("/")
	public Actor addActor(@RequestBody Actor a) {
		return actorRepo.save(a);
	}
	
	// update a actor
	@PutMapping("/{id}")
	public Actor updateActor(@RequestBody Actor a, @PathVariable int id) {
		if (id == a.getId()) {
			return actorRepo.save(a);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor id does not match.");
		}
	}
	
	// delete a actor
	@DeleteMapping("/{id}")
	public Optional<Actor> deleteActor(@PathVariable int id) {
		Optional<Actor> a = actorRepo.findById(id);
		if (a.isPresent()) {
			actorRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found.");
		}
		return a;
	}
	
	// list all actors by gender
	// using RequestParam to pass gender
	@GetMapping("/find-by-gender")
	public List<Actor> getActorsByGender(@RequestParam String gender) {
		return actorRepo.findByGender(gender);
	}
	
	// list all actors whose last name starts with 'letter'
	@GetMapping("/find-lastname-starts-with")
	public List<Actor> getActorsLastNameStarts(@RequestParam String letter) {
		return actorRepo.findByLastNameLike(letter+"%");
	}
	
	// list all actors born between d1 and d2
	@GetMapping("/find-by-birthdate-between")
	public List<Actor> getActorsByBirthDateBetween(@RequestParam String d1, @RequestParam String d2) {
		LocalDate ld1 = LocalDate.parse(d1);
		LocalDate ld2 = LocalDate.parse(d2);
		
		return actorRepo.findByBirthDateBetween(ld1, ld2);
	}
	
	
	
	
	
	
	
	
	
	
	
}
