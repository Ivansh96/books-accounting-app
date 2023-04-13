package ru.shavshin.booksaccountingapp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shavshin.booksaccountingapp.dal.entity.PersonEntity;
import ru.shavshin.booksaccountingapp.service.PeopleService;


@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PersonEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
      PersonEntity person = (PersonEntity) o;
      if(peopleService.findByFullName(person.getFullName()).isPresent()) {
          errors.rejectValue("fullName", "", "Имя не должно повторяться!");
      }
      int x = person.getYearOfBirth();
        if (x == 0) {
            errors.rejectValue("yearOfBirth", "", "Год не может быть меньше или равен нулю!");
        }
    }
}
