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

import com.example.bookapi.entity.Book;
import com.example.bookapi.service.BookService;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@GetMapping
	public ResponseEntity<List<Book>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Book> findById(@PathVariable Long id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Book> save(@RequestBody Book book) {
		return new ResponseEntity<>(service.save(book), HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Book> save(@PathVariable Long id, @RequestBody Book book) {
		return  new ResponseEntity<>(service.update(id, book), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
