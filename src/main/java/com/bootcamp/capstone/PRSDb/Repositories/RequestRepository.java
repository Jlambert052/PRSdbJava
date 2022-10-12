package com.bootcamp.capstone.PRSDb.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bootcamp.capstone.PRSDb.Models.Request;

public interface RequestRepository extends CrudRepository<Request, Integer>{
	
	Optional<Iterable<Request>> findByStatusAndUserIdNot(String status, int userid);

	Optional<Iterable<Request>> findAllByStatus(String status);
}
