package com.bootcamp.capstone.PRSDb.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.bootcamp.capstone.PRSDb.Models.Requestline;

public interface RequestlineRepository extends CrudRepository<Requestline, Integer>{

	Iterable<Requestline> findByRequestId(int requestid);
	
	Requestline findByProductId(int productid);
}
