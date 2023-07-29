package com.december.board.service;

import com.december.board.model.Author;
import com.december.board.model.Writing;
import com.december.board.repository.AuthorRepository;
import com.december.board.repository.WritingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WritingService {
    private final WritingRepository writingRepository;
    private final AuthorRepository authorRepository;

    public Optional<Writing> getWritingById(Long id) {
        Optional<Writing> writingOptional = writingRepository.findById(id);

        return writingOptional;
    }
    public Optional<Writing> getWritingByIdAndAddView(Long id) {
        Optional<Writing> writingOptional = writingRepository.findById(id);

        if (writingOptional.isPresent()) {
            Writing writing = writingOptional.get();
            writing.setViews(writing.getViews() + 1);
            writingRepository.save(writing);
        }

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

        author.getWritings().add(writing);
        authorRepository.save(author);

        System.out.println("author.getWritings().size() = " + author.getWritings().size());

        return Optional.of(writing);
    }

    public List<Writing> getPagedList(Long page, Long size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page.intValue(), size.intValue(), Sort.by(sortBy).descending());
        Slice<Writing> writingSlice = writingRepository.findSliceBy(pageRequest);

        List<Writing> writingList = writingSlice.getContent();

        return writingList;
    }
}
