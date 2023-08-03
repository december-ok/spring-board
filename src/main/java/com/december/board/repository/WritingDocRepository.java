package com.december.board.repository;

import com.december.board.document.WritingDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WritingDocRepository extends ElasticsearchRepository<WritingDoc, Long> {
    @Query("""
            {
                 "bool": {
                   "should": [
                      { "match": { "title": "?0" } },
                      { "match": { "title.nori": "?0" } },
                      { "match": { "title.ngram": "?0" } },
                      { "match": { "content": "?0" } },
                      { "match": { "content.nori": "?0" } },
                      { "match": { "content.ngram": "?0" } }
                   ]
                 }
            }""")
    Page<WritingDoc> findByQuery(String Query, Pageable pageable);
}
