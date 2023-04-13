package ru.shavshin.booksaccountingapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shavshin.booksaccountingapp.dal.entity.BookEntity;
import ru.shavshin.booksaccountingapp.dal.entity.PersonEntity;
import ru.shavshin.booksaccountingapp.service.BookService;
import ru.shavshin.booksaccountingapp.service.PeopleService;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping("/all")
    public String findAllBooks(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
            @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear,
            Model model
    ) {
        if (page == null || booksPerPage == null) {
            List<BookEntity> allBooks = bookService.findAllBooks(sortByYear);
            model.addAttribute("books", allBooks);

        } else {
            List<BookEntity> booksByPage = bookService.findBookWithPagination(page, booksPerPage, sortByYear);
            model.addAttribute("books", booksByPage);
        }
        return "books/index";
    }

    @GetMapping("/{id}")
    public String findBookById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));

        PersonEntity bookOwner = bookService.getBookOwner(id);

        if (bookOwner != null) {
            model.addAttribute("owner", bookOwner);
        } else {
            List<PersonEntity> people = peopleService.findAllPeople();
            model.addAttribute("people", people);
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String getBook(@ModelAttribute("book") BookEntity book) {
        return "books/new";
    }

    @PostMapping("/create")
    public String addBook(@ModelAttribute("book") @Valid BookEntity book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookService.addBook(book);
        return "redirect:/books";

    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") Integer id) {
        BookEntity book = bookService.findBookById(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(
            @ModelAttribute("book") @Valid BookEntity bookToUpdate,
            BindingResult bindingResult,
            @PathVariable("id") Integer id
    ) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookService.updateBook(id, bookToUpdate);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Integer id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") Integer id) {
        bookService.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") Integer id, @ModelAttribute("person") PersonEntity selectedPerson) {
        bookService.assignBook(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam(value = "query") String query) {
        List<BookEntity> booksByTitle = bookService.searchByTitle(query);
        model.addAttribute("books", booksByTitle);
        return "books/search";
    }

}
