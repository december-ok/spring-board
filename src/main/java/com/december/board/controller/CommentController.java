package com.december.board.controller;

import com.december.board.model.Comment;
import com.december.board.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public String postComment(HttpServletRequest httpRequest) {
        String content = httpRequest.getParameter("content");
        Long writingId = Long.valueOf(httpRequest.getParameter("writingId"));
        String authorName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Comment> comment = commentService.createComment(content, writingId, authorName);

        if(comment.isEmpty()) {
            return "error";
        }

        return "redirect:/writing/" + writingId;
    }
}
