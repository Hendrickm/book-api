package com.example.bookapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookapi.entity.Book;
import com.example.bookapi.repository.BookRepository;

import javassist.NotFoundException;

@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;

	public List<Book> findAll() {
		return repository.findAll();
	}

	public Book findById(Long id) {
		Optional<Book> book = repository.findById(id);
		return book.isPresent() 
				? book.get() : null;
	}

	public Book save(Book book) {
		return repository.save(book);
	}
	
	public Book update(Long id, Book book) throws NotFoundException {
		Book bookDb = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book not found"));
		bookDb.setTitle(book.getTitle());
		bookDb.setGenre(book.getGenre());
		bookDb.setAuthor(book.getAuthor());
		bookDb.setReleaseYear(book.getReleaseYear());
		return repository.save(bookDb);
	}

	public void delete(Long id) {
		repository.deleteById(id);;
	}

}
