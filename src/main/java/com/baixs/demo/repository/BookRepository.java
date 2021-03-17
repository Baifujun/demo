package com.baixs.demo.repository;

import com.baixs.demo.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    List<Book> findByName(String name);

    List<Book> findBySummary(String summary);

    List<Book> findByPrice(Integer price);
}
