package com.december.board.service;

import com.december.board.model.Writing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WritingServiceTest {
    @Autowired
    WritingService writingService;

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
//    @Transactional
    void postWriting() {
        for(int i=0; i < 8021; i++){
            String randomTitle = "title" + i;
            String randomContent = "content" + i;
            String randomAuthor = "author" + i;

            Optional<Writing> writing = writingService.postWriting(randomTitle, randomContent, randomAuthor);

            writingService.getWritingById(writing.get().getId());

            System.out.println(writing.get().getId());
        }
//        assertEquals(writing.getTitle(), "title");
    }
}