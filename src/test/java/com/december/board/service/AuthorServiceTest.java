package com.december.board.service;

import com.december.board.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorServiceTest {
    @Autowired
    AuthorService authorService;
    @Test
//    @Transactional
    void createAuthor() {
//        Author author =  authorService.createAuthor("name", "email","1234");
//
//        Author author1 = authorService.getAuthorById(author.getId()).get();
//
//        assertEquals(author.getName(), author1.getName());
    }
}