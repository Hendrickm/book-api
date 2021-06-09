package com.example.bookapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookapi.entity.Author;
import com.example.bookapi.repository.AuthorRepository;

import javassist.NotFoundException;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository repository;

	public List<Author> findAll() {
		return repository.findAll();
	}

	public Author findById(Long id) throws NotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book not found"));
	}

	public Author save(Author author) {
		return repository.save(author);
	}
	
	public Author update(Long id, Author author) throws NotFoundException {
		Author authorDb = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book not found"));
		authorDb.setName(authorDb.getName());
		return repository.save(authorDb);
	}

	public void delete(Long id) {
		repository.deleteById(id);;
	}
}
