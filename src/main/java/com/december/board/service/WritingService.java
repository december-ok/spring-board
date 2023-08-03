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

@Service
@AllArgsConstructor
@Transactional
public class WritingService {
    private final WritingRepository writingRepository;
    private final AuthorRepository authorRepository;
    private final WritingDocRepository writingDocRepository;

    public Optional<Writing> getWritingByIdAndAddView(Long id) {
        int changed = writingRepository.addView(id);
        if(changed == 0){
            return Optional.empty();
        }

        return writingRepository.findById(id);
    }

    @Transactional
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

        return Optional.of(writing);
    }

    public void addToElasticSearch(Writing writing){
        WritingDoc writingDoc = WritingDoc.builder()
                .id(writing.getId())
                .title(writing.getTitle())
                .content(writing.getContent())
                .build();

        writingDocRepository.save(writingDoc);
    }

    public Optional<WritingDoc> getWritingDocById(Long id){
        return writingDocRepository.findById(id);
    }

    public List<Writing> getPagedSearchList(String query, Long page, Long size, String sortBy){
        PageRequest pageRequest = PageRequest.of(page.intValue(), size.intValue(), Sort.by(sortBy).descending());
        Page<WritingDoc> writingSlice = writingDocRepository.findByQuery(query, pageRequest);

        List<WritingDoc> writingDocList = writingSlice.getContent();
        List<Writing> writingList = new ArrayList<>();

        writingDocList.forEach(writingDoc -> {
            Optional<Writing> writingOptional = writingRepository.findById(writingDoc.getId());
            writingOptional.ifPresent(writingList::add);
        });

        return writingList;
    }

    public List<Writing> getPagedList(Long page, Long size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page.intValue(), size.intValue(), Sort.by(sortBy).descending());
        Slice<Writing> writingSlice = writingRepository.findSliceBy(pageRequest);

        return writingSlice.getContent();
    }
}
