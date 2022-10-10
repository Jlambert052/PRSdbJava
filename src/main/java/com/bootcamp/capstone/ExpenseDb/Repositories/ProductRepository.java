package com.bootcamp.capstone.ExpenseDb.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.bootcamp.capstone.ExpenseDb.Models.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{

}
