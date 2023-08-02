package com.december.board.document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;


@Builder
@ToString
@Getter
@Setter
@Document(indexName = "writing")
public class WritingDoc {
    @Id
    private Long id;

    @Field(name = "title")
    private String title;

    @Field(name = "content")
    private String content;
}
