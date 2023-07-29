package com.december.board.service;

import com.december.board.model.Author;
import com.december.board.model.Writing;
import com.december.board.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Author> createAuthor(String name, String email, String password) {
        if (authorRepository.findByName(name).isPresent()) {
            return Optional.empty();
        }

        if (authorRepository.findByEmail(email).isPresent()) {
            return Optional.empty();
        }

        Author author = Author.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .writings(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();


        author = authorRepository.save(author);
        return Optional.of(author);
    }

    public Optional<Author> getAuthorById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        return authorOptional;
    }
}
