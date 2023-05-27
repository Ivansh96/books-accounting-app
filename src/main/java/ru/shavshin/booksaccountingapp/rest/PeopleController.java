package ru.shavshin.booksaccountingapp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shavshin.booksaccountingapp.dal.entity.BookEntity;
import ru.shavshin.booksaccountingapp.dal.entity.PersonEntity;
import ru.shavshin.booksaccountingapp.service.PeopleService;
import ru.shavshin.booksaccountingapp.util.PersonValidator;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {

    private final PersonValidator personValidator;
    private final PeopleService peopleService;

    @GetMapping("/all")
    public String findAllPeople(Model model) {

        List<PersonEntity> people = peopleService.findAllPeople();

        model.addAttribute("people", people);
        return "people/index";
    }

    @GetMapping("/{id}")
    public String findPersonById(@PathVariable("id") Integer id, Model model) {

        PersonEntity person = peopleService.findPersonById(id);
        model.addAttribute("person", person);

        List<BookEntity> books = peopleService.getBookByPersonId(id);
        model.addAttribute("books", books);

        return "people/show";
    }


    @GetMapping("/new")
    public String getPerson(@ModelAttribute("person") PersonEntity person) {
        return "people/new";
    }

    @PostMapping("/add")
    public String addPerson(@ModelAttribute("person") @Valid PersonEntity person,
                            BindingResult bindingResult
    ) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleService.addPerson(person);
        return "redirect:/people";
    }


    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") Integer id, Model model) {

        PersonEntity person = peopleService.findPersonById(id);

        model.addAttribute("person", person);
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(
            @ModelAttribute("person") @Valid PersonEntity personToUpdate,
            BindingResult bindingResult,
            @PathVariable("id") Integer id
    ) {

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.updatePerson(id, personToUpdate);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Integer id) {

        peopleService.deletePerson(id);
        return "redirect:/people";
    }
}
