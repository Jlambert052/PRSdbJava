package com.bootcamp.capstone.ExpenseDb.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bootcamp.capstone.ExpenseDb.Models.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	Optional<User> findByUsernameAndPassword(String username, String password);
}
