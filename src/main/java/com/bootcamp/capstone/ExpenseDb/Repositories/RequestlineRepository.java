package com.bootcamp.capstone.ExpenseDb.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.bootcamp.capstone.ExpenseDb.Models.Requestline;

public interface RequestlineRepository extends CrudRepository<Requestline, Integer>{

	Iterable<Requestline> findByRequestId(int requestid);
}
