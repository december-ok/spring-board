package com.december.board.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "writing")
public class Writing {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;

        @Column(name = "content", columnDefinition = "TEXT")
        private String content;

        @Column(name = "date")
        private Date date;

        @Column(name = "views")
        private Long views;

        @Column(name = "likes")
        private Long likes;

        @Column(name = "dislikes")
        private Long dislikes;

        @ManyToOne
        private Author author;

        @OneToMany(mappedBy = "writing")
        private List<Comment> comments;
}
