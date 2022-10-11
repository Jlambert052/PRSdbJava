package com.bootcamp.capstone.ExpenseDb.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.capstone.ExpenseDb.Models.Request;
import com.bootcamp.capstone.ExpenseDb.Repositories.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestsController {

	private String APPROVED = "APPROVED";
	private String REJECTED = "REJECTED";
	private String REVIEW = "REVIEW";
	
	@Autowired
	private RequestRepository reqRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Request>> getRequests() {
		Iterable<Request> requests = reqRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Request> getRequestByPK(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Request> thisRequest = reqRepo.findById(id);
		if(thisRequest.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(thisRequest.get(), HttpStatus.OK);
	}
	
	//return list of requests with REVIEW status and NOT the given userid
	@GetMapping("reviews/{userid}")
	public ResponseEntity<Iterable<Request>> getReviewRequests(@PathVariable int userid) {
		Optional<Iterable<Request>> reviewRequests = reqRepo.findByStatusAndUserIdNot(REVIEW, userid);
		return new ResponseEntity<Iterable<Request>>(reviewRequests.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request) {
		if(request.getId() != 0) {
			return new ResponseEntity<Request>(HttpStatus.BAD_REQUEST);
		}
		Request newRequest = reqRepo.save(request);
		return new ResponseEntity<Request>(newRequest, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequest(@RequestBody Request request, @PathVariable int id) {
		if(id <= 0 || id != request.getId() ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//set REVIEW unless total < 50, then set APPROVED
	@PutMapping("review/{id}")
	public ResponseEntity<Request> reviewRequest(@PathVariable int id, @RequestBody Request request) {
		if(id != request.getId() ||id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Request> thisRequest = reqRepo.findById(id);
		if(thisRequest.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(thisRequest.get().getTotal() <= 50) {
			thisRequest.get().setStatus(APPROVED);
		} else {
			thisRequest.get().setStatus(REVIEW);
		}
		reqRepo.save(thisRequest.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//set APPROVED
	@PutMapping("approve/{id}")
	public ResponseEntity<Request> approveRequest(@PathVariable int id, @RequestBody Request request) {
		if(id != request.getId() ||id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Request> thisRequest = reqRepo.findById(id);
		if(thisRequest.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		thisRequest.get().setStatus(APPROVED);
		reqRepo.save(thisRequest.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	//set REJECTED
	@PutMapping("reject/{id}")
	public ResponseEntity<Request> rejectRequest(@PathVariable int id, @RequestBody Request request) {
		if(id != request.getId() ||id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Request> thisRequest = reqRepo.findById(id);
		if(thisRequest.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		thisRequest.get().setStatus(REJECTED);
		reqRepo.save(thisRequest.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequest(@PathVariable int id) {
		if (id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Request> thisRequest = reqRepo.findById(id);
		if(thisRequest.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqRepo.delete(thisRequest.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
}
