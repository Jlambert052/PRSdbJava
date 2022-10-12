package com.bootcamp.capstone.ExpenseDb.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bootcamp.capstone.ExpenseDb.Models.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{
	
	//Optional<Product> findByVendorid(int vendorid);
	
	Product findByVendorId(int vendorid);

}
