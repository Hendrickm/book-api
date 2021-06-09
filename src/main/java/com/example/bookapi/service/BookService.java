package com.example.bookapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookapi.entity.Book;
import com.example.bookapi.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;

	public List<Book> findAll() {
		return repository.findAll();
	}

	public Book findById(Long id) {
		return repository.getById(id);
	}

	public Book save(Book book) {
		return repository.save(book);
	}
	
	public Book update(Long id, Book book) {
		return repository.save(book);
	}

	public void delete(Long id) {
		repository.deleteById(id);;
	}

}
