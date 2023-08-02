package com.december.board.controller;

import com.december.board.model.Author;
import com.december.board.model.Writing;
import com.december.board.service.AuthorService;
import com.december.board.service.WritingService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class HomeContorller {
    private WritingService writingService;
    private final AuthorService authorService;

    @RequestMapping("/")
    public String home(
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) String sortBy,
            Model model) {
        if (page == null) page = 1L;
        if(page < 1) page = 1L;

        if(sortBy == null) sortBy = "id";

        List<Writing> writingList = writingService.getPagedList(page-1, 10L, sortBy);


        String authorName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(authorName.equals("anonymousUser")) authorName = null;

        model.addAttribute("authorName", authorName);
        model.addAttribute("writingList", writingList);
        model.addAttribute("page", page);
        model.addAttribute("sortBy", sortBy);
        return "home";
    }

    @GetMapping("/post")
    public String post(Model model) {
        String authorName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("authorName", authorName);

        return "post";
    }

    @GetMapping("/join")
    public String getJoin() {
        return "join";
    }

    @PostMapping("/join")
    public String postJoin(String name, String email, String password, Model model) {
        Optional<Author> authorOptional = authorService.createAuthor(name, email, password);

        if (authorOptional.isEmpty()) {
            model.addAttribute("errMsg", "이미 존재하는 이름 또는 이메일입니다.");
            return "join";
        }

        return "redirect:/";
    }
}
