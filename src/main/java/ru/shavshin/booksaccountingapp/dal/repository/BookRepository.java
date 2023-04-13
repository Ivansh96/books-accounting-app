package ru.shavshin.booksaccountingapp.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shavshin.booksaccountingapp.dal.entity.BookEntity;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    List<BookEntity> findByTitleStartingWith(String title);
}
