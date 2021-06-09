package com.example.bookapi.repository;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookapi.entity.Author;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AuthorRepositoryTeste {
	
	@Autowired
    private AuthorRepository authorRepository;
	
	Author authorTolkien;
	Author authorMartin;
	
	@BeforeEach
	public void setup() {
		
		authorTolkien = new Author(1L, "JRR Tolkien");
		authorMartin = new Author(2L, "George RR Martin");
        authorRepository.save(authorTolkien);
        authorRepository.save(authorMartin);
	}
	
	@Test
    public void shouldReturnList_WhenFindAll() {
       
        
        List<Author> authors = authorRepository.findAll();
       
    	Assertions.assertEquals(2, authors.size());        
    }
	
	@Test
    public void shouldReturnSingleEntity_WhenFindById() {
        
        Optional<Author> authorDb = authorRepository.findById(authorTolkien.getId());
        if (authorDb.isPresent()) {
        	Assertions.assertEquals(authorDb.get().getId(), authorTolkien.getId());
        	Assertions.assertEquals(authorDb.get().getName(), authorTolkien.getName());
        }
       
    }
	
	@Test
    public void shouldCreateEntity_WhenInsertAuthor() {
        Author authorLewis = new Author(3L, "CS Lewis");
        authorRepository.save(authorLewis);
        
        Optional<Author> authorDb = authorRepository.findById(authorLewis.getId());
        if (authorDb.isPresent()) {
        	Assertions.assertEquals(authorDb.get().getId(), authorLewis.getId());
        	Assertions.assertEquals(authorDb.get().getName(), authorLewis.getName());
        }
    }
	
	@Test
    public void shouldUpdateEntity_WhenUpdateAuthor() {
       
        Optional<Author> authorDb = authorRepository.findById(authorTolkien.getId());
        authorDb.get().setName("George RR Martin");
        
        authorRepository.save(authorDb.get());
        
        Optional<Author> authorUpdated = authorRepository.findById(authorTolkien.getId());
        
        if (authorUpdated.isPresent()) {
        	Assertions.assertEquals(authorUpdated.get().getId(), 1L);
        	Assertions.assertEquals(authorUpdated.get().getName(), "George RR Martin");
        }
    }
	
	@Test
    public void shouldRemoveEntity_WhenDeleteAuthor() {
		Author authorLewis = new Author(3L, "CS Lewis");
        authorRepository.save(authorLewis);
        
        authorRepository.deleteById(3L);
        
        Optional<Author> authorDb = authorRepository.findById(3L);
        
        Assertions.assertTrue(authorDb.isEmpty());
    }
	
	
}
