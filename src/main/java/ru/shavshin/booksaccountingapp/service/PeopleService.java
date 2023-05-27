package ru.shavshin.booksaccountingapp.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shavshin.booksaccountingapp.dal.entity.BookEntity;
import ru.shavshin.booksaccountingapp.dal.entity.PersonEntity;
import ru.shavshin.booksaccountingapp.dal.repository.PeopleRepository;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public List<PersonEntity> findAllPeople() {
        return peopleRepository.findAll();
    }

    public PersonEntity findPersonById(Integer id) {
        Optional<PersonEntity> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public void addPerson(PersonEntity person) {
        peopleRepository.save(person);
    }

    public void updatePerson(Integer id, PersonEntity updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    public void deletePerson(Integer id) {
        peopleRepository.deleteById(id);
    }

    public Optional<PersonEntity> findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    public List<BookEntity> getBookByPersonId(Integer id) {
        Optional<PersonEntity> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBookList());
            person.get().getBookList().forEach(book -> {
                long difMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (difMillies > 864000000)
                    book.setExpired(true);
            });
            return person.get().getBookList();
        } else {
            return Collections.emptyList();
        }
    }
}
