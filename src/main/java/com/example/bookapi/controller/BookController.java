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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/book")
@Api(value="Books")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@GetMapping
	@ApiOperation(value="Returns a list of books")
	public ResponseEntity<List<Book>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@ApiOperation(value="Returns a single book")
	public ResponseEntity<Book> findById(@PathVariable Long id)  {
		Book book = service.findById(id);
		if (book != null) {
			return new ResponseEntity<>(book, HttpStatus.OK);			
		} 
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
	}
	
	@PostMapping
	@ApiOperation(value="Saves a book")
	public ResponseEntity<Book> save(@RequestBody Book book) {
		return new ResponseEntity<>(service.save(book), HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	@ApiOperation(value="Updates a book")
	public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) throws NotFoundException {
		return new ResponseEntity<>(service.update(id, book), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	@ApiOperation(value="Deletes a book")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
