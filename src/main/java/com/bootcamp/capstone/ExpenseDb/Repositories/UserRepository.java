package com.bootcamp.capstone.ExpenseDb.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bootcamp.capstone.ExpenseDb.Models.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	Optional<User> findByUsernameAndPassword(String username, String password);
	//FROM clause points to @entity name, not table.
	@Query("SELECT u FROM Users u WHERE u.username = ?1 and u.password = ?2")
	Optional<User> findByLogin(String username, String password);
}
