package com.example.bookapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookapi.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

}
