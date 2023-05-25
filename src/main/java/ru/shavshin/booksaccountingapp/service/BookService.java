package ru.shavshin.booksaccountingapp.service;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.shavshin.booksaccountingapp.dal.entity.BookEntity;
import ru.shavshin.booksaccountingapp.dal.entity.PersonEntity;
import ru.shavshin.booksaccountingapp.dal.repository.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@NoArgsConstructor
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> findAllBooks(Boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(Sort.by("year"));
        else
            return bookRepository.findAll();
    }

    public List<BookEntity> findBookWithPagination(
            Integer page,
            Integer booksPerPage,
            Boolean sortByYear
    ) {
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public BookEntity findBookById(Integer id) {
        Optional<BookEntity> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<BookEntity> searchByTitle(String title) {
        return bookRepository.findByTitleStartingWith(title);
    }


    public void addBook(BookEntity book) {
        bookRepository.save(book);
    }


    public void updateBook(Integer id, BookEntity book) {
        book.setId(id);
        book.setOwner(book.getOwner());
        bookRepository.save(book);
    }


    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public PersonEntity getBookOwner(Integer id) {
        return bookRepository.findById(id).map(BookEntity:: getOwner).orElse(null);
    }


    public void releaseBook(Integer id) {
       bookRepository.findById(id).ifPresent(book -> {
           book.setOwner(null);
           book.setTakenAt(null);
       });

    }


    public void assignBook(Integer id, PersonEntity selectedPerson) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(selectedPerson);
            book.setTakenAt(new Date());
        });
    }


}
