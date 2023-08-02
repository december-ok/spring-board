package com.december.board.controller;

import com.december.board.model.Author;
import com.december.board.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/{id}")
    public String getAuthor(@PathVariable Long id, Model model) {
        Optional<Author> authorOptional = authorService.getAuthorById(id);

        if(!authorOptional.isPresent()) {
            return "error";
        }

        Author author = authorOptional.get();
        author.getWritings().sort((a, b) -> b.getId().compareTo(a.getId()));
        model.addAttribute("author", author);

        return "author";
    }
}
