package com.december.board.repository;

import com.december.board.model.Writing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WritingRepository extends JpaRepository<Writing, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Writing w set w.views = w.views + 1 where w.id = :id")
    int addView(Long id);

    Slice<Writing> findSliceBy(Pageable pageable);

    @Query("select w from Writing w where w.id in :ids")
    List<Writing> findByIdIn(List<Long> ids);
}
