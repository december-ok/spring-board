package com.december.board.repository;

import com.december.board.model.Writing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WritingRepository extends JpaRepository<Writing, Long> {
    Slice<Writing> findSliceBy(Pageable pageable);
}
