package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.business.Credit;
import com.bmdb.db.CreditRepo;

@CrossOrigin
@RestController
@RequestMapping("/credits")
public class CreditController {
	
	@Autowired
	private CreditRepo creditRepo;
	
	// list all credits
	@GetMapping("/")
	public List<Credit> getAllCredits() {
		return creditRepo.findAll();
	}


	// get credit by id
	@GetMapping("/{id}")
	public Optional<Credit> getCredit(@PathVariable int id) {
		Optional<Credit> a = creditRepo.findById(id);
		if (a.isPresent()) {
			return a;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found");
		}
	}

	
	// add a credit
	@PostMapping("/")
	public Credit addCredit(@RequestBody Credit a) {
		return creditRepo.save(a);
	}
	/*	
	// update a credit
	@PutMapping("/{id}")
	public Credit updateCredit(@RequestBody Credit a, @PathVariable int id) {
		if (id == a.getId()) {
			return creditRepo.save(a);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit id does not match.");
		}
	}
	
	// delete a credit
	@DeleteMapping("/{id}")
	public Optional<Credit> deleteCredit(@PathVariable int id) {
		Optional<Credit> a = creditRepo.findById(id);
		if (a.isPresent()) {
			creditRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found.");
		}
		return a;
	}
	*/
	
}
