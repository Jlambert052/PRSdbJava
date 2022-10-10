package com.bootcamp.capstone.ExpenseDb.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.capstone.ExpenseDb.Models.Po;
import com.bootcamp.capstone.ExpenseDb.Models.Vendor;
import com.bootcamp.capstone.ExpenseDb.Repositories.VendorRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorsController {
	
	@Autowired
	private VendorRepository vendRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getVendors() {
		Iterable<Vendor> vendors = vendRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(vendors, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Vendor> getVendorByPK(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Vendor> thisVendor = vendRepo.findById(id);
		if(thisVendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(thisVendor.get(), HttpStatus.OK);
	}
	//PO in progress.
	/*
	@GetMapping("po/{vendorid}")
	public ResponseEntity<Po> CreatePo(int vendorId) {
		 Po po = new Po(); 
		 po.vendor = vendRepo.findById(vendorId);
		 var poReq = 
	}
	*/
	
	@PostMapping
	public ResponseEntity<Vendor> postVendor(@RequestBody Vendor vendor) {
		if(vendor.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Vendor thisVendor = vendRepo.save(vendor);
		return new ResponseEntity<Vendor>(thisVendor, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putVendor(@RequestBody Vendor vendor, @PathVariable int id) {
		if(id != vendor.getId() | id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Vendor> thisVendor = vendRepo.findById(id);
		if(thisVendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		vendRepo.save(vendor);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteVendor(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Vendor> thisVendor = vendRepo.findById(id);
		if(thisVendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		vendRepo.delete(thisVendor.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
