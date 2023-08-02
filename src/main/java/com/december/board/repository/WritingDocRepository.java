package com.december.board.repository;

import com.december.board.document.WritingDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WritingDocRepository extends ElasticsearchRepository<WritingDoc, Long> {
    @Query("{\n" +
            "     \"bool\": { \n" +
            "       \"should\": [ \n" +
            "          { \"match\": { \"title\": \"?0\" } },\n" +
            "          { \"match\": { \"title.nori\": \"?0\" } },\n" +
            "          { \"match\": { \"title.ngram\": \"?0\" } },\n" +
            "          { \"match\": { \"content\": \"?0\" } },\n" +
            "          { \"match\": { \"content.nori\": \"?0\" } },\n" +
            "          { \"match\": { \"content.ngram\": \"?0\" } }\n" +
            "       ]\n" +
            "     }\n" +
            "  }")
    Page<WritingDoc> findByQuery(String Query, Pageable pageable);
}
