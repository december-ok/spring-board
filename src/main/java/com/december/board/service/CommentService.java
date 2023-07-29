package com.december.board.service;

import com.december.board.model.Author;
import com.december.board.model.Comment;
import com.december.board.model.Writing;
import com.december.board.repository.AuthorRepository;
import com.december.board.repository.CommentRepository;
import com.december.board.repository.WritingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;
    private final WritingRepository writingRepository;

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Optional<Comment> createComment(String content, Long writingId, String authorName) {
        Author author = authorRepository.findByName(authorName).orElse(null);
        Writing writing = writingRepository.findById(writingId).orElse(null);

        if(author == null || writing == null) {
            return Optional.empty();
        }

        Comment comment = Comment.builder()
                .content(content)
                .author(author)
                .writing(writing)
                .date(new Date())
                .build();

        author.getComments().add(comment);
        writing.getComments().add(comment);
        comment = commentRepository.save(comment);

        authorRepository.save(author);
        writingRepository.save(writing);

        return Optional.of(comment);
    }
}
