package com.example.bookapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookapi.entity.Author;
import com.example.bookapi.service.AuthorService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/author")
public class AuthorController {
	
	@Autowired
	private AuthorService service;
	
	@GetMapping
	public ResponseEntity<List<Author>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Author> findById(@PathVariable Long id) throws NotFoundException {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Author> save(@RequestBody Author author) {
		return new ResponseEntity<>(service.save(author), HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author author) throws NotFoundException {
		return new ResponseEntity<>(service.update(id, author), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
