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
import com.example.bookapi.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.bookapi.service.AuthorService;


import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import javassist.NotFoundException;

@WebMvcTest
public class AuthorControllerTest {
	
	@Autowired
	private AuthorController authorController;
	
	@MockBean
	private BookService bookService;
	
	@MockBean
	private AuthorService authorService;
	
	private Author author;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.authorController);
		author = Author.builder()
				.id(1L)
				.name("JRR Tolkien")
				.build();	
	}
	
	@Test
	public void shouldReturnSuccess_WhenFindAllBooks() {
		List<Author> authors = new ArrayList<>(); 
		authors.add(author);

		when(authorService.findAll())
			.thenReturn(authors);
		
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
			.when()
				.get("/author")
			.then()
				.extract()
				.response();
		
		Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
	    Assertions.assertEquals(1, response.jsonPath().getList("$").size());
	}
	
	@Test
	public void shouldReturnSuccess_WhenFindById() throws NotFoundException {
		when(authorService.findById(1L))
			.thenReturn(author);
		
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
			.when()
				.get("/author/{id}", 1L)
			.then()
				.extract()
				.response();
	
		Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
	    Assertions.assertEquals("1", response.jsonPath().getString("id"));;
	}
	
	@Test
	public void shouldReturnNotFound_WhenFindById() throws NotFoundException {
		when(authorService.findById(2L))
			.thenReturn(null);
		
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
			.when()
				.get("/author/{id}", 2L)
			.then()
				.extract()
				.response();
			
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.statusCode());
	}
	
	@Test
	public void shouldReturnSuccess_WhenSave() throws JsonProcessingException {
		when(authorService.save(author))
			.thenReturn(author);
		
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
	            .body(toJsonString(author))
			.when()
				.post("/author")
			.then()
				.extract()
				.response();
		
		Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
        Assertions.assertEquals("1", response.jsonPath().getString("id"));
	}
	
	@Test
	public void shouldReturnSuccess_WhenUpdate() throws JsonProcessingException, NotFoundException {
		when(authorService.update(1L, author))
			.thenReturn(author);
		
		MockMvcResponse response = given()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
	            .body(toJsonString(author))
			.when()
				.put("/author/{id}", 1L)
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
	            .delete("/author/{id}", 1)
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
