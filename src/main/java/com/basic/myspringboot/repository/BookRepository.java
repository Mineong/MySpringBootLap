package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookDetail WHERE b.id = :id")
    Optional<Book> findByIdWithBookDetail(@Param("id") Long id);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookDetail WHERE b.isbn = :isbn")
    Optional<Book> findByIsbnWithBookDetail(@Param("isbn") String isbn);

    // ✅ 출판사 ID로 도서 리스트 조회
    List<Book> findByPublisherId(Long publisherId);

    // ✅ 출판사 ID로 도서 수 조회
    Long countByPublisherId(@Param("publisherId") Long publisherId);

    // ✅ BookDetail + Publisher 모두 즉시 로딩
    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.bookDetail " +
            "LEFT JOIN FETCH b.publisher " +
            "WHERE b.id = :id")
    Optional<Book> findByIdWithAllDetails(@Param("id") Long id);

    boolean existsByIsbn(String isbn);
}
