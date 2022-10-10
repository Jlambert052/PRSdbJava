package com.bootcamp.capstone.ExpenseDb.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bootcamp.capstone.ExpenseDb.Models.Request;

public interface RequestRepository extends CrudRepository<Request, Integer>{
	
	Optional<Iterable<Request>> findByStatusAndUserIdNot(String status, int userid);

}
