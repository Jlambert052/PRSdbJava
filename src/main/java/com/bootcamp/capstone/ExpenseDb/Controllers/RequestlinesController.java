package com.bootcamp.capstone.ExpenseDb.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.capstone.ExpenseDb.Models.Requestline;
import com.bootcamp.capstone.ExpenseDb.Repositories.ProductRepository;
import com.bootcamp.capstone.ExpenseDb.Repositories.RequestRepository;
import com.bootcamp.capstone.ExpenseDb.Repositories.RequestlineRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/requestlines")
public class RequestlinesController {
	
	@Autowired
	private RequestlineRepository lineRepo;
	@Autowired
	private RequestRepository reqRepo;
	@SuppressWarnings("unused")
	@Autowired
	private ProductRepository prodRepo;
	
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity RecalcRequestTotal(int requestid) {
		var req = reqRepo.findById(requestid);
		if(req.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var request = req.get();
		var reqTotal = 0;
		Iterable<Requestline> ordLines = lineRepo.findByRequestId(request.getId());
		for(var ordLine : ordLines) {
			reqTotal += ordLine.getQuantity() * ordLine.getProduct().getPrice();
		}
		request.setTotal(reqTotal);
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Requestline>> getAllRequestlines() {
		Iterable<Requestline> requestlines = lineRepo.findAll();
		return new ResponseEntity<Iterable<Requestline>>(requestlines, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Requestline> getRequestlineByPK(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Requestline> thisReqline = lineRepo.findById(id);
		if(thisReqline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Requestline>(thisReqline.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Requestline> postRequestline(@RequestBody Requestline requestline) throws Exception{
		if(requestline.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Requestline thisReqline = lineRepo.save(requestline);
		var RespEntity = this.RecalcRequestTotal(thisReqline.getRequest().getId());
		if(RespEntity.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Recalculate function failed.");
		}
		return new ResponseEntity<Requestline>(thisReqline, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequestline(@RequestBody Requestline requestline, @PathVariable int id) throws Exception{
		if(id != requestline.getId() ||id ==0 ||id <0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Requestline> thisReqline = lineRepo.findById(id);
		if(thisReqline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		lineRepo.save(requestline);
		var RespEntity = this.RecalcRequestTotal(thisReqline.get().getRequest().getId());
		if(RespEntity.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Recalculate function failed");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequestline(@PathVariable int id) throws Exception{
		if(id == 0 ||id < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Requestline> thisReqline = lineRepo.findById(id);
		if(thisReqline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		lineRepo.delete(thisReqline.get());
		var RespEntity = this.RecalcRequestTotal(thisReqline.get().getRequest().getId());
		if(RespEntity.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Recalculate function failed");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
