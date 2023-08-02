package com.december.board.service;

import com.december.board.document.WritingDoc;
import com.december.board.model.Author;
import com.december.board.model.Writing;
import com.december.board.repository.AuthorRepository;
import com.december.board.repository.WritingDocRepository;
import com.december.board.repository.WritingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class WritingService {
    private final WritingRepository writingRepository;
    private final AuthorRepository authorRepository;
    private final WritingDocRepository writingDocRepository;

    public Optional<Writing> getWritingByIdAndAddView(Long id) {
        int changed = writingRepository.addView(id);
        if(changed == 0){
            return Optional.empty();
        }
        Optional<Writing> writingOptional = writingRepository.findById(id);

        return writingOptional;
    }

    public Optional<Writing> postWriting(String title, String content, String authorName){
        Optional<Author> authorOptional = authorRepository.findByName(authorName);

        if(authorOptional.isEmpty()){
            return Optional.empty();
        }

        Author author = authorOptional.get();

        Writing writing = Writing.builder()
                .title(title)
                .content(content)
                .author(author)
                .date(new Date())
                .views(0L)
                .likes(0L)
                .dislikes(0L)
                .build();

        writing = writingRepository.save(writing);

        addToElasticSearch(writing);

        author.getWritings().add(writing);
        authorRepository.save(author);

        System.out.println("author.getWritings().size() = " + author.getWritings().size());

        return Optional.of(writing);
    }

    public WritingDoc addToElasticSearch(Writing writing){
        WritingDoc writingDoc = WritingDoc.builder()
                .id(writing.getId())
                .title(writing.getTitle())
                .content(writing.getContent())
                .build();

        writingDoc = writingDocRepository.save(writingDoc);

        return writingDoc;
    }

    public Optional<WritingDoc> getWritingDocById(Long id){
        Optional<WritingDoc> writingDocOptional = writingDocRepository.findById(id);

        return writingDocOptional;
    }

    public List<Writing> getPagedSearchList(String query, Long page, Long size, String sortBy){
        PageRequest pageRequest = PageRequest.of(page.intValue(), size.intValue());
        Page<WritingDoc> writingSlice = writingDocRepository.findByQuery(query, pageRequest);

        System.out.println("HERE: 1");

        List<WritingDoc> writingDocList = writingSlice.getContent();
        List<Writing> writingList = new ArrayList<Writing>();

        writingDocList.stream().forEach(writingDoc -> {
            Optional<Writing> writingOptional = writingRepository.findById(writingDoc.getId());
            writingOptional.ifPresent(writing -> writingList.add(writing));
        });

        System.out.println("HERE: 2");


        return writingList;
    }


    public List<Writing> getPagedList(Long page, Long size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page.intValue(), size.intValue(), Sort.by(sortBy).descending());
        Slice<Writing> writingSlice = writingRepository.findSliceBy(pageRequest);

        List<Writing> writingList = writingSlice.getContent();

        return writingList;
    }
}
