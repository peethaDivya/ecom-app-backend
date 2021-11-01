package com.ouhamza.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ouhamza.ecommerce.dao.GroceryRepository;
import com.ouhamza.ecommerce.entity.Grocery;



@RestController
@CrossOrigin(origins = "http://grocery-delivery.s3-website.us-east-2.amazonaws.com/")
@RequestMapping(path = "Grocerys")
public class GroceryController {
	
	private byte[] bytes;

	@Autowired
	private GroceryRepository groceryRepository;
	
	@GetMapping("/get")
	public List<Grocery> getGrocerys() {
		return groceryRepository.findAll();
	}
	
	@GetMapping(value = "/get/{id}")
    public Optional<Grocery> getGrocery(@PathVariable("id")  long id) {
        return groceryRepository.findById(id);
    }
	
	
	@PostMapping("/upload")
	public void uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		this.bytes = file.getBytes();
	}

	@PostMapping("/add")
	public void createGrocery(@RequestBody Grocery grocery) throws IOException {
		grocery.setPicByte(this.bytes);
		groceryRepository.save(grocery);
		this.bytes = null;
	}
	
	@DeleteMapping(path = { "/{id}" })
	public Grocery deleteGrocery(@PathVariable("id") long id) {
		Grocery grocery = groceryRepository.getOne(id);
		groceryRepository.deleteById(id);
		return grocery;
	}
	
	@PutMapping("/update")
	public void updateGrocery(@RequestBody Grocery grocery) {
		groceryRepository.save(grocery);
	}
}
