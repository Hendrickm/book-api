package com.example.bookapi.repository;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookapi.entity.Author;
import com.example.bookapi.entity.Book;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookRepositoryTest {
	
	@Autowired
    private BookRepository bookRepository;
	
	Book hobbit;
	Book gameOfThrones;
	
	@BeforeEach
	public void setup() {
		
		
		hobbit = Book.builder()
				.id(1L)
				.author(new Author(1L, "JRR Tolkien"))
				.title("The Hobbit")
				.genre("Fantasy")
				.releaseYear(1946)
				.build();
		
		gameOfThrones = Book.builder()
				.id(2L)
				.author(new Author(2L, "George RR Martin"))
				.title("A Game of Thrones")
				.genre("Fantasy")
				.releaseYear(1946)
				.build();
		
		bookRepository.save(hobbit);
		bookRepository.save(gameOfThrones);
		
		
	}
	
	@Test
    public void shouldReturnList_WhenFindAll() {
        List<Book> books = bookRepository.findAll();
       
    	Assertions.assertEquals(2, books.size());        
    }
	
	@Test
    public void shouldReturnSingle_WhenFindById() {
		Optional<Book> bookDb = bookRepository.findById(hobbit.getId());
        if (bookDb.isPresent()) {
        	Assertions.assertEquals(bookDb.get().getId(), hobbit.getId());
        }      
    }

	@Test
    public void shouldCreateEntity_WhenInsertBook() {
		Book danceWithDragons = Book.builder()
				.id(3L)
				.author(new Author(2L, "George RR Martin"))
				.title("A Dance with Dragons")
				.genre("Fantasy")
				.releaseYear(1946)
				.build();
		
		bookRepository.save(danceWithDragons);
        
        Optional<Book> bookDb = bookRepository.findById(danceWithDragons.getId());
        if (bookDb.isPresent()) {
        	Assertions.assertEquals(bookDb.get().getId(), danceWithDragons.getId());
        	Assertions.assertEquals(bookDb.get().getTitle(), danceWithDragons.getTitle());
        }
    }
	
	@Test
    public void shouldUpdateEntity_WhenUpdateBook() {
        Optional<Book> bookDb = bookRepository.findById(gameOfThrones.getId());
        bookDb.get().setTitle("The Winds of Winter");
        
        bookRepository.save(bookDb.get());
        
        Optional<Book> bookUpdated = bookRepository.findById(gameOfThrones.getId());
        
        if (bookUpdated.isPresent()) {
        	Assertions.assertEquals(bookUpdated.get().getId(), bookDb.get().getId());
        	Assertions.assertEquals(bookUpdated.get().getTitle(), "The Winds of Winter");
        }
    }
	
	@Test
    public void shouldRemoveEntity_WhenDeleteBook() {
        bookRepository.deleteById(1L);
        
        Optional<Book> bookDb = bookRepository.findById(1L);
        
        Assertions.assertTrue(bookDb.isEmpty());
    }
}
