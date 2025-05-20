package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    // 이름으로 출판사 찾기
    Optional<Publisher> findByName(String name);

    // 출판사 + 도서 리스트 즉시 로딩
    @Query("SELECT p FROM Publisher p LEFT JOIN FETCH p.books WHERE p.id = :id")
    Optional<Publisher> findByIdWithBooks(@Param("id") Long id);

    // 이름 중복 검사
    boolean existsByName(String name);
}
