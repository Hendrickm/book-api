package com.example.bookapi.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import com.example.bookapi.entity.Author;
import com.example.bookapi.entity.Book;
import com.example.bookapi.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.bookapi.service.AuthorService;


import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import javassist.NotFoundException;

@WebMvcTest
public class BookControllerTest {
	
	@Autowired
	private BookController bookController;
	
	@MockBean
	private BookService bookService;
	
	@MockBean
	private AuthorService authorService;
	
	private Book book;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.bookController);
		book = Book.builder()
				.id(1L)
				.author(new Author(1L, "JRR Tolkien"))
				.title("The Hobbit")
				.genre("Fantasy")
				.releaseYear(1946)
				.build();	
	}
	
	@Test
	public void shouldReturnSuccess_WhenFindAllBooks() {
		List<Book> books = new ArrayList<>(); 
		books.add(book);

		when(bookService.findAll())
			.thenReturn(books);
		
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
			.when()
				.get("/book")
			.then()
				.extract()
				.response();
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
	    Assertions.assertEquals(1, response.jsonPath().getList("$").size());
	}
	
	@Test
	public void shouldReturnSuccess_WhenFindById() throws NotFoundException {
		when(bookService.findById(1L))
			.thenReturn(book);
		
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
			.when()
				.get("/book/{id}", 1L)
			.then()
				.extract()
				.response();
	
		Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
	    Assertions.assertEquals("1", response.jsonPath().getString("id"));;
	}
	
	@Test
	public void shouldReturnNotFound_WhenFindById() throws NotFoundException {
		when(bookService.findById(2L))
			.thenReturn(null);
		
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
			.when()
				.get("/book/{id}", 2L)
			.then()
				.extract()
				.response();
			
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.statusCode());
	}
	
	@Test
	public void shouldReturnSuccess_WhenSave() throws JsonProcessingException {
		when(bookService.save(book))
			.thenReturn(book);
		
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
	            .body(toJsonString(book))
			.when()
				.post("/book")
			.then()
				.extract()
				.response();
		
		Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        Assertions.assertEquals("1", response.jsonPath().getString("id"));
	}
	
	@Test
	public void shouldReturnSuccess_WhenUpdate() throws JsonProcessingException, NotFoundException {
		when(bookService.update(1L, book))
			.thenReturn(book);
		
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
	            .body(toJsonString(book))
			.when()
				.put("/book/{id}", 1L)
			.then()
				.extract()
				.response();
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
        Assertions.assertEquals("1", response.jsonPath().getString("id"));
	}
	
	@Test
	public void shouldReturnSuccess_WhenDelete() throws NotFoundException {
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
	        .when()
	            .delete("/book/{id}", 1)
	        .then()
	            .extract()
				.response();
	
		Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
	}
	
	private static String toJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	


}
