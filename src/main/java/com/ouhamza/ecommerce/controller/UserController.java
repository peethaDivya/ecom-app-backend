package com.ouhamza.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ouhamza.ecommerce.dao.UserRepository;
import com.ouhamza.ecommerce.entity.User;


@RestController
@CrossOrigin(origins = "http://grocery-delivery.s3-website.us-east-2.amazonaws.com/")
@RequestMapping(path = "users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/get")
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	@RequestMapping(value ="/add" , method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void createUser(@RequestBody User user) {
		userRepository.save(user);
	}
	
	@DeleteMapping(path = { "/{id}" })
	public User deleteUser(@PathVariable("id") long id) {
		System.out.print("id");
		User user = userRepository.getOne(id);
		userRepository.deleteById(id);
		return user;
	}

}