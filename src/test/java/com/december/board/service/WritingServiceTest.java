package com.december.board.service;

import com.december.board.document.WritingDoc;
import com.december.board.model.Author;
import com.december.board.model.Writing;
import com.december.board.repository.WritingDocRepository;
import com.december.board.repository.WritingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WritingServiceTest {
    @Autowired
    WritingService writingService;

    @Autowired
    AuthorService authorService;

    @Autowired
    WritingDocRepository writingDocRepository;

    @Autowired
    WritingRepository writingRepository;

    @Test
    void getWritingById() {

    }

    @Test
    @Transactional
    void getWritingByIdAndAddView() {
//        given
        Optional<Writing> writing = writingService.postWriting("title", "content", "author");
        Long view = writing.get().getViews();

//        when
        writingService.getWritingByIdAndAddView(writing.get().getId());

//        then
        assertEquals(view + 1, writing.get().getViews());
    }

    @Test
    @Transactional
    void getWritingByIdAndAddViewFail(){
//        given
        Optional<Writing> writing = writingService.getWritingByIdAndAddView(-99L);

        assertEquals(Optional.empty(), writing);
    }


    @Test
    void putElasticTest(){
        Writing writing = Writing.builder()
                .id(99L)
                .title("동해물과")
                .content("백두산이")
                .build();

//        writingService.addToElasticSearch(writing);

        WritingDoc writingDoc = writingService.getWritingDocById(99L).get();

        assertEquals(writingDoc.getId(), 99L);
        assertEquals(writingDoc.getTitle(), "동해물과");
        assertEquals(writingDoc.getContent(), "백두산이");
    }

    @Test
    void getWritingDocSearchTest(){
        Iterable<WritingDoc> all = writingDocRepository.findAll();

        for(WritingDoc writingDoc : all){
            System.out.println(writingDoc.getTitle());
        }
    }

    @Test
    @Transactional
    void getWritingDocSearchTestWithRdb(){
//        Optional<Author> author = authorService.createAuthor("author", "author", "author");
//
//        for(int i=0; i<10; i++){
////            create writings
//            String randomTitle = "title" + i;
//            String randomContent = "content" + i;
//
//            writingService.postWriting(randomTitle, randomContent, author.get().getName());
//        }

        List<Writing> writingList = writingService.getPagedSearchList("title", 0L, 150L, "id");

//        System.out.println("size :"+writingList.get(0).getTitle());
        assertEquals(writingList.size(), 0);

        writingList = writingService.getPagedSearchList("content", 0L, 150L, "id");

//        System.out.println(writingList.get(5).getAuthor().getWritings().size());
        assertEquals(writingList.size(), 0);
    }

    @Test
    @Transactional
    void postWriting() {
        for(int i=0; i < 8021; i++){
            String randomTitle = "title" + i;
            String randomContent = "content" + i;
            String randomAuthor = "author" + i;

            Optional<Writing> writing = writingService.postWriting(randomTitle, randomContent, randomAuthor);

            writingService.getWritingByIdAndAddView(writing.get().getId());

            System.out.println(writing.get().getId());
        }
//        assertEquals(writing.getTitle(), "title");
    }
}