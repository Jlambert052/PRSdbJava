package com.bootcamp.capstone.PRSDb.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.capstone.PRSDb.Models.User;
import com.bootcamp.capstone.PRSDb.Repositories.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UsersController {

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<User>> getUsers() {
		Iterable<User> users = userRepo.findAll();
		return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> getUserByPK(@PathVariable int id) {
		if(id == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<User> thisUser = userRepo.findById(id);
		if(thisUser.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(thisUser.get(), HttpStatus.OK);
	}
	
	@GetMapping("{username}/{password}")
	public ResponseEntity<User> getLoginUser(@PathVariable String username, @PathVariable String password) {
		Optional<User> login = userRepo.findByUsernameAndPassword(username, password);
		if(login.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(login.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<User> postUser(@RequestBody User user) {
		if(user.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User newUser = userRepo.save(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putUser(@RequestBody User user, @PathVariable int id) {
		if(id != user.getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<User> thisUser = userRepo.findById(id);
		if(thisUser.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepo.save(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteUser(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<User> thisUser = userRepo.findById(id);
		if(thisUser.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepo.delete(thisUser.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
