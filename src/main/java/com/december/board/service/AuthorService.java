package com.december.board.service;

import com.december.board.model.Author;
import com.december.board.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
//    private final WritingRepository writingRepository;

    public Author createAuthor(String name, String email, String password) {
        Author author = Author.builder()
                .name(name)
                .email(email)
                .password(password)
                .writings(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();


        author = authorRepository.save(author);
        return author;
    }

    public Optional<Author> getAuthorById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        return authorOptional;
    }
}
