package com.bootcamp.capstone.PRSDb.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.capstone.PRSDb.Models.Product;
import com.bootcamp.capstone.PRSDb.Repositories.ProductRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductsController {

	@Autowired
	private ProductRepository prodRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Product>> getAllProducts() {
		Iterable<Product> products = prodRepo.findAll();
		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> getProductByPK(@PathVariable int id) {
		if(id ==0 || id <0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Product> thisProduct = prodRepo.findById(id);
		if(thisProduct.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(thisProduct.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Product> postProduct(@RequestBody Product product) {
		if(product.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Product newProduct = prodRepo.save(product);
		return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putProduct(@RequestBody Product product, @PathVariable int id) {
		if(id != product.getId() ||id ==0 || id < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Product> thisProduct = prodRepo.findById(id);
		if(thisProduct.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		prodRepo.save(product);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		if(id ==0 || id < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Product> thisProduct = prodRepo.findById(id);
		if(thisProduct.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		prodRepo.delete(thisProduct.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
