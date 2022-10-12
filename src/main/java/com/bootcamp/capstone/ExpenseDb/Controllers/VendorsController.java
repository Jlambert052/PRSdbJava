package com.bootcamp.capstone.ExpenseDb.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.capstone.ExpenseDb.Models.Po;
import com.bootcamp.capstone.ExpenseDb.Models.Poline;
import com.bootcamp.capstone.ExpenseDb.Models.Vendor;
import com.bootcamp.capstone.ExpenseDb.Repositories.ProductRepository;
import com.bootcamp.capstone.ExpenseDb.Repositories.RequestRepository;
import com.bootcamp.capstone.ExpenseDb.Repositories.RequestlineRepository;
import com.bootcamp.capstone.ExpenseDb.Repositories.VendorRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorsController {
	
	@Autowired
	private VendorRepository vendRepo;
	@Autowired
	private ProductRepository prodRepo;
	@Autowired
	private RequestlineRepository lineRepo;
	@Autowired 
	private RequestRepository requestRepo;
	
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
	
	@GetMapping("po/{vendorid}")
	public ResponseEntity<Po> CreatePo(@PathVariable int vendorid) {
		 Po po = new Po(); 
		 ArrayList<Poline> polines = new ArrayList<Poline>();
		 po.vendor = vendRepo.findById(vendorid).get();
		 	if(vendRepo.findById(vendorid).isEmpty()) {
		 		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 	}
		 	//collection of requests; iterate each request to check attached requestlines products and associated vendorIDs; add to collection
		 	//(ArrayList). Once collection is filled, you can iterate those for their product names, quantities, price, and line totals and 
		 	//place those into the Iterable Polines and get the PoTotal.
		 var approvedReqs = requestRepo.findAllByStatus("APPROVED").get();
		 	for(var appreq : approvedReqs) {
		 		var reqlines = lineRepo.findByRequestId(appreq.getId());
		 		for(var reqline : reqlines) {
		 			var prodId = reqline.getProduct().getId();
		 			var vendId = reqline.getProduct().getVendor().getId();
		 			var prodName = reqline.getProduct().getName();
		 			var quant = reqline.getQuantity();
		 			var price = reqline.getProduct().getPrice();
		 			var lineTotal =  quant * price;
		 			Poline poline = new Poline();
		 			poline.lineTotal = lineTotal;
		 			poline.product = prodName;
		 			poline.quantity = quant;
		 			poline.price = price;
		 				if(vendId == vendorid) {
		 					polines.add(poline);
		 				}
		 			}
		 	}
		 	po.poTotal = 0;
		 	for(var pline : polines) {
		 		double lineTotal = pline.lineTotal;
		 		po.poTotal += lineTotal;	
		 	}
		 	po.polines = polines;
		 	return new ResponseEntity<Po>(po, HttpStatus.CREATED);
	}
	
	
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
